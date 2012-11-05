package gccb.exception.net;

import gccb.exception.GccbException;

/**
 * Eccezione utilizzata in caso di errori durante
 * le richieste al server
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class GccbNetException extends GccbException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1802686746305482145L;

	/**
	 * Inizializza l'oggetto
	 */
	public GccbNetException() {}

	/**
	 * Inizializza l'oggetto specificando un messaggio di errore
	 * @param arg0 il messaggio
	 */
	public GccbNetException(String arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando una causa
	 * @param arg0 la causa
	 */
	public GccbNetException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando una causa e un messaggio di errore
	 * @param arg0 il messaggio
	 * @param arg1 la causa
	 */
	public GccbNetException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
