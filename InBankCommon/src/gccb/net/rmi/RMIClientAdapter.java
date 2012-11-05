package gccb.net.rmi;

import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.net.rmi.services.IRMIService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe astratta che implementa i metodi principali comuni fra tutti i client
 * richiesti dall'interfaccia {@link IClient}
 * @author Francesco Capozzo
 *
 */
public abstract class RMIClientAdapter extends UnicastRemoteObject implements IClient {

	protected IServiceFactory rmiConnection = null;
	protected RMIClientType clientType;
	protected int clientID;
	private ILogoutAction logoutAction = null;	
	
	/**
	 * Costruttore di default
	 * @throws RemoteException
	 */
	RMIClientAdapter() throws RemoteException {
		super();
	}
	
	/**
	 * Inizializza il client impostando il riferimento al ServiceFactory
	 * a cui connettersi
	 * @param rmiServer il riferimento al serviceFactory
	 * @throws RemoteException in caso di errore di connessione
	 */
	RMIClientAdapter(IServiceFactory rmiServer) throws RemoteException {
		this();
		rmiConnection = rmiServer;
	}
	
	/**
	 * Esegue la connessione al server RMI tentando l'autenticazione tramite
	 * credenziali fornite
	 * @param username username per l'accesso al server
	 * @param password password corrispondente all'username indicato
	 * @throws GccbNetException in caso di errore interno del server
	 * @throws CredenzialiNonValideException se le credenziali indicate non risultano valide
	 * @throws ServerNonDisponibileException se la connessione non e' disponibile
	 */
	public void connetti(String username, String password) throws CredenzialiNonValideException, GccbNetException {
		try {
			if ( username == null || password == null || username.equals("") || password.equals("") )
				throw new IllegalArgumentException("Username o password non inserite");
			clientID = rmiConnection.registerClient(username, password, this);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Connessione al server non disponibile");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.IClient#ping()
	 */
	public boolean ping() {
		return true;
	}
	
	/**
	 * Esegue la disconnessione del client dal RMIServer 
	 */
	public void disconnetti() {
		rimuoviClient();
		rmiConnection = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.IClient#getClientType()
	 */
	public int getClientType() {
		return clientType.ordinal();
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.IClient#getIdentificativo()
	 */
	public String getIdentificativo() {
		return Integer.toString(clientID);
	}
	/**
	 * Esegue il logout del client dal server.
	 * Questo non chiude la connessione al server, semplicemente si limita a
	 * a rimuovere l'autenticazione ricevuta 
	 */
	protected void rimuoviClient() {
		try {
			rmiConnection.unregisterClient(this);
		} catch (RemoteException e) {
			// cmq va bene, significa che non sono piu collegato
		}
	}
	/**
	 * Richiede un servizio al server. E' richiesta l'autenticazione
	 * al server e che il client abbia le credenziali per accedere
	 * al servizio richiesto
	 * @param serviceName il nome del servizio richiesto
	 * @return un riferimento al servizio richiesto
	 * @throws GccbNetException in caso di errore interno del server
	 * @throws ServerNonDisponibileException  se la connessione al server non e' dispobile
	 * @throws CredenzialiNonValideException se le credenziali del server non sono sufficienti
	 */
	public IRMIService getService(String serviceName) throws CredenzialiNonValideException, GccbNetException, ServerNonDisponibileException {
		try {
			return rmiConnection.getServiceReference(serviceName, this);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Connessione al server non disponibile");
		}
	}
	
	/**
	 * Imposta l'azione da eseguire in caso di disconnessione forzata del client
	 * @param newLogoutAction la nuova azione da eseguire
	 */
	public void setLogoutAction(ILogoutAction newLogoutAction) {
		logoutAction = newLogoutAction;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.net.rmi.IClient#forceLogout()
	 */
	public void forceLogout() throws RemoteException {
		if ( logoutAction != null ) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					logoutAction.execute();
				}
			}).start();
		} else {
			System.err.println("RMIClientAdapter::forceLogout() performed by server");
			System.exit(0);
		}
	}
}
