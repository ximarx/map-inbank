package gccb.cliente.net;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import gccb.IContoCorrenteBancario;
import gccb.ITransazione;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.net.rmi.ILogoutAction;
import gccb.net.rmi.RMIClientCliente;
import gccb.net.rmi.RMIClientFactory;
import gccb.net.rmi.services.IClienteService;
import gccb.settings.SettingsManager;

/**
 * Service Delegate per il client eBanking.
 * Permette di recuperare ed inviare le informazioni al server
 * in modo da rendere trasparente il tipo di canale
 * usato per comunicare con il server
 * 
 * @author Francesco Capozzo
 *
 */
public class ClienteServiceDelegate {
	
	private static IClienteService rmiService = null;
	private static RMIClientCliente rmiClient = null;

	static private String serverAddress;
	static private int serverPort;
	static private String serviceName;
	
	static {
		serverAddress = SettingsManager.getDefaultInstance().get("server.host.address");
		try {
			serverPort = Integer.valueOf(SettingsManager.getDefaultInstance().get("server.host.port"));
		} catch (NumberFormatException nme) {
			serverPort = 1099;
		}
		serviceName = SettingsManager.getDefaultInstance().get("server.service.name");
	}
	
	private ClienteServiceDelegate() {}
	
	/**
	 * Indica se il client è connesso
	 * @return l'esito del controllo
	 */
	public static boolean isConnected() {
		return (rmiService != null && rmiClient != null);
	}
	
	/**
	 * Esegue la connessione al server
	 * @param username username
	 * @param password password
	 * @throws RemoteDataException in caso di errore di connessione
	 * @throws CredenzialiNonValideException se le credenziali inserite non sono valide
	 */
	public static void connect(String username, String password) throws GccbNetException, ServerNonDisponibileException, CredenzialiNonValideException {
		if ( rmiClient == null ) 
			rmiClient = RMIClientFactory.getClientCliente(serverAddress, serverPort, serviceName, username, password);
		rmiService = (IClienteService) rmiClient.getService("gccb.net.rmi.services.ClienteService");
	}
	
	/**
	 * Imposta l'operazione da eseguire nel caso in cui il server
	 * richiedesse il logout
	 * @param la l'operazione
	 */
	public static void setLogoutAction(ILogoutAction la) {
		if ( rmiClient != null ) {
			rmiClient.setLogoutAction(la);
		}
	}
	
	/**
	 * Recupera dal server la lista degli ultimi movimenti
	 * @return la lista recuperata
	 * @throws GccbNetException in caso di errore del server
	 * @throws IllegalArgumentException se il conto indicato non e' valido 
	 * @throws ServerNonDisponibileException in caso di errore di connessione
	 */
	public static List<ITransazione> findUltimiMovimenti() throws IllegalArgumentException, ServerNonDisponibileException, GccbNetException {
		try {
			return Arrays.asList(rmiService.getUltimiMovimentiConto(rmiClient.getIdConto(), rmiClient));
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	
	/**
	 * Recupera le informazioni relative al conto
	 * @return una istanza di {@link IContoCorrenteBancario} con le informazioni trovate
	 * @throws GccbNetException in caso di errore interno del server
	 * @throws ServerNonDisponibileException  in caso di errore di connessione
	 * @throws DatiNonTrovatiException se il conto non e' valido
	 */
	public static IContoCorrenteBancario findDatiConto() throws DatiNonTrovatiException, GccbNetException, ServerNonDisponibileException {
		try {
			return rmiService.getConto(rmiClient.getIdConto(), rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	
	/**
	 * Esegue un bonifico
	 * @param idContoDestinazione il conto di destinazione
	 * @param importo l'importo trasferire
	 * @throws GccbNetException in caso di errore del server
	 * @throws IllegalArgumentException se il conto indicato non e' valido 
	 * @throws ServerNonDisponibileException in caso di errore di connessione
	 */
	public static void doBonifico(int idContoDestinazione, float importo) throws ServerNonDisponibileException, IllegalArgumentException, GccbNetException{
		try {
			rmiService.doBonifico(rmiClient.getIdConto(), idContoDestinazione, importo, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}

	/**
	 * Esegue la disconnessione dal server
	 */
	public static void disconnect() {
		rmiService = null;
		rmiClient.disconnetti();
		rmiClient = null;
	}
}
