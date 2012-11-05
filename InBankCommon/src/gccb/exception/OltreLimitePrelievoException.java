package gccb.exception;

/**
 * Lanciata se si tenta di prelevare un importo
 * che eccede il valore del massimo prelevabile
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class OltreLimitePrelievoException extends GccbException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4733497945128213609L;

	/**
	 * Inizializza l'oggetto
	 */
	public OltreLimitePrelievoException() {}

	/**
	 * Inizializza l'oggetto specificando un messaggio di errore
	 * @param arg0 il messaggio
	 */
	public OltreLimitePrelievoException(String arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando una causa
	 * @param arg0 la causa
	 */
	public OltreLimitePrelievoException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando un messaggio di errore e la causa
	 * @param arg0 il messaggio
	 * @param arg1 la causa
	 */
	public OltreLimitePrelievoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
