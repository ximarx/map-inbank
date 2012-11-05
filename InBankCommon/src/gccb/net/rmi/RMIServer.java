package gccb.net.rmi;

import gccb.database.ServerDB;
import gccb.exception.db.GccbDbException;
import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.log.LogManager;
import gccb.net.rmi.services.IRMIService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Gestisce i servizi registrati, i client autenticati e le connessioni tramite rmi
 * @author Francesco Capozzo
 *
 */
class RMIServer {

	private HashMap<Integer, IClient> clients = new HashMap<Integer, IClient>();
	private Map<String, IRMIService> services = new HashMap<String, IRMIService>();
	private Map<String, RMIClientType> services_auth = new HashMap<String, RMIClientType>();
	private IServiceFactory remoteInterface;
	
	private Object LOCK = new Object();
	
	/**
	 * Crea il server rmi inizializzando il ServiceFactory
	 * @throws RemoteException
	 */
	RMIServer() throws RemoteException {
		remoteInterface = new ServiceFactory();
	}
	
	/**
	 * Ritorna un riferimento al serviceFactory
	 * @return il serviceFactory
	 */
	IServiceFactory getRemoteInterface() {
		return remoteInterface;
	}
	
	/**
	 * Controlla se un servizio è registrato
	 * @param serviceName il nome del servizio
	 * @return l'esito del controllo
	 */
	boolean isRegistered(String serviceName) {
		return services.containsKey(serviceName);
	}

	/**
	 * Ritorna il tipo di client verso cui un servizio risulta
	 * registrato
	 * @param serviceName il nome di un servizio registrato
	 * @return il tipo di client a cui è consentito accedere
	 */
	RMIClientType registrationType(String serviceName) {
		return services_auth.get(serviceName);
	}
	
	/**
	 * Registra un nuovo servizio con il nome indicato e verso il tipo di client indicato
	 * @param serviceName il nome con cui registrare il servizio
	 * @param service il servizio da registrare
	 * @param ctype il tipo di client a cui permettere l'accesso al servizio
	 */
	void registerService(String serviceName, IRMIService service, RMIClientType ctype) {
		if ( !services.containsKey(serviceName) ) {
			services.put(serviceName, service);
			services_auth.put(serviceName, ctype);
			LogManager.info("Attivazione servizio.\n" +
					"Servizio: "+serviceName+"\n" +
					"Modalita':"+ctype);
		}
	}
	/**
	 * Rimuove la registrazione di un servizio in base al nome con cui è stato registrato
	 * @param serviceName il nome del servizio da rimuovere
	 * @return l'esito della rimozione del servizio
	 */
	boolean unregisterService(String serviceName) {
		if ( services.containsKey(serviceName) ) {
			RMIClientType ct = services_auth.get(serviceName);
			for ( Integer c : clients.keySet() ) {
				try {
					if ( clients.get(c).getClientType() == ct.ordinal() ) {
						closeClientConnection(c);
					}
				} catch (RemoteException re) {
					// se sono qui, allora il client non e' contattabile
					// non me ne preoccupo perche verrà eliminato dal
					// ciclo di pulizia
				}
			}
			services.remove(serviceName);
			services_auth.remove(serviceName);
			LogManager.info("Disattivazione servizio.\n" +
					"Servizio:" + serviceName);
			return true;
		} else {
			LogManager.error("Richiesta disattivazione servizio non attivo.\n" +
					"Servizio:" + serviceName);
			return false;
		}
	}
	/**
	 * Rimuove un client da quelli autenticati
	 * @param idAgente l'id dell'agente da rimuovere
	 */
	void removeClient(int idAgente) {
		synchronized (LOCK) {
			clients.remove(idAgente);
		}
	}
	/**
	 * Aggiunge un nuovo client alla lista degli autenticati
	 * @param idAgente l'id del client
	 * @param client un riferimento al client
	 */
	void addClient(final int idAgente, IClient client) {
		synchronized (LOCK) {
			if ( clients.containsKey(idAgente) ) {
				closeClientConnection(idAgente);
			}
			clients.put(idAgente, client);
		}
	}
	/**
	 * Ritorna l'array contententi i client attualmente connessi
	 * @return l'insieme di client
	 */
	IClient[] getClients() {
		synchronized (LOCK) {
			return clients.values().toArray(new IClient[]{});
		}
	}

	/**
	 * Disconnette un client autorizzato in base al suo idAgente con cui
	 * risulta autenticato
	 * 
	 * @param clientIndex l'idAgente del client connesso
	 * @return vero se il client è stato disconnesso, falso altrimenti
	 */
	public boolean closeClientConnection(int clientIndex) {
		synchronized (LOCK) {
			try {
				clients.get(clientIndex).forceLogout();
				try {
					clients.get(clientIndex).ping();
					return false;
				} catch (RemoteException ee) {
					removeClient(clientIndex);
					return true;
				}
			} catch (RemoteException e) {
				// il client è gia offline
				removeClient(clientIndex);
				return true;
			} catch (Exception eee) {
				// non esiste il client in quella posizione
				return false;
			}
		}
	}
	
	/**
	 * Controlla la lista di utenti connessi rimuovendo tutti quelli
	 * per i quali risulta interrotta la connessione
	 */
	synchronized public void closeBrokenConnection() {
		synchronized (LOCK) {
			Set<Integer> keys = clients.keySet();
			for ( Integer i : keys) {
				try {
					clients.get(i).ping();
				} catch (RemoteException e) {
					LogManager.warning("Rimozione client disconnesso:\n" +
							"Client" + clients.get(i));
					removeClient(i);
				} catch (NullPointerException npe) {}
			}
		}
	}
	
	/**
	 * Implementazione del {@link IServiceFactory} utilizzata per esporre i servizi registrati
	 * ai client
	 * @author Francesco Capozzo
	 *
	 */
	private class ServiceFactory extends UnicastRemoteObject implements IServiceFactory {

		private static final long serialVersionUID = -1297430893312584549L;

		protected ServiceFactory() throws RemoteException {
			super();
		}

		/*
		 * (non-Javadoc)
		 * @see gccb.net.rmi.IServiceFactory#getAvailableServices(gccb.net.rmi.IClient)
		 */
		public String[] getAvailableServices(IClient client) throws RemoteException, GccbNetException {
			if ( !client.getIdentificativo().equals("-1") && clients.get(Integer.valueOf(client.getIdentificativo())).equals(client) ) {
				try {
					LogManager.info("Client autenticato richiede lista servizi.\n" +
							"Client: " + client);
					return (String[]) services.keySet().toArray();
				} catch (Exception e) {
					throw new GccbNetException("Lista servizi non disponibile");
				}
			} else {
				LogManager.warning("Client non autenticato richiede lista servizi.\n" +
						"Client: "+ client);
				throw new CredenzialiNonValideException("Client non autenticato");
			}
		}

		/*
		 * (non-Javadoc)
		 * @see gccb.net.rmi.IServiceFactory#getServiceReference(java.lang.String, gccb.net.rmi.IClient)
		 */
		public IRMIService getServiceReference(String serviceName, IClient client) throws RemoteException, CredenzialiNonValideException, GccbNetException {
			if ( !client.getIdentificativo().equals("-1") && clients.get(Integer.valueOf(client.getIdentificativo())).equals(client)  ) {
				if ( services.containsKey(serviceName) ) {
					if ( services_auth.get(serviceName).ordinal() == client.getClientType() ) {
						LogManager.info("Accesso a servizio autorizzato per client autenticato.\n" +
								"Servizio: " + serviceName + "\n" +
								"ID Agente:" + client.getIdentificativo());
						return (IRMIService) services.get(serviceName);
					} else {
						LogManager.warning("Client richiede accesso a servizio non accessibile.\n" +
								"Servizio: " + serviceName + "\n" +
								"Client: " + client);
						throw new CredenzialiNonValideException("Autorizzazione insufficiente per accedere al servizio "+serviceName);
					}
				} else {
					LogManager.warning("Client richiede accesso a servizio non attivo.\n" +
							"Servizio: " + serviceName + "\n" +
							"Client: "+ client);
					throw new GccbNetException("Servizio non disponibile: "+serviceName);
				}
			} else {
				LogManager.warning("Client non autenticato richiede accesso a servizio.\n" +
						"Servizio: " + serviceName + "\n" +
						"Client: "+ client);
				throw new CredenzialiNonValideException("Client non autenticato");
			}
		}

		/*
		 * (non-Javadoc)
		 * @see gccb.net.rmi.IServiceFactory#registerClient(java.lang.String, java.lang.String, gccb.net.rmi.IClient)
		 */
		public int registerClient(String username, String password, IClient client) throws GccbNetException, CredenzialiNonValideException {
			try {
				int idAgente = ServerDB.getReference().checkAuth(username, password, client.getClientType());
				if ( idAgente != -1 ) {
					addClient(idAgente, client);
					LogManager.info("Login client autorizzato. Id agente: "+idAgente);
					return idAgente;
				} else {
					LogManager.warning("Credenziali fornite non valide.\n" +
							"Client: " + client + "\n" +
							"Credenziali: " + username + " - " + password);
					throw new CredenzialiNonValideException("Credenziali non valide");
				}
			} catch (GccbDbException e) {
				LogManager.error("Impossibile effettuare autenticazione.\n" +
						"Exception: " + e.getMessage() + "\n" +
						"Client: " + client);
				throw new GccbNetException("Server Error: impossibile effettuare autenticazione");
			} catch (RemoteException re ) {
				LogManager.error("Il client non e' piu' connesso. Client: " + client);
				return -1;
			}
		}

		/*
		 * (non-Javadoc)
		 * @see gccb.net.rmi.IServiceFactory#unregisterClient(gccb.net.rmi.IClient)
		 */
		public boolean unregisterClient(IClient client) throws RemoteException {
			removeClient(Integer.valueOf(client.getIdentificativo()));
			LogManager.info("Logout client " + client.getIdentificativo());
			return !(clients.containsValue(client));
		}
	}
}
