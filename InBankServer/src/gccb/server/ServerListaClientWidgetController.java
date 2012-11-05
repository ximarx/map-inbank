package gccb.server;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import gccb.server.gui.ServerListaClientWidgetView;
import gccb.server.model.ServerClient;
import gccb.server.model.ServerListaClientWidgetModel;

/**
 * Widget della gestione della lista client connessi. Permette la disconnessione
 * di un client connesso tramite l'api RMI
 * @author Francesco Capozzo
 *
 */
public class ServerListaClientWidgetController extends YController {

	private ServerListaClientWidgetView view = new ServerListaClientWidgetView();
	private ServerListaClientWidgetModel model = new ServerListaClientWidgetModel();
	
	/**
	 * Inizializza la widget e registra gli applicationEvent
	 */
	public ServerListaClientWidgetController() {
		setUpMVC(model, view);
		register(InBankServer.RMI_CONNECTED);
		view.setStatoPulsanti(false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	@Override
	public void receivedApplicationEvent(YApplicationEvent event) {
		model.aggiornaListaClient();
	}
	
	/**
	 * Funzione invocata alla pressione del pulsante per l'aggiornamento della lista
	 */
	public void buttonAggiornaPressed() {
		model.aggiornaListaClient();
	}
	
	/**
	 * Gestisce il cambio di selezione della tabella dei client connessi
	 * @param newClient il nuovo client selezionato
	 */
	public void listaClientSelectionChanged(Object newClient) {
		model.setClient((ServerClient)newClient);
		view.setStatoPulsanti(newClient != null);
	}
	
	/**
	 * Gestisce la pressione del pulsante di disconnessione del client selezionato
	 */
	public void buttonDisconnettiPressed() {
		model.disconnettiClient();
	}
	
}
