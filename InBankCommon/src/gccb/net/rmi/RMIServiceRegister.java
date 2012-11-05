package gccb.net.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import gccb.exception.net.GccbNetException;
import gccb.net.rmi.services.IRMIService;

/**
 * Classe factory per l'RmiServer. Permette di registrate un server
 * e di eseguire le operazioni comuni per la gestione dei servizi e dei
 * client connessi
 * @author Francesco Capozzo
 *
 */
abstract public class RMIServiceRegister {

	static private RMIServer serviceManager = null;
	static private Timer cleanTimer = new Timer();
	
	/**
	 * Registra il ServiceFactory nel Naming Server indicato
	 * @param hostAddress indirizzo del server
	 * @param serverName nome con il quale esporre il servizio
	 * @param hostPort indirizzo del server
	 * @throws GccbNetException se il server e' gia attivo o in caso di errori durante la registrazione 
	 */
	static public void createServer(String hostAddress, String serverName, int hostPort) throws GccbNetException {
		if ( serviceManager == null ) {
			try {
				serviceManager = new RMIServer();
				Naming.rebind("rmi://"+hostAddress+":"+hostPort+"/"+serverName, (IServiceFactory) serviceManager.getRemoteInterface());
				cleanTimer.schedule(new TimerTask() {
					public void run() {
						serviceManager.closeBrokenConnection();
					}
				}, 10 * 60 * 1000, 10 * 60 * 1000);
			} catch (RemoteException e) {
				serviceManager = null;
				throw new GccbNetException("Errore durante la creazione del server RMI: " + e.getMessage(), e);
			} catch (MalformedURLException e) {
				serviceManager = null;
				throw new IllegalArgumentException("Indirizzo del RMI Naming server non corretto", e);
			}
		} else {
			throw new GccbNetException("Server gia' registrato");
		}
	}
	
	/**
	 * Restituisce l'insieme di client connessi e autenticati
	 * @return un array di client connessi
	 */
	static public IClient[] getConnectedClients() {
		try {
			return serviceManager.getClients();
		} catch (NullPointerException npe) {
			return new IClient[]{};
		}
	}
	
	/**
	 * Controlla che il servizio indicato sia registrato
	 * @param serviceName il nome del servizio
	 * @return l'esito del controllo
	 */
	static public boolean isRegistered(String serviceName) {
		try {
			return serviceManager.isRegistered(serviceName);
		} catch (NullPointerException npe) {
			return false;
		}
	}
	
	/**
	 * Controlla la modalità di accesso al servizio registrato con il nome indicato
	 * @param serviceName il nome del servizio
	 * @return il tipo di client a cui è consentito accedere al servizio
	 */
	static public RMIClientType registrationType(String serviceName) {
		return serviceManager.registrationType(serviceName);
	}
	
	/**
	 * Indica se il server è pronto per essere utilizzato
	 * @return esito del controllo
	 */
	static public boolean isServerReady() {
		return ( serviceManager != null );
	}
	
	/**
	 * Registra un nuovo servizio con il nome e nella modalità indicata
	 * @param serviceName il nome del servizio
	 * @param ctype il tipo di client a cui consentire l'accesso
	 * @return l'esito della registrazione
	 */
	static public boolean registerService(String serviceName, RMIClientType ctype) {
		try {
			IRMIService s = (IRMIService) Class.forName(serviceName).newInstance();
			serviceManager.registerService(serviceName, s, ctype);
			return true;
		} catch ( ClassNotFoundException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * Rimuove un servizio dalla lista di quelli registrati
	 * @param serviceName il nome del servizio da rimuovere
	 * @return l'esito della operazione
	 */
	static public boolean unregisterService(String serviceName) {
		try {
			return serviceManager.unregisterService(serviceName);
		} catch ( Exception e) {
			return false;
		}
	}
	/**
	 * Interrompe la connessione al client con l'id indicato
	 * @param clientId id agente del client da disconnettere
	 * @return l'esito dell'operazione
	 */
	static public boolean closeClientConnection(int clientId) {
		return serviceManager.closeClientConnection(clientId);
	}
}
