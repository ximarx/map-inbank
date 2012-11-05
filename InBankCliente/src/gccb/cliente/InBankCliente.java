package gccb.cliente;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.handler.GuiHandler;
import gccb.settings.SettingsManager;

/**
 * Classe principale del client per eBanking
 * @author Francesco Capozzo
 *
 */
public class InBankCliente {

	public static final String CLIENT_CONNECTED = "CLIENT_CONNECTED";
	public static final String CLIENT_DISCONNECTED = "CLIENT_DISCONNECTED";
	public static final String DATA_CHANGED = "DATA_CHANGED";
	
	/**
	 * Funzione main dell'applicazione.
	 * Inizializza il programma dopo aver letto le configurazioni
	 * e settato il look&feel
	 * @param args lista di parametri. Accetta themes come parametro per la visualizzazione 
	 * della lista di temi disponibili per l'applicazione
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
				ClienteFrameController fc =  new ClienteFrameController();
				ExceptionHandlerManager.registerNewDefaultEH(new GuiHandler(fc));
				fc.showMainFrame(true);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Errore durante la lettura del file di configurazione", "Errore lettura configurazioni", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
