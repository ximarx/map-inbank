package gccb.server.gui;
import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YDialog;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import fi.mmm.yhteinen.swing.core.component.text.YPasswordField;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Il pannello della vista della lista impiegati
 * @author Francesco Capozzo
 *
 */
public class ServerListaImpiegatiWidgetView extends YPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7347667874430457890L;
	private YColumn[] columnTableImpiegati = new YColumn[]{
			new YColumn("idImpiegato", "ID" ),
			new YColumn("username", "Username" ),
			new YColumn("visualizzato", "Visualizzato" )
	};
	private YTable tableImpiegati = new YTable(columnTableImpiegati);
	private JPanel pannelloPulsanti = new JPanel();
	private YButton buttonRimuovi = new YButton("Rimuovi");
	private YButton buttonCambiaPassword = new YButton("Modifica");
	private JScrollPane jScrollPane1 = new JScrollPane(tableImpiegati);
	private YButton buttonAggiungi = new YButton("Aggiungi");

	
	private YButton buttonConferma = new YButton("Conferma");
	private YButton buttonAnnulla = new YButton("Annulla");
	private YTextField fieldVisualizzato = new YTextField();
	private YPasswordField fieldPassword = new YPasswordField();
	private YTextField fieldUsername = new YTextField();

	private AddDialog addDialog = new AddDialog();
	{
		addDialog.setLocationRelativeTo(this);
		addDialog.setTitle("Dettagli nuovo impiegato");
	}
	
	/**
	 * Inizializza il pannello
	 */
	public ServerListaImpiegatiWidgetView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		tableImpiegati.setMvcName("impiegati");
		buttonRimuovi.setMvcName("buttonRimuovi");
		buttonCambiaPassword.setMvcName("buttonCambia");
		buttonAggiungi.setMvcName("buttonAggiungi");
		buttonConferma.setMvcName("buttonConfermaAggiunta");
		buttonAnnulla.setMvcName("buttonAnnullaAggiunta");
		fieldUsername.setMvcName("newUsername");
		fieldPassword.setMvcName("newPassword");
		fieldVisualizzato.setMvcName("newVisualizzato");
	}
	/**
	 * Imposta lo stato di attivazione dei pulsanti modifica e rimuovi
	 * @param newStato 
	 */
	public void setStatoPulsanti(boolean newStato) {
		buttonRimuovi.setEnabled(newStato);
		buttonCambiaPassword.setEnabled(newStato);
	}
	
	/**
	 * Imposta la visibilità del pannello per l'aggiunta di un nuovo impiegato
	 * @param stato
	 */
	public void setStatoPannelloAggiungi(boolean stato) {
		addDialog.setVisible(stato);
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(452, 275));
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setBorder(BorderFactory.createTitledBorder("Pannello gestione Impiegati"));
			GroupLayout pannelloPulsantiLayout = new GroupLayout((JComponent)pannelloPulsanti);
			pannelloPulsanti.setLayout(pannelloPulsantiLayout);
			pannelloPulsantiLayout.setVerticalGroup(pannelloPulsantiLayout.createSequentialGroup()
				.addGap(6)
				.addGroup(pannelloPulsantiLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(buttonRimuovi, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonCambiaPassword, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonAggiungi, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(7));
			pannelloPulsantiLayout.setHorizontalGroup(pannelloPulsantiLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(buttonAggiungi, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
				.addGap(22)
				.addComponent(buttonCambiaPassword, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
				.addGap(0, 19, Short.MAX_VALUE)
				.addComponent(buttonRimuovi, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());
			pannelloPulsantiLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonAggiungi, buttonCambiaPassword, buttonRimuovi});
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(pannelloPulsanti, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jScrollPane1, 0, 191, Short.MAX_VALUE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(pannelloPulsanti, GroupLayout.Alignment.LEADING, 0, 418, Short.MAX_VALUE)
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 418, Short.MAX_VALUE))
				.addContainerGap());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Pannello per l'aggiunta di un nuovo impiegato
	 * @author Francesco Capozzo
	 *
	 */
	private class AddDialog extends YDialog {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 9142862162517903002L;
		private JPanel mainPanel = new JPanel();
		private JLabel labelUsername = new JLabel("Username:");
		private JLabel labelVisualizzato = new JLabel("Visualizzato:");
		private JLabel labelPassword = new JLabel("Password:");
		private JPanel jPanel1 = new JPanel();

		public AddDialog() {
			initGUI();
		}

		private void initGUI() {

			GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addComponent(labelVisualizzato, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelPassword, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelUsername, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
				.addGap(24)
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addComponent(fieldUsername, GroupLayout.Alignment.LEADING, 0, 198, Short.MAX_VALUE)
				    .addComponent(fieldPassword, GroupLayout.Alignment.LEADING, 0, 198, Short.MAX_VALUE)
				    .addComponent(fieldVisualizzato, GroupLayout.Alignment.LEADING, 0, 198, Short.MAX_VALUE)));
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(fieldUsername, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelUsername, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(fieldPassword, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelPassword, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(fieldVisualizzato, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelVisualizzato, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)));
			
			
			GroupLayout mainPanelLayout = new GroupLayout((JComponent)mainPanel);
			mainPanel.setLayout(mainPanelLayout);
			mainPanel.setBorder(BorderFactory.createTitledBorder("Nuovo impiegato"));
			mainPanelLayout.setHorizontalGroup(mainPanelLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(mainPanelLayout.createParallelGroup()
				    .addComponent(jPanel1, GroupLayout.Alignment.LEADING, 0, 322, Short.MAX_VALUE)
				    .addGroup(GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
				        .addComponent(buttonAnnulla, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 80, Short.MAX_VALUE)
				        .addComponent(buttonConferma, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap());
			mainPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonAnnulla, buttonConferma});
			mainPanelLayout.setVerticalGroup(mainPanelLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
				.addGap(0, 33, Short.MAX_VALUE)
				.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(buttonAnnulla, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonConferma, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap());
			mainPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonConferma, buttonAnnulla});
			
			GroupLayout thisLayout = new GroupLayout((JComponent) getContentPane());
			getContentPane().setLayout(thisLayout);
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(mainPanel, 0, 193, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addComponent(mainPanel, 0, 355, Short.MAX_VALUE));
			this.setSize(365, 223);
		}
	}	
}
