package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoAddCorrentistaView;
import gccb.impiegato.model.ImpiegatoPersoneModel;

/**
 * Controller della finestra di aggiunta del nuovo correntista
 * Permette la visualizzazione della finestra di aggiunta correntista
 * e la aggiunta di un nuovo cliente con i dati inseriti nella finestra
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoAddCorrentistaController extends YController {

	private ImpiegatoAddCorrentistaView view = new ImpiegatoAddCorrentistaView();
	private ImpiegatoPersoneModel model = ImpiegatoPersoneModel.getInstace();
	
	/**
	 * Inizializza il controller impostando la vista e il modello associati
	 */
	public ImpiegatoAddCorrentistaController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Esegue il salvataggio del nuovo correntista
	 * i cui dati sono presenti nel vista
	 */
	public void save() {
		copyToModel("newNome");
		copyToModel("newCognome");
		copyToModel("newIndirizzo");
		YWorker yw = new YWorker();
		try {
			yw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.aggiungiPersona();
					model.resetNew();
					return null;
				}
			}, "Attendere prego...");
		} catch (Exception e) {
			yw.interrupt();
			ExceptionHandlerManager.process(e);
		}
	}
}
