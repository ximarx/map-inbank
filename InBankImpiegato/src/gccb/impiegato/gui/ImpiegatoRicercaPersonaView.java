package gccb.impiegato.gui;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YCheckBox;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.buttongroup.YButtonGroup;
import fi.mmm.yhteinen.swing.core.component.buttongroup.YRadioButton;
import fi.mmm.yhteinen.swing.core.component.field.YFormattedTextField;
import fi.mmm.yhteinen.swing.core.component.field.YIntegerFormatter;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;


/**
 * Vista per la ricerca di un correntista
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoRicercaPersonaView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711615464316622751L;
	private YColumn[] tableRisultatiColumn = {
			new YColumn("idPersona", "ID", 10),
			new YColumn("nome", "Nome cliente"),
			new YColumn("cognome", "Cognome cliente"),
			new YColumn("indirizzo", "Indirizzo", 20)
			};
	private YTable tableRisultati = new YTable(tableRisultatiColumn);

	private YButtonGroup groupSearchBy = new YButtonGroup();
	private YButton buttonSearch = new YButton("Cerca");
	private YCheckBox checkValoriSimili = new YCheckBox("Includi valori simili");
	private YTextField fieldSCognome = new YTextField();
	private YTextField fieldSNome = new YTextField();
	private YRadioButton radioSNomeCognome = new YRadioButton("Nome e cognome:");
	private YFormattedTextField fieldSNCliente = new YFormattedTextField(new YIntegerFormatter());
	private YRadioButton radioSNConto = new YRadioButton("Codice cliente:");
	private JPanel ricercaPanel;
	private JScrollPane jScrollPane1 = new JScrollPane(tableRisultati);
	
	/**
	 * Inizializza la vista
	 */
	public ImpiegatoRicercaPersonaView() {
		initComponents();
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}

	private void initGUI() {
		{
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(543, 257));
			{
				ricercaPanel = new JPanel();
				GroupLayout ricercaPanelLayout = new GroupLayout((JComponent)ricercaPanel);
				ricercaPanel.setLayout(ricercaPanelLayout);
				ricercaPanel.setBorder(BorderFactory.createTitledBorder("Ricerca Correntista"));
				ricercaPanelLayout.setHorizontalGroup(ricercaPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(ricercaPanelLayout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.LEADING, ricercaPanelLayout.createSequentialGroup()
										.addComponent(checkValoriSimili, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
										.addGap(0, 238, Short.MAX_VALUE)
										.addComponent(buttonSearch, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
										.addGroup(ricercaPanelLayout.createSequentialGroup()
												.addGroup(ricercaPanelLayout.createParallelGroup()
														.addComponent(radioSNomeCognome, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
														.addGroup(GroupLayout.Alignment.LEADING, ricercaPanelLayout.createSequentialGroup()
																.addComponent(radioSNConto, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
																.addGap(23)))
																.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(ricercaPanelLayout.createParallelGroup()
																		.addGroup(GroupLayout.Alignment.LEADING, ricercaPanelLayout.createSequentialGroup()
																				.addComponent(fieldSNome, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
																				.addGap(19)
																				.addComponent(fieldSCognome, 0, 212, Short.MAX_VALUE))
																				.addComponent(fieldSNCliente, GroupLayout.Alignment.LEADING, 0, 390, Short.MAX_VALUE)))
																				.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 542, Short.MAX_VALUE))
																				.addContainerGap());
				ricercaPanelLayout.setVerticalGroup(ricercaPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(ricercaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(fieldSNCliente, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioSNConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(ricercaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(fieldSCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(fieldSNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(radioSNomeCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(ricercaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(buttonSearch, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(checkValoriSimili, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jScrollPane1, 0, 82, Short.MAX_VALUE)
												.addContainerGap(16, 16));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(ricercaPanel, 0, 257, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addComponent(ricercaPanel, 0, 611, Short.MAX_VALUE));
		}
	}

	private void setMVCNames() {
		this.setMvcName("searchPanel");
		fieldSNCliente.setMvcName("searchIdPersona");
		fieldSNome.setMvcName("searchNome");
		fieldSCognome.setMvcName("searchCognome");
		buttonSearch.setMvcName("buttonSearch");
		groupSearchBy.setMvcName("searchBy");
		tableRisultati.setMvcName("persone");
		checkValoriSimili.setMvcName("searchAncheSimili");
	}

	private void initComponents() {
		radioSNConto.setSelectionId(0);
		radioSNomeCognome.setSelectionId(1);
		groupSearchBy.add(radioSNConto);
		groupSearchBy.add(radioSNomeCognome);
		enableSearchField(0);
	}

	/**
	 * Imposta lo stato di attivazioni dei campi di ricerca
	 * @param searchBy 0 attiva i campi di ricerca per ID
	 * tutti gli altri valori attivano i campi di ricerca per nome
	 * e cognome
	 */
	public void enableSearchField(int searchBy) {
		if ( searchBy == 0 ) {
			fieldSNome.setEnabled(false);
			fieldSCognome.setEnabled(false);
			checkValoriSimili.setEnabled(false);
			fieldSNCliente.setEnabled(true);
		} else {
			fieldSNome.setEnabled(true);
			fieldSCognome.setEnabled(true);
			checkValoriSimili.setEnabled(true);
			fieldSNCliente.setEnabled(false);
		}
	}
		
}
