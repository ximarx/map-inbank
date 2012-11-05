package gccb.net.rmi.services;

import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.IPersona;
import gccb.ITransazione;
import gccb.database.ImpiegatoDB;
import gccb.exception.OltreLimitePrelievoException;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.db.GccbDbException;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.IClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Realizzazione dell'interfaccia {@link IImpiegatoService} che permette di eseguire
 * le operazioni più comuni attribuite all'impiegato
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoService extends UnicastRemoteObject implements
		IImpiegatoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1295322826449043890L;

	public ImpiegatoService() throws RemoteException {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getPersone(gccb.net.rmi.IClient)
	 */
	@Override
	public IPersona[] getPersone(IClient client) throws RemoteException,
			GccbNetException {

		try {
			return ImpiegatoDB.getReference().getPersone();
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getPersona(int, gccb.net.rmi.IClient)
	 */
	@Override
	public IPersona getPersona(int idPersona, IClient client) throws RemoteException,
			GccbNetException {
		try {
			return ImpiegatoDB.getReference().getPersona(idPersona);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#addPersona(gccb.IPersona, gccb.net.rmi.IClient)
	 */
	@Override
	public IPersona addPersona(IPersona persona, IClient client) throws RemoteException,
		GccbNetException {
		
		if ( !persona.isNew() ) {
			throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
		}
		try {
			return ImpiegatoDB.getReference().addPersona(persona.getNome(), persona.getCognome(), persona.getIndirizzo());
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#addConto(gccb.IContoCorrenteBancario, java.lang.String, gccb.net.rmi.IClient)
	 */
	@Override
	public IContoCorrenteBancario addConto(IContoCorrenteBancario conto, String password,
			IClient client) throws RemoteException,
			GccbNetException {

		
		if ( !conto.isNew() ) {
			throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
		}
		int idPersona;
		if ( conto.getIntestatario().isNew() ) {
			IPersona aggiuntaPersona = addPersona(conto.getIntestatario(), client);
			idPersona = aggiuntaPersona.getIdPersona();
		} else {
			idPersona = conto.getIntestatario().getIdPersona();
		}
		try {
			IContoCorrenteBancario contoNuovo;
			if ( conto instanceof IContoCorrenteConFido ) {
				contoNuovo = ImpiegatoDB.getReference().addConto(idPersona,
						conto.getSaldo(),
						((IContoCorrenteConFido) conto).getFido(),
						conto.getUltimoMovimento().toMysqlString(), 
						password, 
						new Integer(client.getIdentificativo()));
			} else {
				contoNuovo = ImpiegatoDB.getReference().addConto(idPersona,
						conto.getSaldo(),
						conto.getUltimoMovimento().toMysqlString(), 
						password, 
						new Integer(client.getIdentificativo()));
			}
			return contoNuovo;
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#chiudiConto(gccb.IContoCorrenteBancario, gccb.net.rmi.IClient)
	 */
	@Override
	public void chiudiConto(IContoCorrenteBancario conto, IClient client)
			throws RemoteException, GccbNetException {
		try {
			ImpiegatoDB.getReference().chiudiConto(conto.getIdConto(), new Integer(client.getIdentificativo()));
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#doAccredito(gccb.IContoCorrenteBancario, float, gccb.net.rmi.IClient)
	 */
	@Override
	public void doAccredito(IContoCorrenteBancario conto, float importo, IClient client)
			throws RemoteException, GccbNetException {

		try {
			ImpiegatoDB.getReference().doAccredito(conto.getIdConto(), new Integer(client.getIdentificativo()), importo);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#doAddebito(gccb.IContoCorrenteBancario, float, gccb.net.rmi.IClient)
	 */
	@Override
	public void doAddebito(IContoCorrenteBancario conto, float importo, IClient client)
			throws RemoteException, GccbNetException, OltreLimitePrelievoException {

		if ( !conto.isPrelevabile(importo) ) {
			throw new OltreLimitePrelievoException("Importo non prelevabile");
		}
		try {
			ImpiegatoDB.getReference().doAddebito(conto.getIdConto(), new Integer(client.getIdentificativo()), importo);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getBancomat(int, int, gccb.net.rmi.IClient)
	 */
	@Override
	public IBancomat getBancomat(int idBancomat, int idConto, IClient client)
			throws RemoteException, DatiNonTrovatiException,
			GccbNetException {
		try {
			return ImpiegatoDB.getReference().getBancomat(idBancomat, idConto);
		} catch (DatiNonTrovatiException e) {
			throw e;
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getBancomatPerConto(int, gccb.net.rmi.IClient)
	 */
	@Override
	public IBancomat[] getBancomatPerConto(int idConto, IClient client)
			throws RemoteException, DatiNonTrovatiException, GccbNetException {
		try {
			return ImpiegatoDB.getReference().getBancomatPerConto(idConto);
		} catch (DatiNonTrovatiException e) {
			throw e;
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getConti(gccb.net.rmi.IClient)
	 */
	@Override
	public IContoCorrenteBancario[] getConti(IClient client)
			throws RemoteException, GccbNetException {

		try {
			return ImpiegatoDB.getReference().getConti();
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getConto(int, gccb.net.rmi.IClient)
	 */
	@Override
	public IContoCorrenteBancario getConto(int idConto, IClient client)
			throws RemoteException, GccbNetException,
			DatiNonTrovatiException {

		try {
			return ImpiegatoDB.getReference().getConto(idConto);
		} catch (DatiNonTrovatiException e) {
			throw e;
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getStatistiche(gccb.net.rmi.IClient)
	 */
	@Override
	public Object[] getStatistiche(IClient client) throws RemoteException,
			DatiNonTrovatiException, GccbNetException {
		try {
			return ImpiegatoDB.getReference().getStatistiche();
		} catch (DatiNonTrovatiException e) {
			throw e;
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getTransazioni(gccb.net.rmi.IClient)
	 */
	@Override
	public ITransazione[] getTransazioni(IClient client)
			throws RemoteException, GccbNetException{
		try {
			return ImpiegatoDB.getReference().getMovimenti();
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#modificaFido(gccb.IContoCorrenteConFido, float, gccb.net.rmi.IClient)
	 */
	@Override
	public IContoCorrenteConFido modificaFido(IContoCorrenteConFido conto, float nuovoFido,
			IClient client) throws RemoteException, GccbNetException, DatiNonTrovatiException {
		
		if ( nuovoFido <= 0f ) {
			throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: non e' possibile azzerare il fido");
		}
		try {
			return ImpiegatoDB.getReference().modificaFido(conto.getIdConto(), nuovoFido, new Integer(client.getIdentificativo()));
		} catch (DatiNonTrovatiException e) {
			throw e;
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#rimuoviBancomat(gccb.IBancomat, gccb.net.rmi.IClient)
	 */
	@Override
	public void rimuoviBancomat(IBancomat bancomat, IClient client)
			throws RemoteException, GccbNetException {

		if ( bancomat.isNew() ) {
			throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
		}
		try {
			ImpiegatoDB.getReference().rimuoviBancomat(bancomat.getIdBancomat(), bancomat.getContoAssociato().getIdConto(), new Integer(client.getIdentificativo()));
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#addBancomat(gccb.IContoCorrenteBancario, gccb.IBancomat, gccb.net.rmi.IClient)
	 */
	@Override
	public IBancomat addBancomat(IContoCorrenteBancario conto,
			IBancomat bancomat, IClient client) throws RemoteException,
			GccbNetException {
		
		
		if ( conto.isNew() || !bancomat.isNew() ) {
			throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
		}
		try {
			
			return ImpiegatoDB.getReference().addBancomat(conto.getIdConto(), new Integer(client.getIdentificativo()), bancomat.getChiave(), bancomat.getMassimoPrelevabile());
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#searchConto(java.lang.String, java.lang.String, boolean, gccb.net.rmi.IClient)
	 */
	@Override
	public IContoCorrenteBancario[] searchConto(String nome, String cognome,
			boolean valoriSimili, IClient client) throws RemoteException,
			GccbNetException {
		
		try {
			return ImpiegatoDB.getReference().searchConti(nome, cognome, valoriSimili);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#searchPersone(java.lang.String, java.lang.String, boolean, gccb.net.rmi.IClient)
	 */
	@Override
	public IPersona[] searchPersone(String nome, String cognome,
			boolean valoriSimili, IClient client) throws RemoteException, GccbNetException {
		
		try {
			return ImpiegatoDB.getReference().searchPersone(nome, cognome, valoriSimili);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}


	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#getTransazioni(int, gccb.net.rmi.IClient)
	 */
	@Override
	public ITransazione[] getTransazioni(int idConto, IClient client)
			throws RemoteException,GccbNetException {
		try {
			return ImpiegatoDB.getReference().getMovimenti(idConto);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IImpiegatoService#searchContoByPersona(int, gccb.net.rmi.IClient)
	 */
	@Override
	public IContoCorrenteBancario[] searchContoByPersona(int idPersona,
			IClient client) throws RemoteException, GccbNetException {
		try {
			return ImpiegatoDB.getReference().searchContiPersona(idPersona);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}
}
