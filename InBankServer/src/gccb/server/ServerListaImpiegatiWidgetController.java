package gccb.server;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import gccb.IImpiegato;
import gccb.server.gui.ServerListaImpiegatiWidgetView;
import gccb.server.model.ServerListaImpiegatiWidgetModel;

/**
 * Widget per la gestione della lista di impiegati. Permette l'inserimento di un nuovo impiegato,
 * la modifica o la rimozione di uno esistente
 * @author Francesco Capozzo
 *
 */
public class ServerListaImpiegatiWidgetController extends YController {

	private ServerListaImpiegatiWidgetView view = new ServerListaImpiegatiWidgetView();
	private ServerListaImpiegatiWidgetModel model = new ServerListaImpiegatiWidgetModel();
	
	/**
	 * Inizializza lo widget ed imposta lo stato iniziale della view 
	 */
	public ServerListaImpiegatiWidgetController() {
		setUpMVC(model, view);
		register(InBankServer.DB_CONNECTED);
		view.setStatoPulsanti(false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	@Override
	public void receivedApplicationEvent(YApplicationEvent event) {
		model.aggiornaListaImpiegati();
	}
	
	/**
	 * Gestisce l'evento di pressione del pulsante per l'inserimento di un nuovo impiegato
	 */
	public void buttonAggiungiPressed() {
		model.resetNew();
		view.setStatoPannelloAggiungi(true);
	}
	
	/**
	 * Esegue il cambio di password per l'elemento selezionato
	 */
	public void buttonCambiaPressed() {
		JPasswordField pwd = new JPasswordField(10);
	    int action = JOptionPane.showConfirmDialog(view, new Object[]{"Nuova password:", pwd}, "Enter Password",JOptionPane.OK_CANCEL_OPTION);
	    String newPassword = String.valueOf(pwd.getPassword());
		if ( action == JOptionPane.OK_OPTION &&  newPassword != null && !newPassword.equals("")) {
			model.modificaPassword(newPassword);
		}
	}
	
	/**
	 * Rimuove l'impiegato selezionato
	 */
	public void buttonRimuoviPressed() {
		model.rimuoviImpiegato();
	}
	
	/**
	 * Funzione invocata alla pressione del tasto di conferma dell'aggiunta di un nuovo impiegato
	 */
	public void buttonConfermaAggiuntaPressed() {
		copyToModel("newUsername");
		copyToModel("newPassword");
		copyToModel("newVisualizzato");
		model.aggiungiImpiegato();
		view.setStatoPannelloAggiungi(false);
	}
	
	/**
	 * Annulla l'inserimento del nuovo impiegato
	 */
	public void buttonAnnullaAggiuntaPressed() {
		view.setStatoPannelloAggiungi(false);
	}
	
	/**
	 * Gestisce il cambio di selezione nella tabella degli impiegati selezionati
	 * @param impiegato il nuovo impiegato selezionato
	 */
	public void impiegatiSelectionChanged(Object impiegato) {
		model.setImpiegato((IImpiegato) impiegato);
		view.setStatoPulsanti(impiegato != null);
	}
	
}
