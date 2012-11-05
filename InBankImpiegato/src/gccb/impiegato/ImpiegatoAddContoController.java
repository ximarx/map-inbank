package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoAddContoView;
import gccb.impiegato.model.ImpiegatoContiModel;

/**
 * Controller per la finestra di aggiunta di un nuovo conto
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoAddContoController extends YController {

	private ImpiegatoAddContoView view = new ImpiegatoAddContoView();
	private ImpiegatoContiModel model = ImpiegatoContiModel.getInstace();
	
	/**
	 * Inizializza la vista e il modello associati al controller
	 */
	public ImpiegatoAddContoController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Aggiunge il nuovo conto con le informazioni presenti nel modello
	 * e richiama il controller padre per la visualizzazione dei
	 * dettagli del conto appena aggiunto
	 */
	public void buttonConfermaPressed() {
		YWorker yw = new YWorker();
		try {
			yw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.doAggiungiConto();
					return null;
				}
			});
			getParent().invokeMethodIfFound("ricercaContoDialogSelezionaPressed");
		} catch (Exception e) {
			yw.interrupt();
			ExceptionHandlerManager.process(e);
		}
	}
	/**
	 * Invoca il controller padre per la visualizzazione della finestra di ricerca
	 * del correntista per il nuovo conto tramite la funzione
	 * {@link ImpiegatoFrameController#newContoRicercaCorrentista()}
	 */
	public void buttonCercaIntestatarioPressed() {
		try {
			getParent().invokeMethodIfFound("newContoRicercaCorrentista");
		} catch (NullPointerException e) {}
	}
	
	/**
	 * Invoca il controller padre per la visualizzazione della finestra di aggiunta
	 * del correntista per il nuovo conto tramite la funzione
	 * {@link ImpiegatoFrameController#newContoAggiungiCorrentista()}
	 */
	public void buttonAggiungiCorrentistaPressed() {
		try {
			getParent().invokeMethodIfFound("newContoAggiungiCorrentista");
		} catch (NullPointerException e) {}
	}
	
	/**
	 * Annulla la creazione del nuovo conto cancellando i dati presenti
	 * nel modello
	 */
	public void buttonAnnullaPressed() {
		model.resetCreazione();
	}
}
