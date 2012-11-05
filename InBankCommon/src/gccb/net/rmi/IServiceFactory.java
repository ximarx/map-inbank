package gccb.net.rmi;

import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.services.IRMIService;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia del servizio RMI esposto ai client
 * Permette di eseguire l'autenticazione del client
 * e di prelevare dei servizi specifici aggiuntivi
 * @author Francesco Capozzo
 *
 */
public interface IServiceFactory extends Remote {

	/**
	 * Ritorna un insieme di stringhe corrispondenti ai nomi dei servizi
	 * attualmente registrati sul server
	 * @param client il client che invoca il metodo
	 * @return l'insieme di servizi
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException 
	 * @throws ErroreServerException in caso di malfunzionamento generico del server
	 */
	public String[] getAvailableServices(IClient client)
			throws RemoteException, GccbNetException;

	/**
	 * Restituisce il riferimento al servizio richiesto
	 * se il client è autorizzato ad accedervi
	 * @param serviceName il nome del servizio richiesto.
	 * @param client il client richiedente
	 * @return Il riferimento al servizio
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException 
	 * @throws CredenzialiNonValideException 
	 * @throws OperazioneNonConsentita Se il client non è autorizzato ad accedere al servizio
	 * @throws ErroreServerException se il servizio non è registrato o che un errore
	 * generico nel server
	 */
	public IRMIService getServiceReference(String serviceName, IClient client)
			throws RemoteException, CredenzialiNonValideException, GccbNetException;

	/**
	 * Registra il client eseguendo l'autenticazione
	 * @param username la username
	 * @param password la password
	 * @param client il client che richiede l'autenticazione
	 * @return l'id agente del client se l'autenticazione ha successo
	 * @throws RemoteException in caso di errore di connessione
	 * @throws CredenzialiNonValideException se le credenziali fornite non sono valide
	 * @throws GccbNetException in caso di errore interno del server  
	 */
	public int registerClient(String username, String password, IClient client)
			throws RemoteException, CredenzialiNonValideException,
			GccbNetException;

	/**
	 * Esegue il logout del client revocando l'autenticazione
	 * e chiudendo la connessione
	 * @param client il client che richiede la disconnessione
	 * @return l'esito della disconnessione
	 * @throws RemoteException in caso di errori di connessione
	 */
	public boolean unregisterClient(IClient client) throws RemoteException;

}
