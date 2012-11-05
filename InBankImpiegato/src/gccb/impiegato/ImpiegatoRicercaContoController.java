package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.YModelChangeEvent;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.IContoCorrenteBancario;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoRicercaContoView;
import gccb.impiegato.model.ImpiegatoContiModel;

/**
 * Controller per la gestione della ricerca di un conto 
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoRicercaContoController extends YController {

	private ImpiegatoRicercaContoView view = new ImpiegatoRicercaContoView();
	private ImpiegatoContiModel model;
	{
		model = ImpiegatoContiModel.getInstace();
	}
	
	/**
	 * Inizializza il controller impostando la vista e il modello associati
	 */
	public ImpiegatoRicercaContoController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Ricerca i conti in base ai dati presenti nel modello
	 * e visualizza i risultati
	 */
	public void buttonSearchPressed() {
		YWorker yw = new YWorker();
		try {
			yw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.doSearch();
					return null;
				}
			}, "Attendere prego...");
		} catch (Exception e) {
			yw.interrupt();
			ExceptionHandlerManager.process(e);
		}
	}

	/**
	 * Gestisce il cambio di selezione nella lista di risultati
	 * salvando il campo selezionato nel modello
	 * @param selectedConto il conto selezionato
	 */
	public void searchContiSelectionChanged(Object selectedConto) {
		model.setConto((IContoCorrenteBancario) selectedConto);
	}
	
	/**
	 * Cambia il tipo di ricerca effettuabile
	 */
	public void searchByChanged() {
		view.enableSearchField(model.getSearchBy());
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#modelChanged(fi.mmm.yhteinen.swing.core.YModelChangeEvent)
	 */
	@Override
	public void modelChanged(YModelChangeEvent event) {
		super.modelChanged(event);
		if ( event.getFieldName().equals("searchBy") ) {
			searchByChanged();
		}
	}
}
