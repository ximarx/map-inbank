package gccb.exception.handler;

/**
 * Interfaccia da implementare per poter
 * realizzare un nuovo ExceptionHandler registrabile
 * in {@link ExceptionHandlerManager}
 * 
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public interface IExceptionHandler {

	/**
	 * Gestisce l'eccezione. La funzione viene invocata
	 * da {@link ExceptionHandlerManager#process(Exception)}
	 * @param e l'eccezione da visualizzare
	 */
	public void processError(Exception e);
	
}
