package gccb.impiegato.model;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.impiegato.net.ImpiegatoServiceDelegate;

/**
 * Modello per la gestione delle connessiona connessione la server e del frame principale
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoFrameModel extends YModel {

	private String username;
	private String password;
	
	/**
	 * Resetta i valori associati al modello
	 */
	public void cleanAll() {
		username = null;
		password = null;
		notifyObservers();
	}
	
	/**
	 * Effettua la connessione al server
	 * @throws CredenzialiNonValideException
	 * @throws GccbNetException
	 */
	public void connect() throws CredenzialiNonValideException, GccbNetException {
		ImpiegatoServiceDelegate.connect(username, password);
	}

	/**
	 * Indica lo username selezioanto
	 * @return lo username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Imposta una nuova username
	 * @param username la nuova username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Indica la password selezionata
	 * @return la password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Imposta una nuova password
	 * @param password la nuova password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
