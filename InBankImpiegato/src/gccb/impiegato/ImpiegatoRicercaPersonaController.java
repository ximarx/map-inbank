package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.YModelChangeEvent;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.IPersona;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoRicercaPersonaView;
import gccb.impiegato.model.ImpiegatoPersoneModel;

/**
 * Controller per la ricerca di un correntista
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoRicercaPersonaController extends YController {

	private ImpiegatoRicercaPersonaView view = new ImpiegatoRicercaPersonaView();
	private ImpiegatoPersoneModel model;
	{
		model = ImpiegatoPersoneModel.getInstace();
	}
	
	/**
	 * Inizializza il controller impostando il modello e la vista associati
	 */
	public ImpiegatoRicercaPersonaController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Esegue la ricerca in base ai parametri inseriti nel modello
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
	 * impostando il valore selezionato nel modello
	 * @param selectedConto il correntista selezionato
	 */
	public void personeSelectionChanged(Object selectedConto) {
		model.setPersona((IPersona) selectedConto);
	}
	
	/**
	 * Cambia il tipo di ricerca nella modalità selezionata nella vista
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