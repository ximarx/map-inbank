package gccb.net.rmi;

import java.rmi.RemoteException;

/**
 * Client per utente bancomat
 * @author Francesco Capozzo
 *
 */
public class RMIClientBancomat extends RMIClientAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2053394490120452337L;

	/**
	 * Costruisce l'oggetto impostando il riferimento al server RMI
	 * @param rmiServer il service factory del server rmi
	 * @throws RemoteException in caso di errore di connessione
	 */
	RMIClientBancomat(IServiceFactory rmiServer) throws RemoteException {
		super(rmiServer);
		clientType = RMIClientType.BANCOMAT;
	}
}
