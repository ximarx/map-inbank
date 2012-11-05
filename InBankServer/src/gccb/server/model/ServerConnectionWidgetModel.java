package gccb.server.model;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.server.net.ServerServiceDelegate;

/**
 * Classe modello per il widget della barra di stato di connessione
 * @author Francesco Capozzo
 *
 */
public class ServerConnectionWidgetModel extends YModel {

	private String statoConnessione = "";
	private String dbStatus = "";
	private String rmiStatus = "";
	
	/**
	 * Indica il database con connesso
	 */
	public void dbConnected() {
		setDbStatus("Connesso a " + ServerServiceDelegate.getDbUsername() + "@" +
			ServerServiceDelegate.getDbHostAddress() + ":" +
			ServerServiceDelegate.getDbPort() + "/" +
			ServerServiceDelegate.getDbName());
		setStatoConnessione(getDbStatus() + " - " + getRmiStatus());
	}

	/**
	 * Indica il database come disconnesso
	 */
	public void dbDisconnected() {
		setDbStatus("Database non connesso");
		setStatoConnessione(getDbStatus() + " - " + getRmiStatus());
	}
	
	/**
	 * Indica il ServiceFactory come registrato
	 */
	public void rmiConnected() {
		setRmiStatus("Registrato su " + ServerServiceDelegate.getRmiHostAddress() + ":" +
				ServerServiceDelegate.getRmiPort() + "/" +
				ServerServiceDelegate.getRmiServiceName());
		setStatoConnessione(getDbStatus() + " - " + getRmiStatus());
	}

	/**
	 * Indica che il serviceFactory non è stato registrato
	 */
	public void rmiDisconnected() {
		setRmiStatus("Server non registrato");
		setStatoConnessione(getDbStatus() + " - " + getRmiStatus());
	}
	
	/**
	 * Indica lo stato di connessione
	 * @return stato connessione
	 */
	public String getStatoConnessione() {
		return statoConnessione;
	}

	/**
	 * Imposta un nuovo stato di connessione e notifica agli osservatori la modifica
	 * @param statoConnessione
	 */
	public void setStatoConnessione(String statoConnessione) {
		this.statoConnessione = statoConnessione;
		notifyObservers("statoConnessione");
	}

	/**
	 * Indica lo stato di connessione del db
	 * @return lo stato della connessione
	 */
	public String getDbStatus() {
		return dbStatus;
	}

	/**
	 * Imposta lo stato di connessione del db e notifica gli osservatori
	 * @param dbStatus il nuovo stato di connessione
	 */
	public void setDbStatus(String dbStatus) {
		this.dbStatus = dbStatus;
		notifyObservers("dbStatus");
	}

	/**
	 * Indica lo stato di registrazione del servicefactory
	 * @return lo stato del server rmi
	 */
	public String getRmiStatus() {
		return rmiStatus;
	}

	/**
	 * Imposta il nuovo stato di registrazione del serviceFactory e notifica gli osservatori
	 * @param rmiStatus
	 */
	public void setRmiStatus(String rmiStatus) {
		this.rmiStatus = rmiStatus;
		notifyObservers("rmiStatus");
	}
}