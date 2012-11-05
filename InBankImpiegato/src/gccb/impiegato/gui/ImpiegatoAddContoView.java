package gccb.impiegato.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YCheckBox;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.field.YFloatFormatter;
import fi.mmm.yhteinen.swing.core.component.field.YFormattedTextField;
import fi.mmm.yhteinen.swing.core.component.field.YIntegerFormatter;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.component.list.YComboBox;
import fi.mmm.yhteinen.swing.core.component.list.YListItem;
import fi.mmm.yhteinen.swing.core.component.text.YPasswordField;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import gccb.Data;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Pannello per l'aggiunta di un nuovo conto
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoAddContoView extends YPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3309839684183604232L;
	private JPanel panelCorrentista = new JPanel();
	private JToolBar jToolBar1 = new JToolBar();
	private YButton buttonCercaIntestatario = new YButton("Cerca");
	private YButton buttonAggiungiCorrentista = new YButton("Aggiungi nuovo correntista");
	private YPasswordField fieldPassword = new YPasswordField();
	private YLabel labelPassword = new YLabel("Password:");
	private YButton buttonAnnulla = new YButton("Annulla");
	private JPanel jPanel3 = new JPanel();
	private YButton buttonConferma = new YButton("Conferma");
	private JPanel jPanel2 = new JPanel();
	private YComboBox comboTipoConto = new YComboBox(true);
	{
		comboTipoConto.setComboModel(Arrays.asList(new String[]{"NORMALE", "CON FIDO"}));
	}
	private JPanel jPanel1 = new JPanel();
	private YFormattedTextField fieldAnno = new YFormattedTextField(new YIntegerFormatter());
	{
		fieldAnno.setMaxLength(4);
	}
	private YComboBox comboMese = new YComboBox(true);
	{
		comboMese.setComboModel(Arrays.asList(Data.MESI));
	}
	private YFormattedTextField fieldGiorno = new YFormattedTextField(new YIntegerFormatter());
	{
		fieldGiorno.setMaxLength(2);
	}
	private YLabel labelData = new YLabel("Data alternativa:");
	private YCheckBox checkDataAttuale = new YCheckBox("Registra con data odierna");
	private YFormattedTextField fieldFido = new YFormattedTextField(new YFloatFormatter());
	private YLabel labelFido = new YLabel("Fido:");
	private YFormattedTextField fieldSaldo = new YFormattedTextField(new YFloatFormatter());
	private YLabel labelTipoConto = new YLabel("Tipo conto:");
	private YLabel labelSaldo = new YLabel("Saldo:");
	private YLabel labelIntestatario = new YLabel("Intestatario:");
	private YTextField fieldIntestatario = new YTextField();
	private JPanel panelConto = new JPanel();

	/**
	 * Inizializza la vista impostando i nomi MVC
	 * e disponendo gli elementi nel pannello
	 */
	public ImpiegatoAddContoView() {
		setMVCNames();
		initComponents();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		buttonAggiungiCorrentista.setMvcName("buttonAggiungiCorrentista");
		buttonCercaIntestatario.setMvcName("buttonCercaIntestatario");
		buttonConferma.setMvcName("buttonConferma");
		buttonAnnulla.setMvcName("buttonAnnulla");
		comboTipoConto.setMvcName("newContoTipo");
		fieldGiorno.setMvcName("newGiorno");
		comboMese.setMvcName("newMese");
		fieldAnno.setMvcName("newAnno");
		checkDataAttuale.setMvcName("newDataAttuale");
		fieldSaldo.setMvcName("newSaldo");
		fieldFido.setMvcName("newFido");
		fieldIntestatario.setMvcName("newIntestatario");
		fieldPassword.setMvcName("newPassword");
	}
	
	private void initComponents() {
		checkDataAttuale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				boolean newState = checkDataAttuale.isSelected();
				comboMese.setEnabled(!newState);
				fieldAnno.setEnabled(!newState);
				fieldGiorno.setEnabled(!newState);
			}
		});
		
		comboTipoConto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					YListItem newValue = (YListItem) comboTipoConto.getSelectedItem();
					fieldFido.setEnabled(!newValue.toString().equals("NORMALE"));
				} catch ( NullPointerException npe) {
					fieldFido.setEnabled(false);
				}
			}
		});
		jToolBar1.add(buttonAggiungiCorrentista);
	}
	
	private void initGUI() {
		{
			{
			GroupLayout jPanel3Layout = new GroupLayout((JComponent)jPanel3);
			jPanel3.setLayout(jPanel3Layout);
			jPanel3Layout.setVerticalGroup(jPanel3Layout.createSequentialGroup()
				.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(labelSaldo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(fieldSaldo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(19)
				.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(labelTipoConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(comboTipoConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addGap(23)
				.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(labelFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(fieldFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(24)
				.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(labelPassword, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				    .addComponent(fieldPassword, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
				.addContainerGap());
			jPanel3Layout.setHorizontalGroup(jPanel3Layout.createSequentialGroup()
				.addGroup(jPanel3Layout.createParallelGroup()
				    .addComponent(labelSaldo, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelTipoConto, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelFido, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelPassword, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
				.addGap(7)
				.addGroup(jPanel3Layout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
				        .addComponent(comboTipoConto, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
				        .addGap(292))
				    .addComponent(fieldSaldo, GroupLayout.Alignment.LEADING, 0, 424, Short.MAX_VALUE)
				    .addComponent(fieldFido, GroupLayout.Alignment.LEADING, 0, 424, Short.MAX_VALUE)
				    .addGroup(GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
				        .addComponent(fieldPassword, 0, 424, Short.MAX_VALUE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))));
			}
			{
				GroupLayout jPanel2Layout = new GroupLayout((JComponent)jPanel2);
				jPanel2.setLayout(jPanel2Layout);
				jPanel2.setBorder(BorderFactory.createTitledBorder("Data apertura conto"));
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(checkDataAttuale, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addComponent(labelData, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					    .addComponent(jPanel1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(23, 23));
				jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
					        .addComponent(labelData, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					        .addGap(45)
					        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
					        .addComponent(checkDataAttuale, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
					        .addGap(234)))
					.addContainerGap(30, 30));
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup()
					.addComponent(fieldGiorno, GroupLayout.Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(jPanel1Layout.createSequentialGroup()
					    .addComponent(comboMese, 0, 22, Short.MAX_VALUE)
					    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
					.addGroup(jPanel1Layout.createSequentialGroup()
					    .addComponent(fieldAnno, 0, 23, Short.MAX_VALUE)
					    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)));
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addComponent(fieldGiorno, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(comboMese, 0, 132, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(fieldAnno, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE));

			}
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(591, 510));
			{
				GroupLayout panelCorrentistaLayout = new GroupLayout((JComponent)panelCorrentista);
				panelCorrentista.setLayout(panelCorrentistaLayout);
				panelCorrentista.setBorder(BorderFactory.createTitledBorder("Intestatario"));
				panelCorrentistaLayout.setHorizontalGroup(panelCorrentistaLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelIntestatario, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(fieldIntestatario, 0, 310, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(buttonCercaIntestatario, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addContainerGap());
				panelCorrentistaLayout.setVerticalGroup(panelCorrentistaLayout.createSequentialGroup()
					.addGroup(panelCorrentistaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelIntestatario, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldIntestatario, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(buttonCercaIntestatario, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap());
			}
			{
				GroupLayout panelContoLayout = new GroupLayout((JComponent)panelConto);
				panelConto.setLayout(panelContoLayout);
				panelContoLayout.setVerticalGroup(panelContoLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(8));
				panelContoLayout.setHorizontalGroup(panelContoLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(panelContoLayout.createParallelGroup()
					    .addComponent(jPanel2, GroupLayout.Alignment.LEADING, 0, 533, Short.MAX_VALUE)
					    .addComponent(jPanel3, GroupLayout.Alignment.LEADING, 0, 533, Short.MAX_VALUE))
					.addContainerGap());
				panelConto.setBorder(BorderFactory.createTitledBorder("Dettagli nuovo conto"));
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(panelCorrentista, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(panelConto, 0, 333, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, GroupLayout.PREFERRED_SIZE)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(buttonAnnulla, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(buttonConferma, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(jToolBar1, GroupLayout.Alignment.LEADING, 0, 591, Short.MAX_VALUE)
				.addGroup(thisLayout.createSequentialGroup()
				    .addPreferredGap(jToolBar1, panelCorrentista, LayoutStyle.ComponentPlacement.INDENT)
				    .addGroup(thisLayout.createParallelGroup()
				        .addComponent(panelCorrentista, GroupLayout.Alignment.LEADING, 0, 567, Short.MAX_VALUE)
				        .addComponent(panelConto, GroupLayout.Alignment.LEADING, 0, 567, Short.MAX_VALUE)
				        .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				            .addGap(80)
				            .addComponent(buttonAnnulla, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
				            .addGap(0, 163, Short.MAX_VALUE)
				            .addComponent(buttonConferma, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
				            .addGap(82)))
				    .addContainerGap(12, 12)));
			}
		}

	}

}
