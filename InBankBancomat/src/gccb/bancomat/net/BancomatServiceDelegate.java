package gccb.bancomat.net;

import java.rmi.RemoteException;

import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.net.rmi.ILogoutAction;
import gccb.net.rmi.RMIClientBancomat;
import gccb.net.rmi.RMIClientFactory;
import gccb.net.rmi.services.IBancomatService;
import gccb.settings.SettingsManager;

/**
 * Service delegate per il client Bancomat. Permette di accedere ai servizi
 * del server in maniera trasparente al client
 * @author Francesco Capozzo
 *
 */
public class BancomatServiceDelegate {

	
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
	
	private static IBancomatService rmiService = null;
	private static RMIClientBancomat rmiClient = null;
	
	private BancomatServiceDelegate() {}
	
	/**
	 * Esegue la connessione al server in base alle credenziali specificate
	 * @param username username 
	 * @param password password
	 * @throws CredenzialiNonValideException se le credenziali non sono valide
	 * @throws GccbNetException in caso di errore generico
	 */
	public static void connect(String username, String password) throws CredenzialiNonValideException, GccbNetException {
		rmiClient = RMIClientFactory.getClientBancomat(serverAddress, serverPort, serviceName, username, password);
		rmiService = (IBancomatService) rmiClient.getService("gccb.net.rmi.services.BancomatService");
	}
	
	/**
	 * Imposta l'operazione da eseguire in caso di richiesta di logout da parte del server
	 * @param la la nuova azione
	 */
	public static void setLogoutAction(ILogoutAction la) {
		if ( rmiClient != null ) {
			rmiClient.setLogoutAction(la);
		}
	}
	
	/**
	 * Esegue il prelievo della somma indicata
	 * @param importo la somma
	 * @throws GccbNetException in caso di errore generico
	 * @throws ServerNonDisponibileException se il server non e' più disponibile
	 */
	public static void doPrelievo(float importo) throws GccbNetException, ServerNonDisponibileException {
		try {
			rmiService.doPrelievo(importo, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	
	/**
	 * Recupera dal server le informazioni relative ad un conto corrente
	 * @return il conto corrente trovato
	 * @throws ServerNonDisponibileException se il server non e' piu' disponibile
	 * @throws GccbNetException in caso di errore generico
	 */
	public static IContoCorrenteBancario getConto() throws ServerNonDisponibileException, GccbNetException {
		try {
			return rmiService.doSaldo(rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	
	/**
	 * Recupera dal server le informazioni relative ad un bancomat
	 * @return il bancomat trovato
	 * @throws GccbNetException in caso di errore generico
	 * @throws ServerNonDisponibileException se il server non e' disponibile
	 */
	public static IBancomat getBancomat() throws GccbNetException, ServerNonDisponibileException {
		try {
			return rmiService.getBancomat(rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}

	/**
	 * Indica se la connessione al server risulta attiva
	 * @return true se attiva
	 */
	public static boolean isConnected() {
		return (rmiService != null && rmiClient != null);
	}
	
	/**
	 * Esegue la disconnessione dal server
	 */
	public static void disconnect() {
		try {
			rmiClient.disconnetti();
		} finally {
			rmiClient = null;
			rmiService = null;
		}
	}
}
