package gccb.common.gui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

/**
 * Estende la classe YTextField per gestire anche gli eventi di pressione dei tasti
 * Oltre al normale MVC_NAME + KeyReleased viene anche cercata ed invocata
 * ad ogni digitazione la funzione corrispondente a MVC_NAME + il codice del tasto premuto + KeyPressed
 * Questo permette di intercettare anche la digitazione dei tasti speciali come ENTER e TAB
 * 
 * <ul>
 * <li>Il tasto ENTER corrisponde a 10</li>
 * </ul> 
 *   
 * @author Francesco Capozzo
 *
 */
public class YKeyBasedEventTextField extends YTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6698933809924357076L;

	/**
	 * Sovrascrive la funzione {@link YTextField#addViewListener(YController)} in modo 
	 * da chiamare la funzione corrispondente al tasto premuto
	 */
	@Override
	public void addViewListener(final YController controller) {
		this.addFocusListener(new FocusAdapter() {
		    public void focusLost(FocusEvent ev) {
		    	controller.updateModelAndController(YKeyBasedEventTextField.this);
		    }
		});
 		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (isKeyEventsEnabled()) {
					String methodName = YUIToolkit.createMVCMethodName(YKeyBasedEventTextField.this, e.getKeyCode() + "KeyReleased");
					if (methodName != null) {
						controller.invokeMethodIfFound(methodName);
					}
					String methodName2 = YUIToolkit.createMVCMethodName(YKeyBasedEventTextField.this, "KeyReleased");
					if (methodName2 != null) {
						controller.invokeMethodIfFound(methodName2);
					}
				}
			}
		});
	}
	
}
