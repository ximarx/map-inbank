package gccb.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Visualizza i messaggi nello stdout e stderr 
 * @author Francesco Capozzo
 *
 */
public class ConsoleLogger implements ILogger {

	public static final int SHOW_NOTHING = 1;
	public static final int SHOW_ERROR = 1;
	public static final int SHOW_WARNING = 2;
	public static final int SHOW_INFO = 3;
	
	private int logLevel = SHOW_INFO;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	
	public ConsoleLogger() {}
	
	public ConsoleLogger(int logLevel) {
		setLogLevel(logLevel);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.log.ILogger#error(java.lang.String)
	 */
	@Override
	public void error(String msg) {
		if ( logLevel >= SHOW_ERROR )
			System.out.println("[ERROR] " + getNow() + " " +msg);
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.log.ILogger#info(java.lang.String)
	 */
	@Override
	public void info(String msg) {
		if ( logLevel >= SHOW_INFO )
			System.out.println("[INFO] " + getNow() + " " +msg);
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.log.ILogger#warning(java.lang.String)
	 */
	@Override
	public void warning(String msg) {
		if ( logLevel >= SHOW_WARNING )
			System.out.println("[WARNING] " + getNow() + " " +msg);
	}
	
	public void setLogLevel(int level) {
		logLevel = level;
	}
	
	/**
	 * Una stringa rappresentante il momento corrente
	 * @return il formato della stringa e' di tipo "YYYY/MM/DD HH:MM:SS.MM"
	 */
	private String getNow() {
		return formatter.format(new Date());
	}
}
