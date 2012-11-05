package gccb.net.rmi;

import java.rmi.RemoteException;

/**
 * Client per utente impiegato
 * @author Francesco Capozzo
 *
 */
public class RMIClientImpiegato extends RMIClientAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3419368448645944769L;

	RMIClientImpiegato(IServiceFactory rmiServer) throws RemoteException {
		super(rmiServer);
		clientType = RMIClientType.IMPIEGATO;
	}
}
