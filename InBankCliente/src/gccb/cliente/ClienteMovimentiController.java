package gccb.cliente;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import gccb.cliente.gui.ClienteMovimentiView;
import gccb.cliente.model.ClienteMovimentiModel;
import gccb.exception.handler.ExceptionHandlerManager;

/**
 * Controller del pannello lista movimenti
 * @author Francesco Capozzo
 *
 */
public class ClienteMovimentiController extends YController {

	private ClienteMovimentiView view = new ClienteMovimentiView();
	private ClienteMovimentiModel model = new ClienteMovimentiModel();
	
	/**
	 * Inizializza il controller
	 */
	public ClienteMovimentiController() {
		setUpMVC(model, view);
		register(InBankCliente.DATA_CHANGED);
		register(InBankCliente.CLIENT_CONNECTED);
	}

	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankCliente.CLIENT_CONNECTED) || 
				event.getName().equals(InBankCliente.DATA_CHANGED)) {
			try {
				model.fetchMovimenti();
			} catch (Exception e) {
				ExceptionHandlerManager.process(e);
			}
		}
	}
	
}
