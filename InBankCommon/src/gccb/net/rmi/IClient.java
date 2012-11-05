package gccb.net.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia che rappresenta i client
 * Ogni client valido deve implementare questa interfaccia
 * @author Francesco Capozzo
 *
 */
public interface IClient extends Remote {

	/**
	 * Impone un logout al client. Questa funzione viene invocata
	 * dal RMIServer
	 * @throws RemoteException
	 */
	void forceLogout() throws RemoteException;
	/**
	 * Restituisce l'identificativo del tipo del client.
	 * L'intero può essere confrontato con {@link RMIClientType}
	 * @return l'intero identificativo del tipo di client
	 * @throws RemoteException in caso di errore di connessione
	 */
	int getClientType() throws RemoteException;
	/**
	 * Restituisce l'id agente del client
	 * @return un intero > 0 se il client è correttamente autenticato
	 * -1 negli altri casi
	 * @throws RemoteException in caso di errore di connessione
	 */
	String getIdentificativo() throws RemoteException;
	/**
	 * Funzione fasulla invocata dal RMIServer per assicurarsi
	 * che il client sia ancora connesso
	 * @return un qualsiasi booleano se il client è connesso
	 * @throws RemoteException se il client non è più connesso
	 */
	boolean ping() throws RemoteException;
}
