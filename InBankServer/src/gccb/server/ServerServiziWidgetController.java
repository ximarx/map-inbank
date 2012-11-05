package gccb.server;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.net.rmi.RMIClientType;
import gccb.server.gui.ServerServiziWidgetView;
import gccb.server.model.ServerService;
import gccb.server.model.ServerServiziWidgetModel;

/**
 * Widget per il controllo del servizi
 * @author Francesco Capozzo
 *
 */
public class ServerServiziWidgetController extends YController {

	private ServerServiziWidgetView view = new ServerServiziWidgetView();
	private ServerServiziWidgetModel model = new ServerServiziWidgetModel();
	
	/**
	 * Inizializza la widget identificando i servizi registrabili
	 */
	public ServerServiziWidgetController() {
		setUpMVC(model, view);
		register(InBankServer.SERVICES_CHANGED);
		model.inizializzaServizi();
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	@Override
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankServer.SERVICES_CHANGED)) {
			try {
				new YWorker().execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						model.aggiornaStatoServizi();
						return null;
					}
				}, "Attendere prego...");
			} catch (Exception e) {
				ExceptionHandlerManager.process(e);
			}
		}
	}
	
	/**
	 * Gestisce il cambio di selezione nella tabella dei servizi
	 * @param oServizio il nuovo servizio selezionato
	 */
	public void serviziSelectionChanged(Object oServizio) {
		ServerService servizio = (ServerService) oServizio;
		model.setServizio(servizio);
		if ( oServizio != null ) {
			view.setStatoPulsanti(servizio.getStato() == ServerService.Stati.NON_ATTIVO,
					servizio.getStato() == ServerService.Stati.ATTIVO);
		} else {
			view.setStatoPulsanti(false, false);
		}
	}
	
	/**
	 * Attiva il servizio selezionato in modalità bancomat
	 */
	public void buttonAttivaBancomatPressed() {
		try {
			new YWorker().execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.attivaServizio(RMIClientType.BANCOMAT);
					model.aggiornaStatoServizi();
					return null;
				}
			});
			sendApplicationEvent(new YApplicationEvent(InBankServer.RMI_CONNECTED));
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}
	
	/**
	 * Attiva il servizio selezionato in modalità cliente
	 */
	public void buttonAttivaClientePressed() {
		try {
			new YWorker().execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.attivaServizio(RMIClientType.UTENTE);
					model.aggiornaStatoServizi();
					return null;
				}
			});
			sendApplicationEvent(new YApplicationEvent(InBankServer.RMI_CONNECTED));
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}
	
	/**
	 * Attiva il servizio selezionato in modalità impiegato
	 */
	public void buttonAttivaImpiegatoPressed() {
		try {
			new YWorker().execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.attivaServizio(RMIClientType.IMPIEGATO);
					model.aggiornaStatoServizi();
					return null;
				}
			});
			sendApplicationEvent(new YApplicationEvent(InBankServer.RMI_CONNECTED));
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}

	/**
	 * Disattiva il servizio selezionato
	 */
	public void buttonDisattivaPressed() {
		try {
			new YWorker().execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.disattivaServizio();
					model.aggiornaStatoServizi();
					return null;
				}
			});
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}
}
