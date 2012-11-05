package gccb.exception.handler;

import fi.mmm.yhteinen.swing.core.YController;
import gccb.exception.handler.IExceptionHandler;

/**
 * Handler delle eccezioni per client grafici.
 * Tenta di invocare la funzione showError se presente nel controller
 * memorizzato
 * 
 * @author Francesco Capozzo
 * @author Marco D'Addabbo
 *
 */
public class GuiHandler implements IExceptionHandler {

	/**
	 * L'error controller indicato durante la costruzione
	 */
	private YController controller;

	/**
	 * Costruisce l'handler linkando il controller da usare per per visualizzare
	 * l'eccezione
	 * @param controller
	 */
	public GuiHandler(YController controller) {
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.exceptionHandler.IExceptionHandler#processError(java.lang.Exception)
	 */
	public void processError(Exception e) {
		controller.invokeMethodIfFound("showError", new Object[]{e}, new Class[] {Exception.class});
	}
}
