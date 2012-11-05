package gccb.impiegato.model;

import java.util.Collections;
import java.util.List;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.IContoCorrenteBancario;
import gccb.ITransazione;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.impiegato.net.ImpiegatoServiceDelegate;

/**
 * Modello per le operazioni sui movimenti registrati
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoMovimentiModel extends YModel {

	//=== Pattern singleton
	private static ImpiegatoMovimentiModel singleton = null;
	/**
	 * Ritorna l'istanza di default del modello
	 * @return l'istanza condivisa
	 */
	public static ImpiegatoMovimentiModel getInstace() {
		if ( singleton == null )
			singleton = new ImpiegatoMovimentiModel();
		return singleton;
	}
	/**
	 * Ritorna una nuova istanza indipendente del modello
	 * @return una nuova istanza
	 */
	public static ImpiegatoMovimentiModel getInstanceNew() {
		return new ImpiegatoMovimentiModel();
	}
	private ImpiegatoMovimentiModel() {}
	//===	

	private List<ITransazione> transazioni;
	private IContoCorrenteBancario conto;
	
	/**
	 * Resetta tutti i campi del modello
	 */
	public void resetAll() {
		setTransazioni(Collections.EMPTY_LIST);
		setConto(null);
	}
	
	/**
	 * Aggiorna la lista di transazioni associate al conto selezionato
	 * @throws GccbNetException in caso di errore del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void aggiornaListaTransazioni() throws ServerNonDisponibileException, GccbNetException {
		try {
			setTransazioni(ImpiegatoServiceDelegate.getTransazioni(conto.getIdConto()));
		} catch (NullPointerException npe) {
			throw new IllegalArgumentException("Nessun conto selezionato");
		}
	}
	
	/**
	 * Ritorna la lista di transazioni registrate
	 * @return la lista di transazioni
	 */
	public List<ITransazione> getTransazioni() {
		return transazioni;
	}
	/**
	 * Imposta una nuova lista di transazioni e
	 * notifica della modifica
	 * @param transazioni la nuova lista
	 */
	public void setTransazioni(List<ITransazione> transazioni) {
		this.transazioni = transazioni;
		notifyObservers("transazioni");
	}
	/**
	 * Ritorna il conto selezionato
	 * @return il conto selezionato
	 */
	public IContoCorrenteBancario getConto() {
		return conto;
	}
	/**
	 * Imposta il nuovo conto selezionato
	 * @param conto il nuovo conto
	 */
	public void setConto(IContoCorrenteBancario conto) {
		this.conto = conto;
		notifyObservers("conto");
	}
}
