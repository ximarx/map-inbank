package gccb.cliente;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import gccb.cliente.gui.ClienteFrame;
import gccb.cliente.model.ClienteFrameModel;
import gccb.cliente.net.ClienteServiceDelegate;
import gccb.net.rmi.ILogoutAction;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.handler.GuiHandler;

/**
 * Controller principale dell'applicazione.
 * Gestisce la finestra principale e i controller figli
 * @author Francesco Capozzo
 *
 */
public class ClienteFrameController extends YController {

	private ClienteFrame clienteFrame = new ClienteFrame();
	private ClienteLoginController loginC = new ClienteLoginController();
	private ClienteDettagliController dettagliC = new ClienteDettagliController();
	private ClienteMovimentiController movimentiC = new ClienteMovimentiController();
	private ClienteBonificoController bonificoC = new ClienteBonificoController();
	
	private ClienteFrameModel model = new ClienteFrameModel();
	
	private boolean exitOnClose = false;
	
	/**
	 * Inizializza il controller
	 */
	public ClienteFrameController() {
		super();
		this.setUpMVC(model, clienteFrame);
		this.register(InBankCliente.CLIENT_CONNECTED);
		this.register(InBankCliente.CLIENT_DISCONNECTED);
		this.register(InBankCliente.DATA_CHANGED);
		addChildren();
		clienteFrame.setStatoPulsanti(false);
		clienteFrame.setLocationRelativeTo(null);
		clienteFrame.setContent((Component) loginC.getView());
		clienteFrame.setTitle("InBank Client eBanking");
		
	}
	
	/**
	 * Visualizza la finestra principale indicando
	 * l'operazione da compiere in caso di chiusura
	 * dell'applicazione.
	 * Usare true se il controller viene usato in una applicazione
	 * standalone, false se l'applicazione viene usata all'interno di una
	 * applet. Altrimenti la chiusura dell'applicazione può comportare anche 
	 * la chiusura del browser.
	 * @param exitOnClose true per uscire dal programma in caso di chiusura,
	 * false per usare {@link JFrame#dispose()}.
	 */
	public void showMainFrame( boolean exitOnClose ) {
		if ( exitOnClose ) {
			clienteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			clienteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		clienteFrame.setVisible(true);
		this.exitOnClose = exitOnClose;
	}
	
	private void addChildren() {
		this.addChild(loginC);
		dettagliC.lazyInit(model);
		this.addChild(dettagliC);
		this.addChild(movimentiC);
		this.addChild(bonificoC);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	@Override
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankCliente.CLIENT_CONNECTED) ) {
			ClienteServiceDelegate.setLogoutAction(new ILogoutAction() {
				@Override
				public void execute() {
					JOptionPane.showMessageDialog(clienteFrame, "Il server ha richiesto la disconnessione del Client", "Disconnessione", JOptionPane.WARNING_MESSAGE);
					if ( exitOnClose ) {
						System.exit(-1);
					} else {
						clienteFrame.dispose();
					}
				}
			});
			clienteFrame.setStatoPulsanti(true);
			clienteFrame.setContent((Component) dettagliC.getView());
			try {
				model.updateConto();
			} catch (Exception e) {
				ExceptionHandlerManager.process(e);
			}
		} else if ( event.getName().equals(InBankCliente.DATA_CHANGED) ) {
			buttonDettagliPressed();
		} else if ( event.getName().equals(InBankCliente.CLIENT_DISCONNECTED) ) {
			clienteFrame.setStatoPulsanti(false);
			if ( exitOnClose ) {
				System.exit(-1);
			} else {
				clienteFrame.dispose();
			}
		}
	}
	
	/**
	 * Azione eseguita in caso di chiusura della finestra principale
	 * Esegue la disconnessione del client e termina il programma
	 */
	public void clienteFrameClosing() {
		if ( ClienteServiceDelegate.isConnected() ) {
			ClienteServiceDelegate.disconnect();
		}
		if ( exitOnClose ) {
			System.exit(-1);
		} else {
			clienteFrame.dispose();
		}
	}

	/**
	 * Visualizza un errore occorso
	 * Viene invocato dal {@link GuiHandler} per gestire in maniera trasparente
	 * la visualizzazione degli errori tramite {@link ExceptionHandlerManager}
	 * @param e l'eccezione da visualizzare
	 */
	public void showError(Exception e) {
		//ErrorDialogView.showError(e);
		try {
			JOptionPane.showMessageDialog(clienteFrame, e.getCause().getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException npe) {
			JOptionPane.showMessageDialog(clienteFrame, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Imposta come pannello principale la lista dei movimenti del conto
	 */
	public void buttonMovimentiPressed() {
		clienteFrame.setContent((Component) movimentiC.getView());
	}
	
	/**
	 * Visualizza il pannello per l'esecuzione del bonifico
	 */
	public void buttonBonificoPressed() {
		clienteFrame.setContent((Component) bonificoC.getView());
	}
	
	/**
	 * Visualizza il pannello dettagli conto
	 */
	public void buttonDettagliPressed() {
		clienteFrame.setContent((Component) dettagliC.getView());
	}
	
	/**
	 * Esce dall'applicazione invocando {@link #clienteFrameClosing()}
	 */
	public void buttonLogoutPressed() {
		clienteFrameClosing();
	}
	
}
