package gccb.exception.handler;

/**
 * Handler di default per le eccezioni.
 * Invia il messaggio di errore nella stderr
 * 
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class ConsoleHandler implements IExceptionHandler {

	/*
	 * (non-Javadoc)
	 * @see gccb.exceptionHandler.IExceptionHandler#processError(java.lang.Exception)
	 */
	public void processError(Exception e) {
		System.err.println(e.getClass().getSimpleName() + " - " + e.getMessage());
		System.err.println("------------------------");
		e.printStackTrace();
		System.err.println("------------------------");
	}
}
