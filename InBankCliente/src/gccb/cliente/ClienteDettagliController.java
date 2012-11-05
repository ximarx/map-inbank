package gccb.cliente;

import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.YModel;
import gccb.cliente.gui.ClienteDettagliView;

/**
 * Controller del pannello dettagli
 * @author Francesco Capozzo
 *
 */
public class ClienteDettagliController extends YController {

	private ClienteDettagliView view = new ClienteDettagliView();
	private YModel model;
	
	/**
	 * Inizializza il controller
	 */
	public ClienteDettagliController() {}
	
	/**
	 * Setta il modello relativo alla vista
	 * @param model il modello
	 */
	public void lazyInit(YModel model) {
		this.model  = model;
		setUpMVC(this.model, view);
	}
	
	
}
