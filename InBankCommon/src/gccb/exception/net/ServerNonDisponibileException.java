package gccb.exception.net;

/**
 * Lanciata se il server non risulta disponibile a soddisfare una
 * richiesta o se la connessione al server risulta interrotta
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class ServerNonDisponibileException extends GccbNetException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2800367074080748457L;

	/**
	 * Inizializza l'oggetto
	 */
	public ServerNonDisponibileException() {}

	/**
	 * Inizializza l'oggetto specificando un messaggio di errore
	 * @param arg0
	 */
	public ServerNonDisponibileException(String arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando una causa
	 * @param arg0
	 */
	public ServerNonDisponibileException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Inizializza l'oggetto specificando una causa e il messaggio di errore
	 * @param arg0 il messaggio
	 * @param arg1 la causa
	 */
	public ServerNonDisponibileException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
