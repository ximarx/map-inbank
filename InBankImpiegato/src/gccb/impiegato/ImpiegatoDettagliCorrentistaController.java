package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.YIComponent;
import gccb.impiegato.gui.ImpiegatoDettagliCorrentistaView;
import gccb.impiegato.model.ImpiegatoPersoneModel;

/**
 * Controller per la gestione dei dettagli di un correntista
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoDettagliCorrentistaController extends YController {

	private ImpiegatoDettagliCorrentistaView view = new ImpiegatoDettagliCorrentistaView();
	private ImpiegatoPersoneModel model;
	{
		model = ImpiegatoPersoneModel.getInstace();
	}
	
	/**
	 * Inizializza il controller impostando il modello e la vista
	 * associati
	 */
	public ImpiegatoDettagliCorrentistaController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Imposta il widget nel pannello in basso
	 * @param bottomWidget il widget da inserire
	 */
	public void setWidget(YIComponent bottomWidget ) {
		view.setWidgets(bottomWidget);
	}
	
	/**
	 * Visualizza la finestra di aggiunta conto
	 * invocando {@link ImpiegatoFrameController#itemAggiungiContoSelected()}
	 */
	public void buttonCreaContoPressed() {
		try {
			getParent().invokeMethodIfFound("itemAggiungiContoSelected");
		} catch ( NullPointerException npe) {}
	}
}
