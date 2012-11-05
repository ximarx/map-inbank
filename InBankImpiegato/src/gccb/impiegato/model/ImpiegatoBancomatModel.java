package gccb.impiegato.model;

import java.util.Collections;
import java.util.List;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.Bancomat;
import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.impiegato.net.ImpiegatoServiceDelegate;

/**
 * Modello per le operazioni sui bancomat
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoBancomatModel extends YModel {

	//=== Pattern singleton
	private static ImpiegatoBancomatModel singleton = null;
	/**
	 * Ritorna l'istanza di default del modello
	 * @return l'istanza di default
	 */
	public static ImpiegatoBancomatModel getInstace() {
		if ( singleton == null )
			singleton = new ImpiegatoBancomatModel();
		return singleton;
	}
	/**
	 * Ritorna una nuova istanza indipendente del modello
	 * @return una nuova istanza
	 */
	public static ImpiegatoBancomatModel getInstanceNew() {
		return new ImpiegatoBancomatModel();
	}
	private ImpiegatoBancomatModel(){}
	//===
	
	private List<IBancomat> bancomats;
	private IBancomat bancomatSelezionato;
	private String newCodice;
	private float newMassimo = 250;
	private IContoCorrenteBancario conto;

	/**
	 * Resetta tutti i valori memorizzati nel modello 
	 */
	public void resetAll() {
		setBancomats(Collections.EMPTY_LIST);
		setBancomatSelezionato(null);
		setNewCodice("");
		setNewMassimo(250);
		setConto(null);
	}
	
	/**
	 * Aggiorna la lista dei bancomat
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws DatiNonTrovatiException se il conto non esiste
	 */
	public void aggiornaListaBancomat() throws DatiNonTrovatiException, ServerNonDisponibileException, GccbNetException {
		try {
			setBancomats(ImpiegatoServiceDelegate.getBancomats(conto.getIdConto()));
		} catch (NullPointerException npe) {}
	}
	/**
	 * Aggiunge un nuovo bancomat con le informazioni
	 * impostate nei campi getNewXXX del modello
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void aggiungiBancomat() throws ServerNonDisponibileException, GccbNetException {
		try {
			IBancomat newBancomat = new Bancomat(newCodice, newMassimo);
			ImpiegatoServiceDelegate.addBancomat(conto, newBancomat);
		} catch ( NullPointerException npe ) {
			throw new IllegalArgumentException("Nessuno conto selezionato");
		}
	}
	/**
	 * Rimuove il bancomat indicato da {@link #getBancomatSelezionato()}
	 * @throws GccbNetException in caso di errori del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void rimuoviBancomat() throws ServerNonDisponibileException, GccbNetException {
		ImpiegatoServiceDelegate.delBancomat(bancomatSelezionato);
	}
	/**
	 * Ritorna la lista dei bancomat
	 * @return la lista dei bancomat
	 */
	public List<IBancomat> getBancomats() {
		return bancomats;
	}
	/**
	 * Imposta una nuova lista dei bancomat e notifica
	 * gli osservatori
	 * @param bancomats la nuova lista
	 */
	public void setBancomats(List<IBancomat> bancomats) {
		this.bancomats = bancomats;
		notifyObservers("bancomats");
	}
	/**
	 * Indica il bancomat selezionato della lista
	 * @return un bancomat presente in {@link #getBancomats()}
	 */
	public IBancomat getBancomatSelezionato() {
		return bancomatSelezionato;
	}
	/**
	 * Imposta un nuovo bancomat come selezionato
	 * @param bancomat il bancomat selezionato
	 */
	public void setBancomatSelezionato(IBancomat bancomat) {
		this.bancomatSelezionato = bancomat;
		notifyObservers("bancomatSelezionato");
	}
	/**
	 * Ritorna il codice di un nuovo bancomat da inserire
	 * @return il nuovo codice
	 */
	public String getNewCodice() {
		return newCodice;
	}
	/**
	 * Imposta il nuovo codice di un bancomat da inserire
	 * @param newCodice il nuovo codice
	 */
	public void setNewCodice(String newCodice) {
		this.newCodice = newCodice;
		notifyObservers("newCodice");
	}
	/**
	 * Ritorna il massimo prelevabile del nuovo bancomat da inserire 
	 * @return il nuovo massimo prelevabile
	 */
	public float getNewMassimo() {
		return newMassimo;
	}
	/**
	 * Imposta il nuovo massimo prelevabile al bancomat da inserire
	 * @param newMassimo il nuovo massimo prelevabile
	 */
	public void setNewMassimo(float newMassimo) {
		this.newMassimo = newMassimo;
		notifyObservers("newMassimo");
	}
	/**
	 * Ritorna il conto a cui tutti i bancomat sono associati
	 * @return il conto assiciato ai bancomat
	 */
	public IContoCorrenteBancario getConto() {
		return conto;
	}
	/**
	 * Imposta un nuovo conto per l'aggiornamento della lista bancomat
	 * @param conto il nuovo conto
	 */
	public void setConto(IContoCorrenteBancario conto) {
		this.conto = conto;
	}
}
