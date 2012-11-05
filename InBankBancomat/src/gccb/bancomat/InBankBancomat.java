package gccb.bancomat;

import java.io.IOException;

import gccb.bancomat.exception.handler.BancomatHandler;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.settings.SettingsManager;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Classe di avvio per il client Bancomat
 * @author Francesco Capozzo
 *
 */
public class InBankBancomat {

	/**
	 * Evento connessione del client al server
	 */
	public static final String CLIENT_CONNECTED = "CLIENT_CONNECTED";
	/**
	 * Evento disconnessione del client
	 */
	public static final String CLIENT_DISCONNECTED = "CLIENT_DISCONNECTED";
	/**
	 * Evento inserimento carta
	 */
	public static final String CARD_IN = "CARD_IN";
	/**
	 * Evento emissione carta
	 */
	public static final String CARD_OUT = "CARD_OUT";
	/**
	 * Evento sistema pronto
	 */
	public static final String READY = "READY";
	
	/**
	 * Main dell'applicazione. Accetta come parametro "themes"
	 * per la visualizzazione dei temi installati in stdout
	 * @param args la lista dei parametri. Ad ora accettato solo
	 * themes
	 */
	public static void main(String[] args) {
		if ( args.length == 1 && args[0].equals("themes")) {
			System.out.println("--- Lista temi selezionabili ---");
			for ( LookAndFeelInfo lef : UIManager.getInstalledLookAndFeels() ) {
				System.out.println(lef.getClassName());
			}
		} else {
			try {
				SettingsManager.getDefaultInstance().loadConfigs("configs.properties");
				try {
					UIManager.setLookAndFeel(SettingsManager.getDefaultInstance().get("ui.theme").trim());
				} catch (Exception e) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch ( Exception innere) {
						innere.printStackTrace();
					}
				} 
				BancomatFrameController c = new BancomatFrameController();
				ExceptionHandlerManager.registerNewDefaultEH(new BancomatHandler(c.getErrorController()));
			} catch ( IOException e) {
				JOptionPane.showMessageDialog(null, "Errore durante la lettura del file di configurazione", "Errore lettura configurazioni", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
