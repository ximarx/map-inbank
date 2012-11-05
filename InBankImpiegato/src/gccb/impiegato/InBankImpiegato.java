package gccb.impiegato;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.handler.GuiHandler;
import gccb.settings.SettingsManager;

/**
 * Classe principale per il client Impiegato
 * @author Francesco Capozzo
 *
 */
public class InBankImpiegato {

	/**
	 * Nome dell'evento eseguito quando il client si connette
	 */
	public static final String CLIENT_CONNECTED = "CLIENT_CONNECTED";
	/**
	 * Nome dell'evento eseguito quando il client si disconnette
	 */
	public static final String CLIENT_DISCONNECTED = "CLIENT_DISCONNECTED";
	/**
	 * Nome dell'evento eseguito quando un conto viene modificato
	 */
	public static final String CONTO_CHANGED = "CONTO_CHANGED";
	/**
	 * Nome dell'evento eseguito quando un conto viene chiuso
	 */
	public static final String CONTO_CLOSED = "CONTO_CLOSED";

	/**
	 * Funzione main dell'applicazione. Imposta il defaultHandler per le eccezioni,
	 * legge le configurazioni e passa il controllo {@link ImpiegatoFrameController}
	 * per la visualizzazione dell'interfaccia
	 * 
	 * Utilizzando il parametro "themes" dalla linea di comando è possibile visualizzare
	 * la lista dei temi utilizzabili
	 * @param args lista dei parametri opzionali. Viene accettato il parametro themes
	 * per la visualizzazione dei temi
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
					UIManager.setLookAndFeel(SettingsManager.getDefaultInstance().get("ui.theme"));
				} catch (Exception e) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch ( Exception innere) {
						innere.printStackTrace();
					}
				}
				ImpiegatoFrameController fc = new ImpiegatoFrameController();
				ExceptionHandlerManager.registerNewDefaultEH(new GuiHandler(fc));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Errore durante la lettura del file di configurazione", "Errore lettura configurazioni", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
