package gccb.net.rmi.services;

import gccb.IContoCorrenteBancario;
import gccb.ITransazione;
import gccb.database.ClienteDB;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.db.GccbDbException;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.IClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Realizzazione dell'interfaccia {@link IClienteService} che permette di eseguire
 * le operazioni più comuni attribuite al cliente
 * @author Francesco Capozzo
 *
 */
public class ClienteService extends UnicastRemoteObject implements IClienteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1715264044534893043L;

	public ClienteService() throws RemoteException {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IClienteService#doBonifico(int, int, float, gccb.net.rmi.IClient)
	 */
	public void doBonifico(int idContoOrigine, int idContoDestinazione, float importo, IClient client) throws RemoteException, GccbNetException, IllegalArgumentException {
		try {
			if ( !ClienteDB.getReference().doBonifico(idContoOrigine, idContoDestinazione, importo, new Integer(client.getIdentificativo())) ) {
				throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
			}
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IClienteService#getUltimiMovimentiConto(int, gccb.net.rmi.IClient)
	 */
	public ITransazione[] getUltimiMovimentiConto(int idConto, IClient client)
			throws RemoteException, IllegalArgumentException, GccbNetException {

		try {
			return ClienteDB.getReference().getUltimiMovimenti(idConto);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IClienteService#getConto(int, gccb.net.rmi.IClient)
	 */
	public IContoCorrenteBancario getConto(int idConto, IClient client) throws RemoteException, GccbNetException, DatiNonTrovatiException {
		
		try {
			IContoCorrenteBancario c = ClienteDB.getReference().getConto(idConto);
			return c;
		} catch ( GccbDbException e ) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}
}
