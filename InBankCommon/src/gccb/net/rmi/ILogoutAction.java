package gccb.net.rmi;

/**
 * Interfaccia dell'azione eseguita dal client in caso di disconnessione
 * imposta dal server.
 * @author Francesco Capozzo
 *
 */
public interface ILogoutAction {
	/**
	 * Esegue l'azione corrispondente alla chiusura della connessione
	 * dal RMIServer
	 */
	void execute();
}
