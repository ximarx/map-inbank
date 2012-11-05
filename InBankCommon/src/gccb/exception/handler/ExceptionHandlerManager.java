package gccb.exception.handler;


/**
 * Gestore delle eccezioni. Utilizza come
 * handler di default {@link ConsoleHandler} per gestire
 * l'eccezione
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
abstract public class ExceptionHandlerManager {

	static private IExceptionHandler deh = new ConsoleHandler();
	
	/**
	 * Ritorni l'handler registrato come default
	 * @return L'handler di default
	 */
	static public IExceptionHandler getDefaultEH() {
		return deh;
	}
	
	/**
	 * Registra un nuovo handler sostituendolo a quello di default
	 * L'handler verrà utilizzato per gestire tutte le seguenti eccezioni
	 * @param eh il nuovo handler da registrare come default
	 */
	static public void registerNewDefaultEH(IExceptionHandler eh) {
		deh = eh;
	}
	
	/**
	 * Ripristina l'handler di default con quello standard.
	 * Verrà utilizzato {@link ConsoleHandler} come nuovo handler
	 * 
	 */
	static public void backToDefault() {
		deh = new ConsoleHandler();
	}
	
	/**
	 * Scorciatoia per la gestione dell'eccezione indicata.
	 * Questa funzione corrisponde all'uso congiunto di 
	 * {@link #getDefaultEH()} e {@link IExceptionHandler#processError(Exception)}
	 * @param e L'eccezione da gestire
	 */
	static public void process(Exception e) {
		deh.processError(e);
	}
}
