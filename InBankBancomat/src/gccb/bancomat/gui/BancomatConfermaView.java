package gccb.bancomat.gui;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import java.awt.Component;


import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Pannello conferma. Visualizza un richiesta
 * di conferma di una operazione generica e
 * i pulsanti per confermare o annullare l'operazione
 * @author Francesco Capozzo
 *
 */
public class BancomatConfermaView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3994768711994600237L;
	private JLabel corpo = new JLabel("Prelevare l'importo di");
	private YButton buttonConferma = new YButton("Conferma");
	private YButton buttonAnnulla = new YButton("Annulla");
	private JLabel domanda = new JLabel("Conferma di prelievo denaro contante:");
	private YLabel labelImporto = new YLabel();

	/**
	 * Inizializza il pannello
	 */
	public BancomatConfermaView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		buttonAnnulla.setMvcName("buttonAnnulla");
		buttonConferma.setMvcName("buttonConferma");
		labelImporto.setMvcName("importo");
	}
	
	private void initGUI() {
		try {
			
			domanda.setFont(new java.awt.Font("Dialog",3,20));
			labelImporto.setFont(new java.awt.Font("Dialog",1,22));
			
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(104, 104)
				.addComponent(domanda, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(corpo, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(18)
				        .addComponent(labelImporto, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
				        .addGap(19)))
				.addGap(27)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(buttonAnnulla, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(buttonConferma, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				        .addGap(19)))
				.addContainerGap(159, 159));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(94, 94)
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(corpo, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(labelImporto, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
				    .addComponent(domanda, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(49)
				        .addComponent(buttonAnnulla, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
				        .addGap(104)
				        .addComponent(buttonConferma, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				        .addGap(117)))
				.addContainerGap(66, 66));
			thisLayout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonConferma, buttonAnnulla});
			thisLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonAnnulla, buttonConferma});
			this.setSize(640, 480);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
