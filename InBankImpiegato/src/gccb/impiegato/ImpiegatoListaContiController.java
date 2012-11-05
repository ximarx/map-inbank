package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YController;
import gccb.IContoCorrenteBancario;
import gccb.impiegato.gui.ImpiegatoListaContiView;
import gccb.impiegato.model.ImpiegatoContiModel;

/**
 * Controller per la gestione della lista conti
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoListaContiController extends YController {

	private ImpiegatoListaContiView view = new ImpiegatoListaContiView();
	private ImpiegatoContiModel model;
	{
		model = ImpiegatoContiModel.getInstace();
	}
	
	/**
	 * Inizializza il controller impostando il modello e la vista associati
	 */
	public ImpiegatoListaContiController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Gestisce il doppio click su un elemento della lista conti
	 * visualizzando i dettagli del conto tramite la funzione
	 * {@link ImpiegatoFrameController#ricercaContoDialogSelezionaPressed()}
	 * @param o
	 */
	public void searchContiDoubleClicked(Object o) {
		ImpiegatoContiModel.getInstace().setConto((IContoCorrenteBancario) o);
		try {
			getParent().invokeMethodIfFound("ricercaContoDialogSelezionaPressed");
		} catch (NullPointerException npe) {}
	}
}
