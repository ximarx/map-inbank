package gccb.bancomat.gui;

import java.awt.Component;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Pannello errore. Viene utilizzato dall'errorController
 * per visualizzare i messaggi di errori inoltrati dalla handler di eccezioni
 * @author Francesco Capozzo
 *
 */
public class BancomatErroreView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6130185723698523627L;
	private YButton buttonAnnulla = new YButton("Annulla operazione");
	private JLabel intestazioneLabel;
	private JLabel erroreLabel;
	private String intestazione = "";
	private String errore = "";
	
	/**
	 * Inizializza il pannello
	 */
	public BancomatErroreView() {
		buttonAnnulla.setMvcName("buttonAnnulla");
		initGui();
		YUIToolkit.guessViewComponents(this);
	}
	
	protected void initGui() {
		try {
			
			intestazioneLabel = new JLabel();
			intestazioneLabel.setText(intestazione);
			intestazioneLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			erroreLabel = new JLabel();
			erroreLabel.setText(errore);
			erroreLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
				thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(104, 104)
				.addComponent(intestazioneLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(erroreLabel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
				.addGap(33)
				.addComponent(buttonAnnulla, 0, 42, Short.MAX_VALUE)
				.addContainerGap(152, 152));
				thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
					.addContainerGap(80, 80)
					.addGroup(thisLayout.createParallelGroup()
					    .addComponent(intestazioneLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
					    .addComponent(erroreLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE)
					    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
					        .addGap(26)
					        .addComponent(buttonAnnulla, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE)
					        .addGap(37)))
					.addContainerGap(80, 80));
				thisLayout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonAnnulla});
			this.setSize(640, 480);
				thisLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonAnnulla});
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/**
	 * Imposta l'intestazione dell'errore
	 * @param text il titolo dell'errore
	 */
	public void setIntestazione(String text) {
		intestazioneLabel.setText(text);
	}
	
	/**
	 * Imposta il messaggio di errore
	 * @param text il corpo del messaggio
	 */
	public void setMessaggio(String text) {
		erroreLabel.setText(text);
	}
}
