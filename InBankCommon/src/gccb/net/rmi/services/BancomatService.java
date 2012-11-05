package gccb.net.rmi.services;

import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.database.BancomatDB;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.db.GccbDbException;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.IClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Realizzazione dell'interfaccia {@link IBancomatService} che permette di eseguire
 * le operazioni più comuni attribuite al bancomat
 * @author Francesco Capozzo
 *
 */
public class BancomatService extends UnicastRemoteObject implements IBancomatService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 679582588192801279L;
	public BancomatService() throws RemoteException {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IBancomatService#doPrelievo(float, gccb.net.rmi.IClient)
	 */
	public void doPrelievo(float importo, IClient client) throws RemoteException, GccbNetException {
		try {
			if ( !BancomatDB.getReference().doPrelievo(new Integer(client.getIdentificativo()), importo) ) {
				throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
			}
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IBancomatService#doSaldo(gccb.net.rmi.IClient)
	 */
	public IContoCorrenteBancario doSaldo(IClient client) throws RemoteException, GccbNetException {
		try {
			IContoCorrenteBancario c = BancomatDB.getReference().doSaldo(new Integer(client.getIdentificativo()));
			if ( c != null ) {
				return c;
			} else {
				throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
			}
		} catch ( DatiNonTrovatiException e) {
			throw new GccbNetException("Errore durante l'operazione: " + e.getMessage(), e);
		} catch (GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.services.IBancomatService#getBancomat(gccb.net.rmi.IClient)
	 */
	public IBancomat getBancomat(IClient client) throws RemoteException, GccbNetException {
		try {
			IBancomat b = BancomatDB.getReference().getBancomat(new Integer(client.getIdentificativo()));
			if ( b != null ) {
				return b;
			} else {
				throw new IllegalArgumentException("Si e' verificato un errore durante l'operazione: controllare i dati forniti");
			}
		} catch ( DatiNonTrovatiException e) {
			throw new GccbNetException("Errore durante l'operazione: " + e.getMessage(), e);
		} catch ( GccbDbException e) {
			throw new GccbNetException("Errore durante l'operazione");
		}
	}
}
