package gccb.server.gui;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;

/**
 * Pannello della widget della barra di stato
 * @author Francesco Capozzo
 *
 */
public class ServerConnectionWidgetView extends YPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9002535165218023330L;
	private YLabel statoConnessione = new YLabel();

	/**
	 * Inizializza il pannello
	 */
	public ServerConnectionWidgetView() {
		statoConnessione.setMvcName("statoConnessione");
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}

	/**
	 * Inizializza i componenti grafici
	 */
	private void initGUI() {
		{
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
				thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(statoConnessione, GroupLayout.Alignment.LEADING, 0, 431, Short.MAX_VALUE));
				thisLayout.setVerticalGroup(thisLayout.createParallelGroup()
				.addComponent(statoConnessione, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE));
			this.setPreferredSize(new java.awt.Dimension(431, 15));
			this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}

	}
	
}
