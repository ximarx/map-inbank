package gccb.impiegato.gui;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.field.YFloatFormatter;
import fi.mmm.yhteinen.swing.core.component.field.YFormattedTextField;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import fi.mmm.yhteinen.swing.core.component.text.YPasswordField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import java.awt.Component;


/**
 * Vista della lista bancomat associati a conto
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoListaBancomatView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4865615400584472064L;
	private YColumn[] tableBancomatColumn = {
			new YColumn("idBancomat", "ID", 10),
			new YColumn("massimoPrelevabile", "Massimo prelevabile"),
			};
	private YTable tableBancomat = new YTable(tableBancomatColumn);
	private YButton buttonRemoveBancomat = new YButton("-");
	private YButton buttonAddBancomat = new YButton("+");
	private YPanel jPanel = new YPanel();
	private JScrollPane jScrollPane2 = new JScrollPane(tableBancomat);
	

	private YLabel labelMassimoPrelevabile = new YLabel("Massimo prelevabile:");
	private YFormattedTextField fieldMassimoPrelevabile = new YFormattedTextField(new YFloatFormatter());
	private YButton buttonConferma = new YButton("Conferma");
	private YButton buttonAnnulla = new YButton("Annulla");
	private YLabel labelCodiceBancomat = new YLabel("PIN Bancomat:");
	private YPasswordField fieldCodiceBancomat = new YPasswordField();
	
	private AddBancomatDialog addBancomatDialog = new AddBancomatDialog();
	{
		addBancomatDialog.setTitle("Dettagli nuovo Bancomat");
		addBancomatDialog.setLocationRelativeTo(this);
	}
	
	/**
	 * Inizializza la vista disponendo gli elementi nel pannello
	 */
	public ImpiegatoListaBancomatView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	private void initGUI() {
		GroupLayout thisLayout = new GroupLayout((JComponent)this);
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(211, 314));
		GroupLayout jPanel3Layout = new GroupLayout((JComponent)jPanel);
		jPanel.setLayout(jPanel3Layout);
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jScrollPane2, 0, 206, Short.MAX_VALUE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(buttonAddBancomat, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
			    .addComponent(buttonRemoveBancomat, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
			.addContainerGap());		
		jPanel.setBorder(BorderFactory.createTitledBorder("Bancomat associati a conto"));
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(jPanel, 0, 314, Short.MAX_VALUE));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addComponent(jPanel, 0, 211, Short.MAX_VALUE));
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createSequentialGroup()
			.addContainerGap()
			.addGroup(jPanel3Layout.createParallelGroup()
			    .addGroup(GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
			        .addComponent(buttonAddBancomat, 0, 63, Short.MAX_VALUE)
			        .addGap(0, 41, GroupLayout.PREFERRED_SIZE)
			        .addComponent(buttonRemoveBancomat, 0, 62, Short.MAX_VALUE))
			    .addComponent(jScrollPane2, GroupLayout.Alignment.LEADING, 0, 166, Short.MAX_VALUE))
			.addContainerGap());
		jPanel3Layout.linkSize(SwingConstants.VERTICAL, new Component[] {buttonRemoveBancomat, buttonAddBancomat});
	}
	
	private void setMVCNames() {
		tableBancomat.setMvcName("bancomats");
		buttonAddBancomat.setMvcName("buttonAddBancomat");
		buttonRemoveBancomat.setMvcName("buttonDelBancomat");
		
		buttonConferma.setMvcName("buttonConferma");
		buttonAnnulla.setMvcName("buttonAnnulla");
		fieldMassimoPrelevabile.setMvcName("newMassimo");
		fieldCodiceBancomat.setMvcName("newCodice");
	}
	
	/**
	 * Imposta lo stato di attivazione del pulsante di rimozione
	 * bancomat
	 * @param newState
	 */
	public void setButtonState(boolean newState) {
		buttonRemoveBancomat.setEnabled(newState);
	}
	
	/**
	 * Imposta lo stato della finesta di aggiunta bancomat
	 * @param newState il nuovo stato della finestra
	 */
	public void setAddButtonDialogVisible(boolean newState) {
		addBancomatDialog.setVisible(newState);
	}
	
	private class AddBancomatDialog extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8227915306385425320L;

		public AddBancomatDialog() {
			initGUI();
		}
		
		private void initGUI() {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(labelMassimoPrelevabile, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				    .addComponent(fieldMassimoPrelevabile, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(labelCodiceBancomat, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				    .addComponent(fieldCodiceBancomat, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(0, 17, Short.MAX_VALUE)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(buttonConferma, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonAnnulla, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(18, 18));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(labelCodiceBancomat, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelMassimoPrelevabile, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(18)
				        .addComponent(buttonAnnulla, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
				        .addGap(21)))
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(fieldCodiceBancomat, GroupLayout.Alignment.LEADING, 0, 167, Short.MAX_VALUE)
				    .addComponent(fieldMassimoPrelevabile, GroupLayout.Alignment.LEADING, 0, 167, Short.MAX_VALUE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(0, 41, Short.MAX_VALUE)
				        .addComponent(buttonConferma, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
				        .addGap(20)))
				.addContainerGap());
			this.setPreferredSize(new java.awt.Dimension(345, 155));
			{
				this.setSize(345, 155);
			}			
		}
	}
}
