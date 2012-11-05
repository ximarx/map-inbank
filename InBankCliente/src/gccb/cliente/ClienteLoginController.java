package gccb.cliente;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.cliente.gui.ClienteLoginView;
import gccb.cliente.model.ClienteLoginModel;
import gccb.exception.handler.ExceptionHandlerManager;

/**
 * Controller del pannello Login
 * @author Francesco Capozzo
 *
 */
public class ClienteLoginController extends YController {

	private ClienteLoginView view = new ClienteLoginView();
	private ClienteLoginModel model = new ClienteLoginModel();
	
	/**
	 * Inizializza il controller
	 */
	public ClienteLoginController() {
		YUIToolkit.setUpMVC(model, view, this);
	}
	
	/**
	 * Esegue il login in base alle informazioni inserite nella
	 * vista.
	 */
	public void buttonLoginPressed() {
		try {
			copyToModel("username");
			copyToModel("password");
			new YWorker().execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.connect();
					return null;
				}
			}, " Attendere prego... ");
			sendApplicationEvent(new YApplicationEvent(InBankCliente.CLIENT_CONNECTED));
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
			model.setUsername("");
			model.setPassword("");
		}
	}
}
