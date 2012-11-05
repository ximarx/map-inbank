package gccb.exception;

/**
 * Classe base per tutte le eccezioni
 * dei package gccb.*
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class GccbException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -959186985991915824L;

	public GccbException() {
	}

	public GccbException(String arg0) {
		super(arg0);
	}

	public GccbException(Throwable arg0) {
		super(arg0);
	}

	public GccbException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
