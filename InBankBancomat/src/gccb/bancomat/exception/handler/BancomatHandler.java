package gccb.bancomat.exception.handler;

import fi.mmm.yhteinen.swing.core.YController;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.handler.IExceptionHandler;

/**
 * Handler delle eccezioni per bancomat.
 * Permette di utilizzare {@link ExceptionHandlerManager}
 * per visualizzare gli errori nell'interfaccia del bancomat
 * in maniera trasparente all'applicazione
 * @author Francesco Capozzo
 *
 */
public class BancomatHandler implements IExceptionHandler {

	private YController controller;
	
	/**
	 * Inizializza l'handler impostando l'error controller
	 * da utilizzare per visualizzare gli errori
	 * @param controller
	 */
	public BancomatHandler(YController controller) {
		this.controller = controller;
	}
	
	/**
	 * Processa l'eccezione indicata richiamando l'error controller indicato
	 * per visualizzare il messaggio di errore
	 */
	public void processError(Exception e) {
		controller.invokeMethodIfFound("showError", new String[] {
				"Si e' verificato un errore",
				e.getMessage()
		}, new Class[] {
			String.class,
			String.class
		});
	}
}
