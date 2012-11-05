package gccb.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe Helper per la gestione e l'accesso alle configurazioni
 * @author Francesco Capozzo
 *
 */
public class SettingsManager {
	
	private static SettingsManager defaultInstance;
	
	/**
	 * Ritorna l'istanza di default del {@link SettingsManager}
	 * @return il setting manager di default
	 */
	public static SettingsManager getDefaultInstance() {
		if ( defaultInstance == null ) {
			defaultInstance = new SettingsManager();
		}
		return defaultInstance;
	}
	
	private Properties configs = new Properties();
	
	/**
	 * Carica il file di configurazione indicato dalla cartella configs/
	 * @param configFile il nome del file
	 * @throws IOException in caso di errore durante la lettura del file
	 */
	public void loadConfigs(String configFile) throws IOException {
		File f = new File("configs/"+configFile);
		configs.load(new FileInputStream(f));
	}
	
	/**
	 * Carica le proprietà da due array, uno di chiavi e uno di valori
	 * @param keys l'array di chiavi
	 * @param values l'array di valori
	 */
	public void loadAppletPreferences( String[] keys, String[] values ) {
		for ( int i = 0; i < keys.length; i++ ) {
			try {
				configs.put(keys[i], values[i]);
			} catch (ArrayIndexOutOfBoundsException e) {
				configs.put(keys[i], "");
			} catch ( NullPointerException npe ) {
				configs.put(keys[i], "");
			}
		}
	}
	
	/**
	 * Accessor per la funzione {@link Properties#getProperty(String)} per il ritrovamento della configurazione 
	 * @param propName la proprietà cercata
	 * @return il valore associato alla proprietà
	 */
	public String get(String propName) {
		return configs.getProperty(propName);
	}
	
	/**
	 * Accessor per la funzione {@link Properties#getProperty(String, String)} per il ritrovamento della configurazione 
	 * @param propName la proprietà cercata
	 * @param defaultValue il valore di default da ritornare se la proprietà non è impostata
	 * @return il valore associato alla proprietà
	 */
	public String get(String propName, String defaultValue) {
		return configs.getProperty(propName, defaultValue);
	}
}
