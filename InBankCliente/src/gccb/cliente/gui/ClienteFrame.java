package gccb.cliente.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import fi.mmm.yhteinen.swing.core.YIComponent;
import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YPanel;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;

import javax.swing.WindowConstants;


/**
 * Finestra principale del client per eBanking
 * @author Francesco Capozzo
 *
 */
public class ClienteFrame extends fi.mmm.yhteinen.swing.core.component.YFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8471625524995810215L;
	private YPanel contentPanel = new YPanel();
	private YButton buttonMovimenti = new YButton("Movimenti Conto");
	private JToolBar jToolBar1 = new JToolBar();
	private YButton buttonDettagli = new YButton("Dettagli Conto");
	private YButton buttonLogout = new YButton("Esci");
	private YButton buttonBonifico = new YButton("Esegui Bonifico");

	/**
	 * Inizializza il pannello
	 */
	public ClienteFrame() {
		initComponents();
		setMVCNames();
		initGUI();
		//YUIToolkit.guessViewComponents(this);
	}
	
	/**
	 * Imposta lo stato di attivazione dei pulsanti della toolbar
	 * @param newState il nuovo stato dei pulsanti
	 */
	public void setStatoPulsanti(boolean newState) {
		buttonMovimenti.setEnabled(newState);
		buttonDettagli.setEnabled(newState);
		buttonBonifico.setEnabled(newState);
	}
	
	private void setMVCNames() {
		getYProperty().put(YIComponent.MVC_NAME, "clienteFrame");
		buttonBonifico.getYProperty().put(YIComponent.MVC_NAME, "buttonBonifico");
		buttonLogout.getYProperty().put(YIComponent.MVC_NAME, "buttonLogout");
		buttonMovimenti.getYProperty().put(YIComponent.MVC_NAME, "buttonMovimenti");
		buttonDettagli.getYProperty().put(YIComponent.MVC_NAME, "buttonDettagli");
		
	}
	
	private void initComponents() {
		jToolBar1.add(buttonMovimenti);
		jToolBar1.add(buttonDettagli);
		jToolBar1.addSeparator();
		jToolBar1.add(buttonBonifico);
		jToolBar1.addSeparator();
		jToolBar1.add(buttonLogout);

	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				BorderLayout contentPanelLayout = new BorderLayout();
				contentPanel.setLayout(contentPanelLayout);
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(contentPanel, 0, 328, Short.MAX_VALUE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
					.addComponent(jToolBar1, GroupLayout.Alignment.LEADING, 0, 538, Short.MAX_VALUE)
					.addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
					    .addPreferredGap(jToolBar1, contentPanel, LayoutStyle.ComponentPlacement.INDENT)
					    .addComponent(contentPanel, 0, 514, Short.MAX_VALUE)
					    .addContainerGap(12, 12)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		pack();
		this.setSize(550, 430);
	}
	
	/**
	 * Imposta il contenuto del pannello principale
	 * @param newComponent il nuovo contenuto
	 */
	public void setContent(Component newComponent) {
		((YPanel)contentPanel).removeAll();
		((YPanel)contentPanel).add(newComponent, BorderLayout.CENTER);
		validate();
		repaint();
	}
	/**
	 * Ritorna una istanza del pulsante
	 * del bonifico presente nella toolbar
	 * @return il pulsante bonifico
	 */
	public YButton getButtonBonifico() {
		return buttonBonifico;
	}
	/**
	 * Ritorna una istanza del pulsante
	 * di uscita presente nella toolbar
	 * @return il pulsante esci
	 */
	public YButton getButtonLogout() {
		return buttonLogout;
	}

	/**
	 * Ritorna una istanza del pulsante
	 * della lista movimenti presente nella toolbar
	 * @return il pulsante movimenti
	 */
	public YButton getButtonMovimenti() {
		return buttonMovimenti;
	}

	public YButton getButtonDettagli() {
		return buttonDettagli;
	}
}
