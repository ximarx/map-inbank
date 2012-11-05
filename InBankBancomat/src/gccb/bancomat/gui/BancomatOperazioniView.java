package gccb.bancomat.gui;

import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

/**
 * Pannello per la selezione delle operazioni
 * da eseguire
 * @author Francesco Capozzo
 *
 */
public class BancomatOperazioniView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4714642459971394356L;
	private YButton buttonSaldo = new YButton("Prelievo e saldo");
	private YButton buttonPrelievo = new YButton("Prelievo");
	private YLabel labelIntestazione = new YLabel("Selezionare l'operazione");
	
	/**
	 * Inizializza il pannello
	 */
	public BancomatOperazioniView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		buttonSaldo.setMvcName("buttonSaldo");
		buttonPrelievo.setMvcName("buttonPrelievo");
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setSize(640, 480);
			this.setLayout(thisLayout);
			
			buttonSaldo.setFont(new java.awt.Font("AlArabiya",1,16));
			buttonPrelievo.setFont(new java.awt.Font("AlArabiya",1,16));
			
			labelIntestazione.setFont(new java.awt.Font("AlArabiya",1,28));
			labelIntestazione.setHorizontalAlignment(SwingConstants.CENTER);
			
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(77, 77)
				.addComponent(labelIntestazione, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
				.addGap(78)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(buttonSaldo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonPrelievo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(64, 64));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(55, 55)
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(buttonSaldo, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
				        .addGap(80)
				        .addComponent(buttonPrelievo, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
				    .addComponent(labelIntestazione, GroupLayout.Alignment.LEADING, 0, 532, Short.MAX_VALUE))
				.addContainerGap(53, 53));
			thisLayout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonSaldo, buttonPrelievo});
			thisLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonSaldo, buttonPrelievo});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
