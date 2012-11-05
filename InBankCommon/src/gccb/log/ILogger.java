package gccb.log;

/**
 * Interfaccia implementata dai logger utilizzabili tramite LogManager
 * @author Francesco Capozzo
 *
 */
public interface ILogger {

	/**
	 * Gestisce il log di un messaggio di tipo informativo
	 * @param msg il messaggio
	 */
	void info(String msg);
	
	/**
	 * Gestisce il log di un messaggio di tipo avviso
	 * @param msg il messaggio
	 */
	void warning(String msg);
	
	/**
	 * Gestisce il log di un messaggio di tipo errore
	 * @param msg il messaggio
	 */
	void error(String msg);
	
}
