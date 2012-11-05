package gccb.bancomat.external;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YDialog;
import fi.mmm.yhteinen.swing.core.component.text.YPasswordField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;


/**
 * Interfaccia grafica del driver virtuale di dispositivo esterno.
 * Permette di simulare l'inserimento della carta, la stampa dello
 * scontrino e di visualizzare lo stato del dispositivo
 * @author Francesco Capozzo
 *
 */
public class VirtualDeviceAdapterView extends YDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5376091743972990395L;
	private YButton buttonSimula = new YButton("Simula inserimento");
	private JLabel codiceEtichetta = new JLabel("Codice Bancomat:");
	private YPasswordField codiceCarta = new YPasswordField();
	private JTextPane stampanteVirtuale;
	private JScrollPane jScrollPane1;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JTextField statoField;
	private JLabel statoLabel;
	private JPanel jPanel1;
	
	
	/**
	 * Inizializza la vista
	 */
	public VirtualDeviceAdapterView() {
		setMVCNames();
		initGui();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		buttonSimula.setMvcName("buttonSimula");
		codiceCarta.setMvcName("codiceCarta");
	}
	
	protected void initGui() {

		GroupLayout BancomatInterazioneFrameLayout = new GroupLayout((JComponent)getContentPane());
		getContentPane().setLayout(BancomatInterazioneFrameLayout);
		BancomatInterazioneFrameLayout.setVerticalGroup(BancomatInterazioneFrameLayout.createSequentialGroup()
			.addContainerGap()
			.addComponent(getJPanel1(), GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
			.addComponent(getJPanel3(), 0, 169, Short.MAX_VALUE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, GroupLayout.PREFERRED_SIZE)
			.addComponent(getJPanel2(), GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
			.addContainerGap());
		BancomatInterazioneFrameLayout.setHorizontalGroup(BancomatInterazioneFrameLayout.createSequentialGroup()
			.addContainerGap()
			.addGroup(BancomatInterazioneFrameLayout.createParallelGroup()
			    .addGroup(GroupLayout.Alignment.LEADING, BancomatInterazioneFrameLayout.createSequentialGroup()
			        .addComponent(getJPanel2(), 0, 598, Short.MAX_VALUE)
			        .addGap(14))
			    .addComponent(getJPanel1(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 612, GroupLayout.PREFERRED_SIZE)
			    .addGroup(GroupLayout.Alignment.LEADING, BancomatInterazioneFrameLayout.createSequentialGroup()
			        .addComponent(getJPanel3(), GroupLayout.PREFERRED_SIZE, 596, GroupLayout.PREFERRED_SIZE)
			        .addGap(16)))
			.addContainerGap());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Pannello di interazione virtuale con Bancomat");
		this.setPreferredSize(new java.awt.Dimension(640, 300));
		this.setResizable(false);
		this.setSize(640, 300);
	}

	/**
	 * Ritorna il campo password corrispondente al codice
	 * della carta letto
	 * @return
	 */
	JPasswordField getCodiceCarta() {
		return codiceCarta;
	}
	
	private JButton getSimulaButton() {
		return buttonSimula;
	}
	
	/**
	 * Funzione corrispondente all'attivazione del dispositivo.
	 * Attiva il campo per l'inserimento del codice carta
	 * e il pulsante di simulazione inserimento
	 */
	public void attiva() {
		getStampanteVirtuale().setEnabled(true);
		getCodiceCarta().setEditable(true);
		getCodiceCarta().setFocusable(true);
		getSimulaButton().setEnabled(true);
		
	}
	/**
	 * Disattiva i campi inserimento del codice carta
	 * e il pulsante di simulazione inserimento
	 */
	public void disattiva() {
		getCodiceCarta().setEditable(false);
		getCodiceCarta().setFocusable(false);
		getSimulaButton().setEnabled(false);
		getStampanteVirtuale().setEnabled(false);
	}
	
	/**
	 * Resetta l'interfaccia grafica allo stato iniziale
	 */
	public void pulisci() {
		getCodiceCarta().setText("");
		getStampanteVirtuale().setText("");
		setTestoStato(VirtualDeviceAdapterController.STATI.IN_ATTESA);
	}
	
	private JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new JPanel();
			GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
			jPanel1.setLayout(jPanel1Layout);
			
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(codiceEtichetta, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
			.addComponent(getCodiceCarta(), 0, 340, Short.MAX_VALUE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(buttonSimula, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
			.addContainerGap());
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(buttonSimula, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
			    .addComponent(codiceEtichetta, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
			    .addComponent(getCodiceCarta(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
			.addContainerGap(48, 48));
		}
		return jPanel1;
	}

	private JLabel getStatoLabel() {
		if(statoLabel == null) {
			statoLabel = new JLabel();
			statoLabel.setText("Stato dispositivo:");
		}
		return statoLabel;
	}
	
	private JTextField getStatoField() {
		if(statoField == null) {
			statoField = new JTextField();
			statoField.setText("Non inizializzato");
			statoField.setEditable(false);
			statoField.setFocusable(false);
		}
		return statoField;
	}
	
	private JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new JPanel();
			GroupLayout jPanel2Layout = new GroupLayout((JComponent)jPanel2);
			jPanel2.setLayout(jPanel2Layout);
			jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
				.addComponent(getStatoLabel(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(0, 7, GroupLayout.PREFERRED_SIZE)
				.addComponent(getStatoField(), GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE));
			jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup()
				.addComponent(getStatoLabel(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				.addComponent(getStatoField(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
		}
		return jPanel2;
	}
	
	private JTextPane getStampanteVirtuale() {
		if(stampanteVirtuale == null) {
			stampanteVirtuale = new JTextPane();
			stampanteVirtuale.setEditable(false);
			stampanteVirtuale.setFocusable(false);
		}
		return stampanteVirtuale;
	}

	private JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new JPanel();
			GroupLayout jPanel3Layout = new GroupLayout((JComponent)jPanel3);
			jPanel3.setLayout(jPanel3Layout);
			jPanel3.setBorder(BorderFactory.createTitledBorder("Output Stampante Virtuale"));
			jPanel3Layout.setVerticalGroup(jPanel3Layout.createSequentialGroup()
				.addContainerGap(3, 3)
				.addComponent(getJScrollPane1(), GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(12, 12));
			jPanel3Layout.setHorizontalGroup(jPanel3Layout.createSequentialGroup()
				.addContainerGap(12, 12)
				.addComponent(getJScrollPane1(), GroupLayout.PREFERRED_SIZE, 566, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(8, 8));
		}
		return jPanel3;
	}
	
	private JScrollPane getJScrollPane1() {
		if(jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new java.awt.Dimension(3, 3));
			jScrollPane1.setViewportView(getStampanteVirtuale());
		}
		return jScrollPane1;
	}
	/**
	 * Imposta il campo corrispondente allo stato del dispositivo
	 * @param stato il nuovo stato
	 */
	public void setTestoStato(VirtualDeviceAdapterController.STATI stato) {
		getStatoField().setText(stato.toString());
	}
	/**
	 * Visualizza a schermo la stringa che dovrebbe essere stampata
	 * dal dispositivo esterno
	 * @param testo il testo dello scontrino
	 */
	public void setStampanteVirtuale(String testo) {
		getStampanteVirtuale().setText(testo);
	}
}
