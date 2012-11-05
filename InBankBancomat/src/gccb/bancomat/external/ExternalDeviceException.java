package gccb.bancomat.external;

/**
 * Eccezione propagata dal terminale esterno per
 * indicare un malfunzionamento o l'impossibilità
 * di soddisfare una richiesta * 
 * @author Francesco Capozzo
 *
 */
public class ExternalDeviceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6286788392243639806L;

	public ExternalDeviceException() {}

	public ExternalDeviceException(String message) {
		super(message);
	}

	public ExternalDeviceException(Throwable cause) {
		super(cause);
	}

	public ExternalDeviceException(String message, Throwable cause) {
		super(message, cause);
	}

}
