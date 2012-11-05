package gccb.net.rmi;

import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Classe factory per i client rmi
 * @author Francesco Capozzo
 *
 */
abstract public class RMIClientFactory {

	/**
	 * Crea la connessione al server rmi e ritorna un client connesso
	 * @param serverAddress l'indirizzo del Naming Server
	 * @param serverPort la porta su cui è in ascolto il Naming Server
	 * @param serviceName il nome del servizio con cui è registrato il Service Factory 
	 * @param ct il tipo client richiesto
	 * @param username username per l'autenticazione
	 * @param password password associata allo username
	 * @return il client connesso in base alle informazioni indicate
	 * @throws GccbNetException in caso di errori durante la connessione
	 * @throws CredenzialiNonValideException se le credenziali fornite non sono valide
	 */
	static private RMIClientAdapter getClient(String serverAddress, int serverPort, String serviceName, RMIClientType ct, String username, String password) throws CredenzialiNonValideException, GccbNetException {

		String connectionString = "rmi://"+serverAddress+":"+serverPort+"/"+serviceName;
		RMIClientAdapter rmiA = null;
		try {
			if ( ct == RMIClientType.BANCOMAT ) {
				rmiA = new RMIClientBancomat((IServiceFactory) Naming.lookup(connectionString));
				rmiA.connetti(username, password);
			} else if ( ct == RMIClientType.IMPIEGATO ) {
				rmiA = new RMIClientImpiegato((IServiceFactory) Naming.lookup(connectionString));
				rmiA.connetti(username, password);
			} else if ( ct == RMIClientType.UTENTE ) {
				rmiA = new RMIClientCliente((IServiceFactory) Naming.lookup(connectionString));
				rmiA.connetti(username, password);
				((RMIClientCliente)rmiA).setIdConto(username);
			}
			return rmiA;
		} catch (MalformedURLException e) {
			throw new GccbNetException("Indirizzo del server non corretto",e);
		} catch (NotBoundException e) {
			throw new GccbNetException("Il server sembra non essere presente all'indirizzo specificato",e);
		} catch (RemoteException e) {
			throw new GccbNetException("Errore durante la connessione al server: " + e.getMessage(), e);
		}
	}
	/**
	 * Esegue la connessione di un client di tipo Cliente all'indirizzo indicato
	 * @param serverAddress l'indirizzo del Naming Server
	 * @param serverPort porta del Naming Server
	 * @param serviceName nome del servizio
	 * @param username username 
	 * @param password password
	 * @return un client di tipo Cliente connesso
	 * @throws GccbNetException In caso di errore durante la connessione
	 * @throws CredenzialiNonValideException se le credenziali non sono valide
	 */
	static public RMIClientCliente getClientCliente(
				String serverAddress,
				int serverPort,
				String serviceName,
				String username,
				String password) throws CredenzialiNonValideException, GccbNetException
			{
		
		return (RMIClientCliente) getClient(serverAddress, serverPort,
				serviceName, RMIClientType.UTENTE, username, password);
	}

	/**
	 * Esegue la connessione di un client di tipo Bancomat all'indirizzo indicato
	 * @param serverAddress l'indirizzo del Naming Server
	 * @param serverPort porta del Naming Server
	 * @param serviceName nome del servizio
	 * @param username username 
	 * @param password password
	 * @return un client di tipo Cliente connesso
	 * @throws GccbNetException In caso di errori durante la connessione
	 * @throws CredenzialiNonValideException se le credenziali non sono valide
	 */
	static public RMIClientBancomat getClientBancomat(
				String serverAddress,
				int serverPort,
				String serviceName,
				String username,
				String password) throws CredenzialiNonValideException, GccbNetException
			{

		
		return (RMIClientBancomat) getClient(serverAddress, serverPort,
				serviceName, RMIClientType.BANCOMAT, username, password);
	}

	/**
	 * Esegue la connessione di un client di tipo Impiegato all'indirizzo indicato
	 * @param serverAddress l'indirizzo del Naming Server
	 * @param serverPort porta del Naming Server
	 * @param serviceName nome del servizio
	 * @param username username 
	 * @param password password
	 * @return un client di tipo Cliente connesso
	 * @throws GccbNetException in caso di errori durante la connessione
	 * @throws CredenzialiNonValideException se le credenziali non sono valide
	 */
	static public RMIClientImpiegato getClientImpiegato(
				String serverAddress,
				int serverPort,
				String serviceName,
				String username,
				String password) throws CredenzialiNonValideException, GccbNetException
			{
		
		return (RMIClientImpiegato) getClient(serverAddress, serverPort,
				serviceName, RMIClientType.IMPIEGATO, username, password);
	}
}
