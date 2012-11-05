package gccb.exception;

/**
 * Lanciata se preleva se il saldo successivo ad un prelievo
 * risulta inferiore al valore del fido
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class OltreLimiteFidoException extends OltreLimitePrelievoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4693795148961469591L;

	public OltreLimiteFidoException() {
	}

	public OltreLimiteFidoException(String arg0) {
		super(arg0);
	}

	public OltreLimiteFidoException(Throwable arg0) {
		super(arg0);
	}

	public OltreLimiteFidoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
