package gccb.server.net;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import gccb.IImpiegato;
import gccb.database.DbAccess;
import gccb.database.ServerDB;
import gccb.exception.db.GccbDbException;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.net.rmi.IClient;
import gccb.net.rmi.RMIClientType;
import gccb.net.rmi.RMIServiceRegister;
import gccb.net.rmi.services.IRMIService;
import gccb.server.model.ServerClient;
import gccb.settings.SettingsManager;

/**
 * Service delegate usato dal GUI del server
 * per accedere alle informazioni e allo stato
 * delle connessioni rmi e al database.
 * Consente inoltre di accedere e modificare le informazioni
 * nel database
 * @author Francesco Capozzo
 *
 */
public class ServerServiceDelegate {

	static private String dbHostAddress;
	static private String dbUsername;
	static private String dbPassword;
	static private int dbPort;
	static private String dbName;
	static {
		dbHostAddress = SettingsManager.getDefaultInstance().get("database.host.address");
		dbUsername = SettingsManager.getDefaultInstance().get("database.username");
		dbPassword = SettingsManager.getDefaultInstance().get("database.password");
		try {
			dbPort = Integer.valueOf(SettingsManager.getDefaultInstance().get("database.host.port"));
		} catch (NumberFormatException nme) {
			dbPort = 3306;
		}
		dbName = SettingsManager.getDefaultInstance().get("database.name");
	}

	static private String rmiHostAddress;
	static private String rmiServiceName;
	static private int rmiPort;
	static private String[] rmiRegistrableService;
	static {
		rmiHostAddress = SettingsManager.getDefaultInstance().get("rmi.host.address");
		try {
			rmiPort = Integer.valueOf(SettingsManager.getDefaultInstance().get("rmi.host.port"));
		} catch (NumberFormatException nme) {
			rmiPort = 1099;
		}
		rmiServiceName = SettingsManager.getDefaultInstance().get("rmi.service.name");
		rmiRegistrableService = loadRegistrableServices();
	}

	/**
	 * Attiva il servizio specificato e lo esporta tramite il ServiceFactory
	 * @param serviceName il nome del servizio
	 * @param modalita la modalità di attivazione
	 * @throws Exception in caso di errore durante la registrazione
	 */
	static public void attivaServizio(String serviceName, RMIClientType modalita) throws Exception {
		if ( !RMIServiceRegister.isServerReady() ) {
			registraServer();
		}
		RMIServiceRegister.registerService(serviceName, modalita);
	}
	
	/**
	 * Disattiva il servizio con il nome specificato
	 * @param serviceName il nome del servizio da disattivare
	 */
	static public void disattivaServizio(String serviceName) {
		RMIServiceRegister.unregisterService(serviceName);
	}
	
	/**
	 * Disattiva tutti i servizi attivi
	 * La disconnessione di tutti i client
	 * è una conseguenza della disattivazione di tutti i servizi
	 */
	static public void disattivaTuttiServizi() {
		for (String servizio : rmiRegistrableService ) {
			if ( RMIServiceRegister.isRegistered(servizio) ) {
				RMIServiceRegister.unregisterService(servizio);
			}
		}
	}
	
	/**
	 * Registra il serviceFactory inizializzando
	 * il sistema rmi per essere utilizzato
	 * @throws Exception in caso di errore durante la registrazione
	 */
	static public void registraServer() throws Exception {
		RMIServiceRegister.createServer(rmiHostAddress, rmiServiceName, rmiPort);
	}
	
	/**
	 * Attiva la connessione al database
	 * @throws DatabaseException in caso di errori durante la connessione
	 */
	static public void connettiDb() throws GccbDbException {
		DbAccess.initializeConnection(dbHostAddress+":"+dbPort, dbName, dbUsername, dbPassword);
	}
	
	/**
	 * Chiude la connessione attiva al database
	 */
	static public void disconnettiDb() {
		DbAccess.closeConnection();
	}
	/**
	 * Controlla se il database è connesso
	 * e indica l'esito del controllo
	 * @return true se il db è connesso
	 */
	static public boolean isDbConnesso() {
		return DbAccess.isConnected();
	}
	
	/**
	 * Restituisce la lista dei client connessi ai servizi
	 * inglobando le informazioni all'interno di 
	 * istanza di {@link ServerClient}
	 * @return la lista di client connessi
	 */
	static public List<ServerClient> listaClientConnessi() {
		
		List<ServerClient> listaClient = new ArrayList<ServerClient>();
		for ( IClient client : RMIServiceRegister.getConnectedClients() ) {
			String clientAddress = client.toString();
			clientAddress = clientAddress.substring(
					clientAddress.indexOf("[endpoint:[") + 11,
					clientAddress.indexOf("](remote)")
				);
			try {
				listaClient.add(new ServerClient( client.getIdentificativo(), clientAddress, RMIClientType.values()[client.getClientType()] ));
			} catch (RemoteException re) { /* il client non è connesso */ }
		}
		return listaClient;
	}
	
	/**
	 * Disconnette il client indicato
	 * @param client il client da diconnettere
	 */
	static public void disconnettiClient(ServerClient client) {
		RMIServiceRegister.closeClientConnection(Integer.valueOf(client.getIdClient()));
	}
	
	/**
	 * Recupera e restituisce la lista di impiegati registrati
	 * nel database
	 * @return la lista di {@link IImpiegato}
	 */
	static public List<IImpiegato> listaImpiegati() {
		try {
			return ServerDB.getReference().getImpiegati();
		} catch (Exception e) {
			return Collections.EMPTY_LIST;
		}
	}
	
	/**
	 * Modifica la password di un impiegato specificato con quella
	 * indicata
	 * @param idImpiegato id dell'impiegato da modificare
	 * @param newPassword la nuova password da modificare
	 */
	static public void modificaPasswordImpiegato(int idImpiegato, String newPassword) {
		try {
			ServerDB.getReference().modificaPasswordImpiegato(idImpiegato, newPassword);
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}
	
	/**
	 * Aggiunge un nuovo impiegato
	 * @param newNome il nome del nuovo impiegato
	 * @param newPassword la password del nuovo impiegato
	 * @param newVisualizzato l'etichetta dell'impiegato
	 * @return l'esito dell'inserimento
	 */
	static public boolean aggiungiImpiegato(String newNome, String newPassword, String newVisualizzato) {
		try {
			if ( newNome == null || newNome.equals("") ||
					newPassword == null || newPassword.equals("") ||
					newVisualizzato == null || newVisualizzato.equals("") ) {
				throw new IllegalArgumentException("Dati inseriti non corretti");
			}
			return ServerDB.getReference().aggiungiImpiegato(newNome, newPassword, newVisualizzato);
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
			return false;
		}
	}
	
	/**
	 * Cancella l'impiegato indicato dalla lista di quelli registrati
	 * @param idImpiegato id dell'impiegato
	 */
	static public void rimuoviImpiegato(int idImpiegato) {
		try {
			ServerDB.getReference().rimuoviImpiegato(idImpiegato);
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}
	
	/**
	 * Indica l'indirizzo del database
	 * @return l'indirizzo
	 */
	static public String getDbHostAddress() {
		return dbHostAddress;
	}
	/**
	 * Imposta un nuovo indirizzo al server db
	 * @param dbHostAddress un indirizzo ip valido o un hostname valido
	 */
	static public void setDbHostAddress(String dbHostAddress) {
		ServerServiceDelegate.dbHostAddress = dbHostAddress;
		
	}
	/**
	 * Indica lo username per la connessione al db
	 * @return lo username impostato
	 */
	static public String getDbUsername() {
		return dbUsername;
	}
	/**
	 * Imposta lo username per l'accesso al db
	 * @param dbUsername il nuovo username
	 */
	static public void setDbUsername(String dbUsername) {
		ServerServiceDelegate.dbUsername = dbUsername;
	}
	/**
	 * Indica la porta verso cui il server db è in ascolto
	 * @return la porta selezionata
	 */
	static public int getDbPort() {
		return dbPort;
	}
	/**
	 * Imposta la nuova porta verso cui tentare la connessione al db
	 * @param dbPort la nuova porta
	 */
	static public void setDbPort(int dbPort) {
		ServerServiceDelegate.dbPort = dbPort;
	}
	/**
	 * Indica il nome del database a cui collegarsi
	 * @return il nome del database
	 */
	static public String getDbName() {
		return dbName;
	}
	/**
	 * Imposta un nuovo nome al database
	 * @param dbName il nuovo nome
	 */
	static public void setDbName(String dbName) {
		ServerServiceDelegate.dbName = dbName;
	}
	/**
	 * Indica l'indirizzo del Naming Server RMI
	 * @return l'indirizzo del naming server
	 */
	static public String getRmiHostAddress() {
		return rmiHostAddress;
	}

	/**
	 * Imposta l'indirizzo verso cui tentare la registrazione
	 * dei servizi
	 * @param rmiHostAddress il nuovo indirizzo del naming server
	 */
	static public void setRmiHostAddress(String rmiHostAddress) {
		ServerServiceDelegate.rmiHostAddress = rmiHostAddress;
	}
	
	/**
	 * Indica il nome con cui registrare il ServiceFactory
	 * @return il nome del servizio
	 */
	static public String getRmiServiceName() {
		return rmiServiceName;
	}

	/**
	 * Imposta un nuovo nome con cui registrare il servizio
	 * @param rmiServiceName il nuovo nome
	 */
	static public void setRmiServiceName(String rmiServiceName) {
		ServerServiceDelegate.rmiServiceName = rmiServiceName;
	}

	/**
	 * La porta su cui è in ascolto il Naming Server
	 * @return la porta
	 */
	static public int getRmiPort() {
		return rmiPort;
	}

	/**
	 * Imposta una nuova porta verso cui tentare il collegamento
	 * @param rmiPort la nuova porta
	 */
	static public void setRmiPort(int rmiPort) {
		ServerServiceDelegate.rmiPort = rmiPort;
	}

	/**
	 * Restituisce un array di nome di servizi
	 * trovati nel classpath che è possibile registrare
	 * @return l'insieme di servizi
	 */
	static public String[] getRmiRegistrableService() {
		return rmiRegistrableService;
	}
	
	/**
	 * Imposta una nuova lista di servizi registrabili
	 * @param rmiRegistrableService la nuova lista
	 */
	static public void setRmiRegistrableService(String[] rmiRegistrableService) {
		ServerServiceDelegate.rmiRegistrableService = rmiRegistrableService;
	}

	/**
	 * Indica se un servizio è attivo
	 * @param serviceName il nome del servizio
	 * @return l'esito del controllo
	 */
	static public boolean isAttivo(String serviceName) {
		return RMIServiceRegister.isRegistered(serviceName);
	}

	/**
	 * Indica il tipo di attivazione di un servizio attivo
	 * @param serviceName il nome del servizio
	 * @return il tipo di attivazione
	 */
	static public RMIClientType tipoAttivazione(String serviceName) {
		return RMIServiceRegister.registrationType(serviceName);
	}
	
	/**
	 * Carica la lista di servizi registrabili scandendo il classpath
	 * e il package gccb.net.rmi.services alla ricerca di classi
	 * che corrispondano ai requisiti richiesti.
	 * Le classi valide devono implementare {@link IRMIService}
	 * e devono avere il file _Stub compilato tramite in rmic
	 * @return l'insieme dei servizi individuati
	 */
	private static String[] loadRegistrableServices() {
		
		String pkgname = "gccb.net.rmi.services";
		
		ArrayList<String> services = new ArrayList<String>();
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				return new String[]{};
			}
			
			String path = pkgname.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				return new String[]{};
			}
			directory = new File(resource.getFile());
		} catch (NullPointerException x) {
			x.printStackTrace();
			return new String[]{};
		}
		if (directory.exists()) {
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				if (files[i].endsWith("Service.class")) {
					String serviceName = pkgname + '.'	+ files[i].substring(0, files[i].length() - 6);
					try {
						// per essere ammissibile il servizio non deve essere una interfaccia 
						// e deve implementare IRMIService
						if ( IRMIService.class.isAssignableFrom(Class.forName(serviceName)) &&
								!Class.forName(serviceName).isInterface() ) {
							services.add(serviceName);
						}
					} catch (ClassNotFoundException cnfe) {
					} catch (Error err) { /* Ignora gli errori di compilazione dei file di servizio */}
					
				}
			}
		}
		try {
			String jarPlace = ServerServiceDelegate.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			File fff = new File(jarPlace);
			String jarDir = fff.getParent();
			services.addAll(getClassesFromFileJarFile(pkgname, jarDir));
		} catch (Exception npe) {}
		return services.toArray(new String[]{});
	}
	
	/**
	 * Carica la lista dei nomi delle classi che possono essere utilizzate come servizi
	 * scandendo tutti i package nella path indicata
	 * @param pckgname il nome del package in cui cercare
	 * @param baseDirPath la path dove risiedono i file .jar
	 * @return una lista di nome di classi trovate
	 * @throws ClassNotFoundException in caso di errori durante il tentativo di controllo
	 */
	private static ArrayList<String> getClassesFromFileJarFile(String pckgname, String baseDirPath) throws ClassNotFoundException {
		ArrayList<String> services = new ArrayList<String>();
		String path = pckgname.replace('.', '/') + "/";
		File mF = new File(baseDirPath);
		String[] files = mF.list();
		ArrayList<String> jars = new ArrayList<String>();
		for (int i = 0; i < files.length; i++)
			if (files[i].endsWith(".jar")) jars.add(files[i]);
		
		for (int i = 0; i < jars.size(); i++) {
			try	{
				JarFile currentFile = new JarFile(jars.get(i).toString());
				for (Enumeration<JarEntry> e = currentFile.entries(); e.hasMoreElements(); ) {
					JarEntry current = (JarEntry) e.nextElement();
					if(current.getName().length() > path.length() && current.getName().substring(0, path.length()).equals(path) && current.getName().endsWith(".class")) {
						String serviceName = current.getName().replaceAll("/", ".").replace(".class", "");
						if ( IRMIService.class.isAssignableFrom(Class.forName(serviceName)) &&
								!Class.forName(serviceName).isInterface() && !serviceName.endsWith("_Stub") ) {
							services.add(serviceName);
						}
					}
				}
			} catch (IOException e) {}
		}
		return services;
	}
}
