package gccb.cliente.model;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.cliente.net.ClienteServiceDelegate;
import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;

/**
 * Modello relativo alla schermata di login
 * @author Francesco Capozzo
 *
 */
public class ClienteLoginModel extends YModel {

	private String username = "";
	private String password = "";
	
	/**
	 * Esegue la connessione al server
	 * @throws GccbNetException in caso di errore del server
	 * @throws ServerNonDisponibileException in caso di errore di connessione
	 * @throws CredenzialiNonValideException se le credenziali indicate non sono valide
	 */
	public void connect() throws ServerNonDisponibileException, CredenzialiNonValideException, GccbNetException {
		ClienteServiceDelegate.connect(username, password);
	}

	/**
	 * Indica la username impostata
	 * @return username
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
		notifyObservers("username");
	}

	/**
	 * Indica la password importata
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
		notifyObservers("password");
	}
	
}
