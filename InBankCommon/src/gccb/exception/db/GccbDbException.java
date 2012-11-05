package gccb.exception.db;

import gccb.exception.GccbException;

/**
 * Eccezione causata da un errore generico durante l'accesso al database
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class GccbDbException extends GccbException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 914639640298558127L;

	/**
	 * Inizializza l'oggetto
	 */
	public GccbDbException() {}

	/**
	 * Inizializza l'oggetto specificando un messaggio di errore
	 * @param arg0 il messaggio
	 */
	public GccbDbException(String arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando una causa
	 * @param arg0 la causa
	 */
	public GccbDbException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando un messaggio e una causa
	 * @param arg0 il messaggio
	 * @param arg1 la causa
	 */
	public GccbDbException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
