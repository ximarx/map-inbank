package gccb.impiegato.model;

import java.util.Collections;
import java.util.List;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.IPersona;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.impiegato.net.ImpiegatoServiceDelegate;

/**
 * Modello per la gestione delle operazioni associate ai correntisti
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoPersoneModel extends YModel {

	//=== Pattern singleton
	private static ImpiegatoPersoneModel singleton = null;
	/**
	 * Ritorna l'istanza di default del modello
	 * @return l'istanza condivisa
	 */
	public static ImpiegatoPersoneModel getInstace() {
		if ( singleton == null )
			singleton = new ImpiegatoPersoneModel();
		return singleton;
	}
	/**
	 * Ritorna una nuova istanza indipendente
	 * @return una nuova istanza
	 */
	public static ImpiegatoPersoneModel getInstanceNew() {
		return new ImpiegatoPersoneModel();
	}
	private ImpiegatoPersoneModel() {}
	//===	
	
	private List<IPersona> persone;
	private IPersona persona;
	
	private String newNome;
	private String newCognome;
	private String newIndirizzo;
	
	private int searchIdPersona;
	private String searchCognome;
	private String searchNome;
	private int searchBy = 0;
	private boolean searchAncheSimili = false;
	
	
	
	/**
	 * Esegue una ricerca fra i conti in base ai campi specificati
	 * di tipo searchXXX
	 * Utilizza ImpiegatoServiceDelegate per l'accesso ai dati
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void doSearch() throws ServerNonDisponibileException, GccbNetException {
		if ( searchBy == 0) {
			setPersone(ImpiegatoServiceDelegate.searchPersone(searchIdPersona));
		} else if ( searchBy == 1) {
			setPersone(ImpiegatoServiceDelegate.searchPersone(
					(searchNome != null ? searchNome : ""),
					(searchCognome != null ? searchCognome : ""),
					searchAncheSimili));
		}
	}
	
	/**
	 * Aggiunge un nuovo cliente
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws RemoteDataException in caso di errore
	 */
	public void aggiungiPersona() throws ServerNonDisponibileException, GccbNetException {
		setPersona(ImpiegatoServiceDelegate.addPersona(newNome, newCognome, newIndirizzo));
	}
	
	/**
	 * Resetta tutti campi del modello
	 */
	public void resetAll() {
		resetNew();
		resetSearch();
		resetVisualizzazione();
	}
	/**
	 * Resetta i campi per la creazione
	 */
	public void resetNew() {
		setNewNome("");
		setNewCognome("");
		setNewIndirizzo("");
	}
	/**
	 * Resetta i campi di ricerca
	 */
	public void resetSearch() {
		setSearchAncheSimili(false);
		setSearchBy(0);
		setSearchCognome("");
		setSearchIdPersona(0);
		setSearchNome("");
	}
	/**
	 * Resetta i campi di visualizzazione
	 */
	public void resetVisualizzazione() {
		setPersona(null);
		setPersone(Collections.EMPTY_LIST);
	}
	
	/**
	 * Ritorna la lista di persone trovate
	 * @return la lista
	 */
	public List<IPersona> getPersone() {
		return persone;
	}
	/**
	 * Imposta una nuova lista di persone trovate
	 * e notifica del cambiamento
	 * @param persone
	 */
	public void setPersone(List<IPersona> persone) {
		this.persone = persone;
		notifyObservers("persone");
	}
	/**
	 * Ritorna la persona selezionata
	 * @return la persona
	 */
	public IPersona getPersona() {
		return persona;
	}
	/**
	 * Imposta un nuovo correntista selezionato
	 * @param persona il nuovo correntista
	 */
	public void setPersona(IPersona persona) {
		this.persona = persona;
		notifyObservers("persona");
	}
	/**
	 * Ritorna il valore del nome del nuovo correntista
	 * @return il nome
	 */
	public String getNewNome() {
		return newNome;
	}
	/**
	 * Imposta il nome del nuovo correntista
	 * @param newNome il nuovo nome
	 */
	public void setNewNome(String newNome) {
		this.newNome = newNome;
		notifyObservers("newNome");
	}
	/**
	 * Ritorna il valore del cognome del nuovo correntista
	 * @return il nuovo cognome
	 */
	public String getNewCognome() {
		return newCognome;
	}
	/**
	 * Imposta un nuovo valore come cognome del correntista
	 * @param newCognome il nuovo cognome
	 */
	public void setNewCognome(String newCognome) {
		this.newCognome = newCognome;
		notifyObservers("newCognome");
	}
	/**
	 * Ritorna il valore dell'indirizzo del nuovo correntista
	 * @return l'indirizzo
	 */
	public String getNewIndirizzo() {
		return newIndirizzo;
	}
	/**
	 * Imposta l'indirizzo del nuovo correntista
	 * @param newIndirizzo l'indirizzo
	 */
	public void setNewIndirizzo(String newIndirizzo) {
		this.newIndirizzo = newIndirizzo;
		notifyObservers("newIndirizzo");
	}
	/**
	 * Ritorna l'id di ricerca per il correntista
	 * @return l'id persona
	 */
	public int getSearchIdPersona() {
		return searchIdPersona;
	}
	/**
	 * Imposta un nuovo id per la ricerca tramite id del correntista
	 * @param searchIdPersona il nuovo id da cercare
	 */
	public void setSearchIdPersona(int searchIdPersona) {
		this.searchIdPersona = searchIdPersona;
		notifyObservers("searchIdPersona");
	}
	/**
	 * Ritorna il valore di ricerca del cognome
	 * @return il cognome
	 */
	public String getSearchCognome() {
		return searchCognome;
	}
	/**
	 * Imposta un nuovo valore di ricerca del cognome
	 * @param searchCognome il nuovo cognome
	 */
	public void setSearchCognome(String searchCognome) {
		this.searchCognome = searchCognome;
		notifyObservers("searchCognome");
	}
	/**
	 * Ritorna il valore di ricerca del nome
	 * @return il nome
	 */
	public String getSearchNome() {
		return searchNome;
	}
	/**
	 * Imposta un nuovo valore di ricerca del nome
	 * @param searchNome il nuovo nome
	 */
	public void setSearchNome(String searchNome) {
		this.searchNome = searchNome;
		notifyObservers("searchNome");
	}
	/**
	 * Indica il tipo di ricerca da effettuare
	 * @return il tipo
	 */
	public int getSearchBy() {
		return searchBy;
	}
	/**
	 * Imposta il nuovo tipo di ricerca
	 * @param searchBy il nuovo tipo
	 */
	public void setSearchBy(int searchBy) {
		this.searchBy = searchBy;
		notifyObservers("searchBy");
	}
	/**
	 * Indica se cercare anche i valori simili
	 * @return valori simili?
	 */
	public boolean getSearchAncheSimili() {
		return searchAncheSimili;
	}
	/**
	 * Imposta se cercare anche valori simili
	 * @param searchAncheSimili il nuovo ricerca anche simili
	 */
	public void setSearchAncheSimili(boolean searchAncheSimili) {
		this.searchAncheSimili = searchAncheSimili;
		notifyObservers("searchAncheSimili");
	}
}
