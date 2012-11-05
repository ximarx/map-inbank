package gccb.exception.net;

/**
 * Lanciata nel caso di un errore durante l'autenticazione 
 * dovuto all'utilizzo di credenziali non valide
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class CredenzialiNonValideException extends GccbNetException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917004426941395329L;

	/**
	 * Costruttore di default
	 */
	public CredenzialiNonValideException() {
	}

	/**
	 * Inizializza l'eccezione specificando un messaggio di errore
	 * @param arg0 il messaggio da visualizzare
	 */
	public CredenzialiNonValideException(String arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'eccezione specificando una eccezione causa di questa
	 * @param arg0 la causa
	 */
	public CredenzialiNonValideException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando sia il messaggio che una causa
	 * @param arg0 il messaggio
	 * @param arg1 la causa
	 */
	public CredenzialiNonValideException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
