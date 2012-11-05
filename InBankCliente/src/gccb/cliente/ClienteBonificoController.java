package gccb.cliente;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.cliente.gui.ClienteBonificoView;
import gccb.cliente.model.ClienteBonificoModel;
import gccb.exception.handler.ExceptionHandlerManager;

/**
 * Controller del pannello bonifico
 * @author Francesco Capozzo
 *
 */
public class ClienteBonificoController extends YController {

	private ClienteBonificoView view = new ClienteBonificoView();
	private ClienteBonificoModel model = new ClienteBonificoModel();
	
	/**
	 * Inizializza il controller
	 */
	public ClienteBonificoController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Esegue il bonifico in base ai dati inseriti nella
	 * vista
	 */
	public void buttonAcceptPressed() {
		YWorker aw = new YWorker();
		copyToModel("fieldConto");
		copyToModel("fieldImporto");
		try {
			aw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.doBonifico();
					model.setContoDestinazione(0);
					model.setImporto(new Float(0));
					sendApplicationEvent(new YApplicationEvent(InBankCliente.DATA_CHANGED));
					return null;
				}
			}, "Attendere prego...");
		} catch (Exception e) {
			aw.interrupt();
			ExceptionHandlerManager.process(e);
		}
	}
}
