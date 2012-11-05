package gccb.exception.db;

/**
 * L'eccezione viene lanciata se una ricerca di un dati nel database
 * non restituisce alcun risultato anche se dovrebbe
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class DatiNonTrovatiException extends GccbDbException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7956291866488489497L;

	/**
	 * Inizializza l'oggetto
	 */
	public DatiNonTrovatiException() {}

	/**
	 * Inizializza l'oggetto specificando un messaggio di errore
	 * @param arg0 il messaggio
	 */
	public DatiNonTrovatiException(String arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando una causa
	 * @param arg0
	 */
	public DatiNonTrovatiException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando sia un messaggio che una causa
	 * @param arg0 il messaggio
	 * @param arg1 la causa
	 */
	public DatiNonTrovatiException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
