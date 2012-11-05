package gccb.impiegato.model;

import java.util.Collections;
import java.util.List;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.ContoCorrenteBancario;
import gccb.ContoCorrenteConFido;
import gccb.Data;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.IData;
import gccb.IPersona;
import gccb.exception.OltreLimitePrelievoException;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.impiegato.net.ImpiegatoServiceDelegate;

/**
 * Modello per la gestione delle operazioni legate ai conti
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoContiModel extends YModel {

	//=== Pattern singleton
	private static ImpiegatoContiModel singleton = null;
	/**
	 * Ritorna l'istanza di default del modello
	 * @return l'istanza di default
	 */
	public static ImpiegatoContiModel getInstace() {
		if ( singleton == null )
			singleton = new ImpiegatoContiModel();
		return singleton;
	}
	/**
	 * Ritorna una nuova istanza indipendente
	 * @return una nuova istanza di {@link ImpiegatoContiModel}
	 */
	public static ImpiegatoContiModel getInstanceNew() {
		return new ImpiegatoContiModel();
	}
	private ImpiegatoContiModel() {}
	//===
	
	// campi visualizzazione
	private IContoCorrenteBancario conto;
	private String contoTipo;
	private float contoFido;
	
	// campi ricerca
	private List<IContoCorrenteBancario> searchConti;
	private int searchIdConto;
	private String searchNome;
	private String searchCognome;
	private boolean searchAncheSimili = false;
	private int searchBy = 0;
	
	// campi creazione
	private String newContoTipo;
	private float newFido;
	private IPersona newIntestatario;
	private boolean newDataAttuale;
	private float newSaldo;
	private String newPassword;
	private int newGiorno;
	private String newMese;
	private int newAnno;
	
	//campi operazioni
	private float newPrelievo;
	private float newVersamento;
	
	
	
	/**
	 * Esegue una ricerca fra i conti in base ai campi specificati
	 * di tipo searchXXX
	 * Utilizza ImpiegatoServiceDelegate per l'accesso ai dati
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void doSearch() throws GccbNetException {
		if ( searchBy == 0) {
			setSearchConti(ImpiegatoServiceDelegate.search(searchIdConto));
		} else if ( searchBy == 1) {
			setSearchConti(ImpiegatoServiceDelegate.search(
					(searchNome != null ? searchNome : ""),
					(searchCognome != null ? searchCognome : ""),
					searchAncheSimili));
		}
	}
	/**
	 * Esegue la ricerca dei conti associati una cliente
	 * @param idPersona l'id persona del cliente
	 * @throws GccbNetException in caso di errori del server 
	 * @throws ServerNonDisponibileException  in caso di errori di connessione
	 */
	public void doSearchByIdPersona(int idPersona) throws ServerNonDisponibileException, GccbNetException {
		setSearchConti(ImpiegatoServiceDelegate.searchByIdPersona(idPersona));
	}
	
	/**
	 * Aggiorna i dettagli del conto selezionato
	 * @throws GccbNetException in caso di errori del server
	 * @throws DatiNonTrovatiException in caso di errori di connessione
	 */
	public void aggiornaConto() throws DatiNonTrovatiException, GccbNetException  {
		if ( conto != null ) {
			setConto(ImpiegatoServiceDelegate.getConto(conto.getIdConto()));
		} else 
			throw new IllegalArgumentException("Nessun conto selezionato");
	}
	
	/**
	 * Chiude il conto selezionato
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void doChiudiConto() throws ServerNonDisponibileException, GccbNetException {
		if ( conto != null ) {
			ImpiegatoServiceDelegate.chiudiConto(conto);
		} else 
			throw new IllegalArgumentException("Nessun conto selezionato");
	}
	
	/**
	 * Esegue la modifica del fido associato al conto
	 * se il conto e' di tipo {@link IContoCorrenteConFido}
	 * @throws GccbNetException in caso di errori del server 
	 * @throws DatiNonTrovatiException  se il conto non esiste
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void doModificaConto() throws ServerNonDisponibileException, DatiNonTrovatiException, GccbNetException {
		try {
			IContoCorrenteConFido cFido = (IContoCorrenteConFido) conto;
			setConto(ImpiegatoServiceDelegate.modificaConto(cFido, contoFido));
		} catch (ClassCastException cce) {
			throw new IllegalArgumentException("Conto non modificabile", cce);
		} catch (NullPointerException npe) {
			throw new IllegalArgumentException("Nessun conto selezionato");
		}
	}
	
	/**
	 * Aggiunge un nuovo conto corrente in base ai dati
	 * possieduti dai cambi di tipo getNewXXX
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione 
	 */
	public void doAggiungiConto() throws ServerNonDisponibileException, GccbNetException {
		IContoCorrenteBancario newConto;
		IData newData = ( newDataAttuale ? Data.today() : new Data(newGiorno, newMese, newAnno) );
		if ( newContoTipo.equals("CON FIDO") ) {
			newConto = new ContoCorrenteConFido(newIntestatario, newSaldo, newFido, newData);
		} else {
			newConto = new ContoCorrenteBancario(newIntestatario, newSaldo, newData);
		}
		setConto(ImpiegatoServiceDelegate.addConto(newConto, newPassword));
	}
	
	/**
	 * Esegue un versamento sul conto selezionato
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void doVersamento() throws ServerNonDisponibileException, GccbNetException {
		ImpiegatoServiceDelegate.doVersamento(conto, newVersamento);
	}
	
	/**
	 * Esegue un prelievo sul conto selezionato
	 * @throws OltreLimitePrelievoException se l'importo non e' prelevabile
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void doPrelievo() throws ServerNonDisponibileException, GccbNetException, OltreLimitePrelievoException {
		if ( conto.isPrelevabile(newPrelievo) ) {
			ImpiegatoServiceDelegate.doPrelievo(conto, newPrelievo);
		} else {
			throw new IllegalArgumentException("Importo non prelevabile dal conto");
		}
	}
	
	/**
	 * Resetta tutti i campi contenuti nel modello
	 */
	public void resetAll() {
		resetVisualizzazione();
		resetRicerca();
		resetCreazione();
	}
	
	/**
	 * Resetta i campi per la visualizzazione
	 */
	public void resetVisualizzazione() {
		setConto(null);
		setContoTipo("");
		setContoFido(0);
	}
	
	/**
	 * Resetta i campi per la ricerca
	 */
	public void resetRicerca() {
		setSearchConti(Collections.EMPTY_LIST);
		setSearchIdConto(0);
		setSearchNome("");
		setSearchCognome("");
		setSearchAncheSimili(false);
		setSearchBy(0);
	}
	
	/**
	 * Resetta i campi per la creazione
	 */
	public void resetCreazione() {
		setNewContoTipo("NORMALE");
		setNewFido(0);
		setNewIntestatario(null);
		setNewDataAttuale(true);
		setNewSaldo(0);
		setNewPassword("");
	}
	
	// getter & setter
	
	/**
	 * Ritorna il conto selezionato
	 */
	public IContoCorrenteBancario getConto() {
		return conto;
	}
	/**
	 * Imposta il nuovo conto selezionato e notifica
	 * del cambiamento
	 * @param conto
	 */
	public void setConto(IContoCorrenteBancario conto) {
		this.conto = conto;
		setContoTipo(conto instanceof IContoCorrenteConFido ? "CON FIDO" : "NORMALE");
		setContoFido(conto instanceof IContoCorrenteConFido ? ((IContoCorrenteConFido)conto).getFido() : 0f);
		notifyObservers("conto");
	}
	/**
	 * Ritorna il tipo di conto selezionato
	 * @return una stringa rappresentante il tipo di conto
	 */
	public String getContoTipo() {
		return contoTipo;
	}
	/**
	 * Imposta il tipo di conto selezionato e notifica
	 * del cambiamento
	 * @param contoTipo il nuovo tipo conto
	 */
	public void setContoTipo(String contoTipo) {
		this.contoTipo = contoTipo;
		notifyObservers("contoTipo");
	}
	/**
	 * Ritorna il fido associato al conto
	 * @return fido
	 */
	public float getContoFido() {
		return contoFido;
	}
	/**
	 * Imposta un nuovo fido al conto selezionato
	 * @param contoFido il nuovo fido
	 */
	public void setContoFido(float contoFido) {
		this.contoFido = contoFido;
		notifyObservers("contoFido");
	}
	/**
	 * Ritorna la lista di conti trovati
	 * @return la lista di conti
	 */
	public List<IContoCorrenteBancario> getSearchConti() {
		return searchConti;
	}
	/**
	 * Imposta una nuova lista di conti trovati
	 * e notifica del cambiamento
	 * @param searchConti
	 */
	public void setSearchConti(List<IContoCorrenteBancario> searchConti) {
		this.searchConti = searchConti;
		notifyObservers("searchConti");
	}
	/**
	 * Id del conto per la ricerca
	 * @return l'id del conto
	 */
	public int getSearchIdConto() {
		return searchIdConto;
	}
	/**
	 * Imposta l'id del conto da cercare
	 * @param searchIdConto
	 */
	public void setSearchIdConto(int searchIdConto) {
		this.searchIdConto = searchIdConto;
		notifyObservers("searchIdConto");
	}
	/**
	 * Ritorna il nome in base al quale effettuare la ricerca
	 * @return il nome
	 */
	public String getSearchNome() {
		return searchNome;
	}
	/**
	 * Imposta il nome in base al quale effettuale la ricerca
	 * @param searchNome il nuovo nome
	 */
	public void setSearchNome(String searchNome) {
		this.searchNome = searchNome;
		notifyObservers("searchNome");
	}
	/**
	 * Ritorna il cognome in base al quale effettuare la ricerca
	 * @return il cognome
	 */
	public String getSearchCognome() {
		return searchCognome;
	}
	/**
	 * Imposta il cognome in base al quale effettuare la ricerca
	 * @param searchCognome il nuovo cognome
	 */
	public void setSearchCognome(String searchCognome) {
		this.searchCognome = searchCognome;
		notifyObservers("searchCognome");
	}
	/**
	 * Indica se effettuare la ricerca anche dei campi simili
	 * @return ammettere valori simili
	 */
	public boolean isSearchAncheSimili() {
		return searchAncheSimili;
	}
	/**
	 * Imposta se ammettere valori simili nella ricerca
	 * @param searchAncheSimili ammettere?
	 */
	public void setSearchAncheSimili(boolean searchAncheSimili) {
		this.searchAncheSimili = searchAncheSimili;
		notifyObservers("searchAncheSimili");
	}
	/**
	 * Ritorna il tipo di ricerca da effettuare
	 * @return il tipo di ricerca
	 */
	public int getSearchBy() {
		return searchBy;
	}
	/**
	 * Imposta il nuovo tipo di ricerca da effettuare
	 * @param searchBy il nuovo tipo
	 */
	public void setSearchBy(int searchBy) {
		this.searchBy = searchBy;
		notifyObservers("searchBy");
	}
	/**
	 * Ritorna il tipo del nuovo conto
	 * @return tipo
	 */
	public String getNewContoTipo() {
		return newContoTipo;
	}
	/**
	 * Impost ail nuovo tipo di conto da creare
	 * @param newConFido il nuovo tipo
	 */
	public void setNewContoTipo(String newConFido) {
		this.newContoTipo = newConFido;
		notifyObservers("newContoTipo");
	}
	/**
	 * Ritorna il nuovo valore di fido
	 * @return il valore
	 */
	public float getNewFido() {
		return newFido;
	}
	/**
	 * Imposta il nuovo valore del fido
	 * @param newFido il nuovo valore
	 */
	public void setNewFido(float newFido) {
		this.newFido = newFido;
		notifyObservers("newFido");
	}
	/**
	 * Ritorna l'intestatario del nuovo conto
	 * @return una istanza di {@link IPersona} rappresentante il nuovo intestatario
	 */
	public IPersona getNewIntestatario() {
		return newIntestatario;
	}
	/**
	 * Imposta il nuovo intestatario
	 * @param newIntestatario l'intestatario
	 */
	public void setNewIntestatario(IPersona newIntestatario) {
		this.newIntestatario = newIntestatario;
		notifyObservers("newIntestatario");
	}
	/**
	 * Indica se usare la data attuale per l'apertura del conto
	 * @return usare la data attuale?
	 */
	public boolean isNewDataAttuale() {
		return newDataAttuale;
	}
	/**
	 * Imposta se usare o meno la data attuale per l'apertura del conto
	 * @param newDataAttuale
	 */
	public void setNewDataAttuale(boolean newDataAttuale) {
		this.newDataAttuale = newDataAttuale;
		notifyObservers("newDataAttuale");
	}
	/**
	 * Ritorna il saldo del nuovo conto
	 * @return il saldo
	 */
	public float getNewSaldo() {
		return newSaldo;
	}
	/**
	 * Imposta il saldo del nuovo conto
	 * @param newSaldo il nuovo saldo
	 */
	public void setNewSaldo(float newSaldo) {
		this.newSaldo = newSaldo;
		notifyObservers("newSaldo");
	}
	/**
	 * Ritorna la nuova password del conto
	 * @return la nuova password
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * Imposta una nuova password al conto
	 * @param newPassword la nuova password
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		notifyObservers("newPassword");
	}
	/**
	 * Ritorna il nuovo giorno di creaizone del conto
	 * @return il nuovo giorno del mese
	 */
	public int getNewGiorno() {
		return newGiorno;
	}
	/**
	 * Imposta il nuovo giorno per la creazione del conto
	 * @param newGiorno il nuovo giorno
	 */
	public void setNewGiorno(int newGiorno) {
		if ( newGiorno >= 1 && newGiorno <= 31 ) {
			this.newGiorno = newGiorno;
		}
		notifyObservers("newGiorno");
	}
	/**
	 * Ritorna il mese per l'apertura del nuovo conto
	 * @return il mese
	 */
	public String getNewMese() {
		return newMese;
	}
	/**
	 * Imposta il mese in cui aprire il nuovo conto
	 * @param newMese il nuovo mese
	 */
	public void setNewMese(String newMese) {
		this.newMese = newMese;
		notifyObservers("newMese");
	}
	/**
	 * Indica l'anno in cui aprire il conto
	 * @return il nuovo anno
	 */
	public int getNewAnno() {
		return newAnno;
	}
	/**
	 * Imposta l'anno in cui aprire il nuovo conto
	 * @param newAnno il nuovo anno
	 */
	public void setNewAnno(int newAnno) {
		this.newAnno = newAnno;
		notifyObservers(newAnno);
	}
	/**
	 * Indica il valore del prelievo da effettuare
	 * @return il valore
	 */
	public float getNewPrelievo() {
		return newPrelievo;
	}
	/**
	 * Imposta il valore del nuovo prelievo da effettuare
	 * @param newPrelievo il nuovo valore
	 */
	public void setNewPrelievo(float newPrelievo) {
		this.newPrelievo = newPrelievo;
		notifyObservers("newPrelievo");
	}
	/**
	 * Indica il valore del nuovo versamento da effettuare
	 * @return il valore del versamento
	 */
	public float getNewVersamento() {
		return newVersamento;
	}
	/**
	 * Imposta un nuovo valore da versare sul conto selezionato
	 * @param newVersamento il nuovo valore
	 */
	public void setNewVersamento(float newVersamento) {
		this.newVersamento = newVersamento;
		notifyObservers("newVersamento");
	}
}
