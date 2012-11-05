package gccb.impiegato.gui;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

/**
 * Vista per l'aggiunta di un nuovo correntista
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoAddCorrentistaView extends YPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -868355897150560717L;
	private JPanel panelCorrentista = new JPanel();
	private YLabel labelNome = new YLabel("Nome:");
	private YTextField fieldCognome = new YTextField();
	private YTextField fieldIndirizzo = new YTextField();
	private YLabel labelIndirizzo = new YLabel("Indirizzo:");
	private YLabel labelCognome = new YLabel("Cognome:");
	private YTextField fieldNome = new YTextField();

	/**
	 * Inizializza la vista disponendo gli elementi
	 * e settando i nomi MVC
	 */
	public ImpiegatoAddCorrentistaView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void initGUI() {
		{
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(598, 162));
			{
				GroupLayout panelCorrentistaLayout = new GroupLayout((JComponent)panelCorrentista);
				panelCorrentista.setLayout(panelCorrentistaLayout);
				panelCorrentista.setBorder(BorderFactory.createTitledBorder(null, "Dati nuovo Correntista", TitledBorder.LEADING, TitledBorder.TOP));
				panelCorrentistaLayout.setHorizontalGroup(panelCorrentistaLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(panelCorrentistaLayout.createParallelGroup()
					    .addComponent(labelNome, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelCognome, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelIndirizzo, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(panelCorrentistaLayout.createParallelGroup()
					    .addComponent(fieldNome, GroupLayout.Alignment.LEADING, 0, 432, Short.MAX_VALUE)
					    .addComponent(fieldCognome, GroupLayout.Alignment.LEADING, 0, 432, Short.MAX_VALUE)
					    .addComponent(fieldIndirizzo, GroupLayout.Alignment.LEADING, 0, 432, Short.MAX_VALUE))
					.addContainerGap());
				panelCorrentistaLayout.setVerticalGroup(panelCorrentistaLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(panelCorrentistaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(panelCorrentistaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(panelCorrentistaLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(112, 112));
			}
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(panelCorrentista, 0, 574, Short.MAX_VALUE)
				.addContainerGap());
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(panelCorrentista, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, GroupLayout.PREFERRED_SIZE));
		}

	}
	
	private void setMVCNames() {
		fieldCognome.setMvcName("newCognome");
		fieldIndirizzo.setMvcName("newIndirizzo");
		fieldNome.setMvcName("newNome");
	}
	
}
