package gccb.bancomat.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

/**
 * Pannello di benvenuto.
 * Visualizzato in attesa dell'inserimento di una nuova carta
 * bancomat
 * @author Francesco Capozzo
 *
 */
public class BancomatBenvenutoView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6805047897234474463L;
	private JLabel logo= new JLabel();
	
	/**
	 * Inizializza il pannello di benvenuto
	 */
	public BancomatBenvenutoView() {
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	private void initGUI() {
		
		logo.setIcon(new ImageIcon("images/bancomat.gif")); 
		add(logo);
	}
	
}
