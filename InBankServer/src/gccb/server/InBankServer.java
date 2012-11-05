package gccb.server;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.handler.GuiHandler;
import gccb.log.FileLogger;
import gccb.log.LogManager;
import gccb.settings.SettingsManager;

/**
 * Classe di avvio per l'applicazione InBankServer
 * @author Francesco Capozzo
 *
 */
public class InBankServer {

	/**
	 * Evento lanciato quando la connessione al database viene creata
	 */
	public static final String DB_CONNECTED = "DB_CONNECTED";
	/**
	 * Evento lanciato quando la connessione al database viene interrotta
	 */
	public static final String DB_DISCONNECTED = "DB_DISCONNECTED";
	/**
	 * Evento lanciato quando la connessione al Naming Server RMI viene stabilita
	 */
	public static final String RMI_CONNECTED = "RMI_CONNECTED";
	/**
	 * Evento lanciato quando lo stato di un servizio viene alterato
	 */
	public static final String SERVICES_CHANGED = "SERVICES_CHANGED";

	/**
	 * Avvia InBankServer
	 * @param args la lista di parametri
	 * Il programma accetta il parametro "themes" per visualizzare la lista di temi installati disponibili
	 * per l'applicazione.
	 */
	public static void main(String[] args) {
		
		if ( args.length == 1 && args[0].equals("themes")) {
			System.out.println("--- Lista temi selezionabili ---");
			for ( LookAndFeelInfo lef : UIManager.getInstalledLookAndFeels() ) {
				System.out.println(lef.getClassName());
			}
		} else {
			try {
				// Inizializzazione del logger
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String logName = "inbank-server_"+sdf.format(new Date())+"_";
					File logFile = new File("logs/"+logName+"0.log");
					int i = 1; 
					while ( logFile.exists() ) {
						logFile = new File("logs/" + logName + i++ + ".log");
					}
					LogManager.setLogger(new FileLogger(logFile));
				} catch ( Exception e ) {
					LogManager.resetDefaultLogger();
					LogManager.warning("Errore durante l'inizializzazione del FileLogger. Verra' utilizzato il logger di default");
				}
				// Inizializzazione configurazioni
				SettingsManager.getDefaultInstance().loadConfigs("configs.properties");
				// Inizializzazione Look&Feel
				try {
					UIManager.setLookAndFeel(SettingsManager.getDefaultInstance().get("ui.theme").trim());
				} catch (Exception e) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch ( Exception innere) {
						innere.printStackTrace();
					}
				}
				// Inizializzazione FrameController
				ServerFrameController fc = new ServerFrameController();
				ExceptionHandlerManager.registerNewDefaultEH(new GuiHandler(fc));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Errore durante la lettura del file di configurazione", "Errore lettura configurazioni", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
