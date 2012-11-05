package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.IBancomat;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoListaBancomatView;
import gccb.impiegato.model.ImpiegatoBancomatModel;

/**
 * Controller per la gestione della lista bancomat associati ad un conto
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoListaBancomatController extends YController {

	private ImpiegatoListaBancomatView view = new ImpiegatoListaBancomatView();
	private ImpiegatoBancomatModel model;
	{
		model = ImpiegatoBancomatModel.getInstace(); 
	}
	
	/**
	 * Inizializza il controller impostando la vista e il modello associati
	 */
	public ImpiegatoListaBancomatController() {
		setUpMVC(model, view);
		view.setButtonState(false);
	}
	
	/**
	 * Gestiste il cambio di selezione nella lista dei bancomat
	 * attivando o disattivando il pulsante per la cancellazione del bancomat
	 * e inserendo il valore selezionato nel modello
	 * @param o l'elemento selezionato
	 */
	public void bancomatsSelectionChanged(Object o) {
		model.setBancomatSelezionato((IBancomat) o);
		view.setButtonState(o != null);
	}
	
	/**
	 * Esegue la cancellazione del bancomat selezionato
	 */
	public void buttonDelBancomatPressed() {
		YWorker yw = new YWorker();
		try {
			yw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.rimuoviBancomat();
					model.aggiornaListaBancomat();
					return null;
				}
			}, "Attendere prego...");
			sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CONTO_CHANGED));
		} catch (Exception e) {
			yw.interrupt();
			ExceptionHandlerManager.process(e);
		}
	}
	
	/**
	 * Visualizza la finestra per l'aggiunta del nuovo bancomat
	 */
	public void buttonAddBancomatPressed() {
		view.setAddButtonDialogVisible(true);
	}
	
	/**
	 * Aggiunge il nuovo bancomat con le infomazioni presenti 
	 * nella finestra di aggiunta bancomat visualizzata
	 * da {@link #buttonAddBancomatPressed()}
	 */
	public void buttonConfermaPressed() {
		copyToModel("newCodice");
		copyToModel("newMassimo");
		YWorker yw = new YWorker();
		try {
			yw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.aggiungiBancomat();
					model.aggiornaListaBancomat();
					return null;
				}
			}, "Attendere prego...");
			view.setAddButtonDialogVisible(false);
			sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CONTO_CHANGED));
		} catch (Exception e) {
			yw.interrupt();
			view.setAddButtonDialogVisible(false);
			ExceptionHandlerManager.process(e);
		}
		
	}
	
	/**
	 * Annulla l'inserimento di un nuovo bancomat
	 */
	public void buttonAnnullaPressed() {
		view.setAddButtonDialogVisible(false);
	}
}
