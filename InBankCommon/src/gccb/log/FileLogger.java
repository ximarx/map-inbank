package gccb.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Inserisce i messaggi all'interno di un file di log
 * specificato nel costruttore
 * 
 * @author Francesco Capozzo
 *
 */
public class FileLogger implements ILogger {

	private FileWriter infoFile = null;
	private FileWriter warnFile = null;
	private FileWriter errorFile = null;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	
	/**
	 * Costruttore di default. Inizializza il logger
	 * senza specificare alcun file dove inserire 
	 * i messaggi, quindi tutti i messaggi verranno ignorati
	 */
	public FileLogger() {}
	
	/**
	 * Inizializza il logger impostando il file
	 * in cui inserire i messaggi.
	 * @param file il file in cui i messaggi di tutti i tipi
	 * verranno inseriti
	 */
	public FileLogger(File file) {
		setFile(file);
	}
	
	/**
	 * Inizializza il logger impostando i file
	 * @param iFile il file dove inserire le INFO. (se null i messaggi verranno tralasciati)
	 * @param wFile il file dove inserire le WARNING. (se null i messaggi verranno tralasciati)
	 * @param eFile il file dove inserire le ERROR. (se null i messaggi verranno tralasciati)
	 */
	public FileLogger(File iFile, File wFile, File eFile) {
		setFile(iFile, wFile, eFile);
	}

	/**
	 * Imposta i file in cui inserire i messaggi.
	 * @param file il file in cui i messaggi di tutti i tipi
	 * verranno inseriti
	 */
	public void setFile(File file) {
		try {
			if ( file.exists() || file.createNewFile() ) {
				if ( file.canWrite() ) {
					infoFile = warnFile = errorFile = new FileWriter(file);
				} else {
					infoFile = warnFile = errorFile = null;
				}
			}
		} catch (IOException e) {}
	}
	
	/**
	 * Imposta i file in cui inserire i messaggi
	 * @param iFile il file dove inserire le INFO. (se null i messaggi verranno tralasciati)
	 * @param wFile il file dove inserire le WARNING. (se null i messaggi verranno tralasciati)
	 * @param eFile il file dove inserire le ERROR. (se null i messaggi verranno tralasciati)
	 */
	public void setFile(File iFile, File wFile, File eFile) {
		try {
			if ( iFile != null ) {
				if ( iFile.exists() || iFile.createNewFile() ) {
					if ( iFile.canWrite() ) {
						infoFile = new FileWriter(iFile);
					}
				}
			}
		} catch (IOException e) {}
		
		try {
			if ( wFile != null ) {
				if ( wFile.exists() || wFile.createNewFile() ) {
					if ( wFile.canWrite() ) {
						warnFile = new FileWriter(wFile);
					}
				}
			}
		} catch (IOException e) {}

		try {
			if ( eFile != null ) {
				if ( eFile.exists() || eFile.createNewFile() ) {
					if ( eFile.canWrite() ) {
						errorFile = new FileWriter(wFile);
					}
				}
			}
		} catch (IOException e) {}
	}
	
	@Override
	public void error(String msg) {
		if ( errorFile != null ) {
			try {
				String newMsg = "[ERROR] " + getNow() + " " + msg + "\n"; 
				errorFile.append(newMsg.subSequence(0, newMsg.length()));
				infoFile.flush();
			} catch (IOException e) {}
		}
	}

	@Override
	public void info(String msg) {
		if ( infoFile != null ) {
			try {
				String newMsg = "[INFO] " + getNow() + " " + msg + "\n"; 
				infoFile.append(newMsg.subSequence(0, newMsg.length()));
				infoFile.flush();
			} catch (IOException e) {}
		}
	}

	@Override
	public void warning(String msg) {
		if ( warnFile != null ) {
			try {
				String newMsg = "[WARNING] " + getNow() + " " + msg + "\n"; 
				warnFile.append(newMsg.subSequence(0, newMsg.length()));
				infoFile.flush();
			} catch (IOException e) {}
		}
	}

	/**
	 * Una stringa rappresentante il momento corrente
	 * @return il formato della stringa e' di tipo "YYYY/MM/DD HH:MM:SS.MM"
	 */
	private String getNow() {
		return formatter.format(new Date());
		/*
		GregorianCalendar cal = new GregorianCalendar();		
		return cal.get(GregorianCalendar.YEAR) + "/" + (cal.get(GregorianCalendar.MONTH) + 1) +
			"/" + cal.get(GregorianCalendar.DAY_OF_MONTH) + " " + cal.get(GregorianCalendar.HOUR_OF_DAY) + 
			":" + cal.get(GregorianCalendar.MINUTE) + ":" + cal.get(GregorianCalendar.SECOND) + 
			"." + cal.get(GregorianCalendar.MILLISECOND);
			*/
	}
}
