package gccb.server;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import gccb.server.gui.ServerConnectionWidgetView;
import gccb.server.model.ServerConnectionWidgetModel;

/**
 * Controller del widget della barra di stato
 * Visualizza lo stato delle connessioni a database ed rmi
 * @author Francesco Capozzo
 *
 */
public class ServerConnectionWidgetController extends YController {

	private ServerConnectionWidgetView view = new ServerConnectionWidgetView();
	private ServerConnectionWidgetModel model = new ServerConnectionWidgetModel();
	
	/**
	 * Inizializza il widget della barra di stato
	 */
	public ServerConnectionWidgetController() {
		setUpMVC(model, view);
		register(InBankServer.DB_CONNECTED);
		register(InBankServer.DB_DISCONNECTED);
		register(InBankServer.RMI_CONNECTED);
		model.dbDisconnected();
		model.rmiDisconnected();
	}

	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	@Override
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankServer.DB_CONNECTED) ) {
			model.dbConnected();
		} else if ( event.getName().equals(InBankServer.DB_DISCONNECTED) ) {
			model.dbDisconnected();
		} else if ( event.getName().equals(InBankServer.RMI_CONNECTED) ) {
			model.rmiConnected();
		}
	}
}
