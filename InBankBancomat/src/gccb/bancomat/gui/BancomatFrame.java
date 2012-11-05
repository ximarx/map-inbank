package gccb.bancomat.gui;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.WindowConstants;
import fi.mmm.yhteinen.swing.core.component.YFrame;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

/**
 * Finestra principale del bancomat
 * Contiene i riferimenti ai pannelli figli visualizzabili
 * @author Francesco Capozzo
 *
 */
public class BancomatFrame extends YFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4411956961958033946L;

	private YPanel mainPanel;
	
	/**
	 * Pannello di benvenuto
	 */
	public BancomatBenvenutoView BENVENUTO = new BancomatBenvenutoView();
	/**
	 * Pannello operazioni
	 */
	public BancomatOperazioniView OPERAZIONI = new BancomatOperazioniView();
	/**
	 * Pannello di login
	 */
	public YPanel LOGIN = new BancomatLoginView();
	/**
	 * Pannello selezione importi
	 */
	public BancomatImportiView IMPORTI = new BancomatImportiView();
	/**
	 * Pannello di conferma operazione
	 */
	public BancomatConfermaView CONFERMA = new BancomatConfermaView();
	/**
	 * Pannello di fine operazione
	 */
	public BancomatArrivederciView ARRIVEDERCI = new BancomatArrivederciView();
	/**
	 * Pannello di errore
	 */
	public BancomatErroreView ERRORE = new BancomatErroreView();
	
	/**
	 * Inizializza la finestra impostando il pannello di benvenuto
	 * come pannello principale
	 */
	public BancomatFrame() {
		initGUI();
		setMvcName("bancomatFrame");
		YUIToolkit.guessViewComponents(this);
	}
	
	private void initGUI() {
		try {
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			this.setTitle("InBank Bancomat Client");
			this.setMaximumSize(new java.awt.Dimension(640, 480));
			this.setMinimumSize(new java.awt.Dimension(640, 480));
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(getMainPanel(), 0, 452, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addComponent(getMainPanel(), 0, 636, Short.MAX_VALUE));
			this.setResizable(false);
			pack();
			this.setSize(640, 480);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Restituisce una istanza del pannello principale,
	 * contenutore dei sotto pannelli
	 * @return il pannello principale
	 */
	public YPanel getMainPanel() {
		if(mainPanel == null) {
			mainPanel = new YPanel();
			mainPanel.setLayout(new BorderLayout());
		}
		return mainPanel;
	}
	
	/**
	 * Imposta il contenuto del pannello principale
	 * @param panel il nuovo pannello da inserire
	 */
	public void setContent(YPanel panel) {
		mainPanel.removeAll();
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.revalidate();
		mainPanel.repaint();
	}
}
