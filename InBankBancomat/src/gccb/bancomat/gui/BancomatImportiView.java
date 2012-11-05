package gccb.bancomat.gui;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Pannello scelta importi.
 * Visualizza un serie di pulsanti corrispondenti alla cifra da erogare
 * @author Francesco Capozzo
 *
 */
public class BancomatImportiView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5740762972036733794L;
	private YButton button50 = new YButton("50");
	private YButton buttonAnnulla = new YButton("Annulla operazione");
	private JLabel importoLabel = new JLabel("Selezionare l'importo da erogare");
	private JPanel jPanel1 = new JPanel();
	private YButton button250 = new YButton("250");
	private YButton button500 = new YButton("500");
	private YButton buttonMax = new YButton("MAX");
	private YButton button150 = new YButton("150");
	private YButton button100 = new YButton("100");

	/**
	 * Inizializza il pannello
	 */
	public BancomatImportiView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		buttonAnnulla.setMvcName("buttonAnnulla");
		buttonMax.setMvcName("buttonMax");
		button50.setMvcName("button50");
		button100.setMvcName("button100");
		button150.setMvcName("button150");
		button250.setMvcName("button250");
		button500.setMvcName("button500");
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setSize(640, 480);
			
			GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1.setBorder(BorderFactory.createTitledBorder("Importi"));
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap(27, 27)
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addComponent(button50, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
				    .addComponent(button100, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
				    .addComponent(button150, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
				.addGap(169)
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addComponent(button250, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
				    .addComponent(button500, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonMax, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(24, 24));
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap(21, 21)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(button50, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				    .addComponent(button250, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
				.addGap(31)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(button100, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				    .addComponent(button500, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
				.addGap(25)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(button150, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonMax, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(21, 21));
			
			importoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			importoLabel.setFont(new java.awt.Font("AlArabiya",1,24));
			
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(26, 26)
				.addComponent(importoLabel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
				.addGap(16)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addComponent(buttonAnnulla, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(38, 38));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(49, 49)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(jPanel1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 532, GroupLayout.PREFERRED_SIZE)
				    .addComponent(importoLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 532, GroupLayout.PREFERRED_SIZE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(28)
				        .addComponent(buttonAnnulla, GroupLayout.PREFERRED_SIZE, 478, GroupLayout.PREFERRED_SIZE)
				        .addGap(26)))
				.addContainerGap(59, 59));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imposta il valore massimo prelevabile, disattivando tutti
	 * i pulsanti corrispondenti a valori che superano la soglia indicata
	 * @param max il valore massimo
	 */
	public void setMaxPrelevabile(float max) {
		buttonMax.setText(String.valueOf(max));
		button50.setEnabled((max >= 50));
		button100.setEnabled((max >= 100));
		button150.setEnabled((max >= 150));
		button250.setEnabled((max >= 250));
		button500.setEnabled((max >= 500));
	}
}
