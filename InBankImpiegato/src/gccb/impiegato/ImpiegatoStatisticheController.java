package gccb.impiegato;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import gccb.exception.GccbException;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoStatisticheView;
import gccb.impiegato.model.ImpiegatoStatisticheModel;

/**
 * Controller per la visualizzazione delle statistiche di sistema
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoStatisticheController extends YController {

	private ImpiegatoStatisticheView view = new ImpiegatoStatisticheView();
	private ImpiegatoStatisticheModel model = new ImpiegatoStatisticheModel();
	
	/**
	 * Inizializza la classe impostando il modello e la vista associati al controller
	 * Il controller viene registrato per gli eventi
	 * {@link InBankImpiegato#CLIENT_CONNECTED}
	 * {@link InBankImpiegato#CONTO_CLOSED}
	 */
	public ImpiegatoStatisticheController() {
		setUpMVC(model, view);
		register(InBankImpiegato.CLIENT_CONNECTED);
		register(InBankImpiegato.CONTO_CLOSED);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	@Override
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankImpiegato.CLIENT_CONNECTED) ) {
			try {
				model.updateStatistiche();
				model.updatePreferences();
			} catch (GccbException e) {
				ExceptionHandlerManager.process(e);
			}
		} else if ( event.getName().equals(InBankImpiegato.CONTO_CLOSED) ) {
			try {
				model.updateStatistiche();
			} catch (GccbException e) {
				ExceptionHandlerManager.process(e);
			}
		}
	}
}
