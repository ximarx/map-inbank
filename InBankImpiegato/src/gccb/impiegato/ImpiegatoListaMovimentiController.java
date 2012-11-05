package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoListaMovimentiView;
import gccb.impiegato.model.ImpiegatoMovimentiModel;

/**
 * Controller per la gestione della lista movimenti associati ad un conto
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoListaMovimentiController extends YController {

	private ImpiegatoListaMovimentiView view = new ImpiegatoListaMovimentiView();
	private ImpiegatoMovimentiModel model;
	{
		model = ImpiegatoMovimentiModel.getInstace();
	}
	
	/**
	 * Inizializza il controller caricando la vista e il modello associati
	 * e registrando il controller per gli eventi
	 * {@link InBankImpiegato#CONTO_CHANGED}
	 */
	public ImpiegatoListaMovimentiController() {
		setUpMVC(model, view);
		register(InBankImpiegato.CONTO_CHANGED);
	}
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankImpiegato.CONTO_CHANGED) ) {
			YWorker yw = new YWorker();
			try {
				yw.execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						model.aggiornaListaTransazioni();
						return null;
					}
				}, "Attendere prego...");
			} catch (Exception e) {
				ExceptionHandlerManager.process(e);
			}
		}
	}
}
