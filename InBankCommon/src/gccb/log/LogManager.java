package gccb.log;

/**
 * Gestore dei logger
 * @author Francesco Capozzo
 *
 */
public class LogManager {

	private static ILogger defaultLogger = new ConsoleLogger(); 
	
	private LogManager() {}
	
	/**
	 * Inoltra una richiesta di tipo ERROR al logger di default
	 * @param msg il messaggio da inserire
	 */
	static public void error(String msg) {
		defaultLogger.error(msg);
	}
	/**
	 * Inoltra una richiesta di tipo WARNING al logger di default
	 * @param msg il messaggio da inserire
	 */
	static public void warning(String msg) {
		defaultLogger.warning(msg);
	}
	/**
	 * Inoltra una richiesta di tipo INFO al logger di default
	 * @param msg il messaggio da inserire
	 */
	static public void info(String msg) {
		defaultLogger.info(msg);
	}
	/**
	 * Ripristina il logger di default a quello standard
	 */
	static public void resetDefaultLogger() {
		setLogger(new ConsoleLogger());
	}
	/**
	 * Imposta un nuovo logger come quello di default
	 * @param logger il nuovo logger
	 */
	static public void setLogger(ILogger logger) {
		defaultLogger = logger;
	}
}
