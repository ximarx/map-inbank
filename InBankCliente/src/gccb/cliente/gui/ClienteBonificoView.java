package gccb.cliente.gui;
import fi.mmm.yhteinen.swing.core.component.YButton;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.field.YFloatFormatter;
import fi.mmm.yhteinen.swing.core.component.field.YFormattedTextField;
import fi.mmm.yhteinen.swing.core.component.field.YIntegerFormatter;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Pannello bonifico. Permette di inserire le informazioni relative
 * ad un bonifico e di eseguire il bonifico
 * @author Francesco Capozzo
 *
 */
public class ClienteBonificoView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4220794520454613663L;
	private YLabel labelConto = new YLabel("Conto di destinazione:");
	private YFormattedTextField fieldConto = new YFormattedTextField(new YIntegerFormatter());
	private JPanel jPanel1 = new JPanel();
	private YButton buttonAccept = new YButton("Esegui");
	private YFormattedTextField fieldImporto = new YFormattedTextField(new YFloatFormatter());
	private YLabel labelImporto = new YLabel("Importo da trasferire:");

	/**
	 * Inizializza il pannello
	 */
	public ClienteBonificoView() {
		setMVCNames();
		initGUI();
		//YUIToolkit.guessViewComponents(this);
	}

	private void setMVCNames() {
		fieldConto.setMvcName("contoDestinazione");
		fieldImporto.setMvcName("importo");
		buttonAccept.setMvcName("buttonAccept");
	}
	
	private void initGUI() {
		{
			this.setSize(446, 268);
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(446, 268));
			{
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(22, 22)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(fieldConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(31)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelImporto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldImporto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, 24));
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(labelConto, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelImporto, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(fieldConto, GroupLayout.Alignment.LEADING, 0, 177, Short.MAX_VALUE)
					    .addComponent(fieldImporto, GroupLayout.Alignment.LEADING, 0, 177, Short.MAX_VALUE))
					.addContainerGap(22, 22));
				jPanel1Layout.linkSize(SwingConstants.VERTICAL, new Component[] {fieldConto, fieldImporto});
				jPanel1.setBorder(BorderFactory.createTitledBorder("Dettagli bonifico"));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(22, 22)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(jPanel1, GroupLayout.Alignment.LEADING, 0, 396, Short.MAX_VALUE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(232, 232, GroupLayout.PREFERRED_SIZE)
				        .addComponent(buttonAccept, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
				        .addGap(49)))
				.addContainerGap(28, 28));
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
					.addContainerGap(38, 38)
					.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(buttonAccept, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(53, 53));
			}
		}
	}

	/**
	 * Ritorna il riferimento al campo conto di destinazione
	 * @return un campo testo
	 */
	public YFormattedTextField getFieldConto() {
		return fieldConto;
	}

	/**
	 * Ritorna il riferimento al pulsante di esecuzione del bonifico
	 * @return il pulsante
	 */
	public YButton getButtonAccept() {
		return buttonAccept;
	}

	/**
	 * Ritorna il riferimento al campo importo
	 * @return il campo importo
	 */
	public YFormattedTextField getFieldImporto() {
		return fieldImporto;
	}
}
