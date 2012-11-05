package gccb.server.model;

import java.util.List;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.IImpiegato;
import gccb.server.net.ServerServiceDelegate;

/**
 * Modello del widget per la lista impiegati
 * @author Francesco Capozzo
 *
 */
public class ServerListaImpiegatiWidgetModel extends YModel {

	private List<IImpiegato> impiegati;
	private IImpiegato impiegato;
	
	private String newUsername;
	private String newPassword;
	private String newVisualizzato;

	/**
	 * Resetta i campi relativi all'inserimento di un nuovo impiegato
	 */
	public void resetNew() {
		setNewUsername("");
		setNewPassword("");
		setNewVisualizzato("");
	}
	
	/**
	 * Aggiorna la lista di impiegati
	 */
	public void aggiornaListaImpiegati() {
		setImpiegati(ServerServiceDelegate.listaImpiegati());
	}
	
	/**
	 * Modifica la password dell'impiegato indicato da {@link #getImpiegato()}
	 * @param newPassword la nuova password
	 */
	public void modificaPassword(String newPassword) {
		ServerServiceDelegate.modificaPasswordImpiegato(impiegato.getIdImpiegato(), newPassword);
	}
	
	/**
	 * Aggiunge un nuovo impiegato alla lista
	 */
	public void aggiungiImpiegato() {
		if ( ServerServiceDelegate.aggiungiImpiegato(newUsername, newPassword, newVisualizzato) ) {
			aggiornaListaImpiegati();
		}
	}

	/**
	 * Rimuove l'impiegato indicato da {@link #getImpiegato()}
	 */
	public void rimuoviImpiegato() {
		ServerServiceDelegate.rimuoviImpiegato(impiegato.getIdImpiegato());
		aggiornaListaImpiegati();
	}
	
	/**
	 * Ritorna la lista di impiegati
	 * @return la lista di {@link IImpiegato}
	 */
	public List<IImpiegato> getImpiegati() {
		return impiegati;
	}

	/**
	 * Imposta una nuova lista di impiegati
	 * @param impiegati la nuova lista
	 */
	public void setImpiegati(List<IImpiegato> impiegati) {
		this.impiegati = impiegati;
		notifyObservers("impiegati");
	}

	/**
	 * Ritorna l'impiegato selezionato
	 * @return una istanza di {@link IImpiegato}
	 */
	public IImpiegato getImpiegato() {
		return impiegato;
	}

	/**
	 * Imposta il nuovo impiegato selezionato e notifica agli osservatori
	 * @param impiegato il nuovo impiegato
	 */
	public void setImpiegato(IImpiegato impiegato) {
		this.impiegato = impiegato;
		notifyObservers("impiegato");
	}

	/**
	 * Ritorna lo username del nuovo impiegato
	 * @return lo username
	 */
	public String getNewUsername() {
		return newUsername;
	}

	/**
	 * Imposta un nuovo username al nuovo impiegato
	 * @param newUsername il nuovo username
	 */
	public void setNewUsername(String newUsername) {
		this.newUsername = newUsername;
		notifyObservers("newUsername");
	}

	/**
	 * Ritorna la password del nuovo impiegato
	 * @return la nuova password
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Imposta una nuova password al nuovo impiegato
	 * @param newPassword la nuova password
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		notifyObservers("newPassword");
	}

	/**
	 * Indica la nuova etichetta dell'impiegato
	 * @return la nuova etichetta
	 */
	public String getNewVisualizzato() {
		return newVisualizzato;
	}

	/**
	 * Imposta una nuova etichetta al nuovo impieato
	 * @param newVisualizzato la nuova etichetta
	 */
	public void setNewVisualizzato(String newVisualizzato) {
		this.newVisualizzato = newVisualizzato;
		notifyObservers("newVisualizzato");
	}
	
	
}
