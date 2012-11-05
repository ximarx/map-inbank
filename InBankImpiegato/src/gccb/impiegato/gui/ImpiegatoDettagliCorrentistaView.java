package gccb.impiegato.gui;

import fi.mmm.yhteinen.swing.core.YComponent;
import fi.mmm.yhteinen.swing.core.YIComponent;
import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import java.awt.BorderLayout;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;

/**
 * Vista per la visualizzazione dei dettagli di un correntista
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoDettagliCorrentistaView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8085114443954639011L;
	private YButton buttonPrelievo = new YButton("Apri Nuovo Conto");
	private YTextField fieldCodiceCliente = new YTextField();
	private YLabel labelCodiceCliente = new YLabel("Codice cliente:");
	private JPanel jPanel4;
	private JToolBar jToolBar2;
	private JPanel jPanel2;
	private YTextField fieldIndirizzo = new YTextField();
	private YLabel labelIndirizzo = new YLabel("Indirizzo:");
	private YTextField fieldCognome = new YTextField();
	private YLabel labelCognome = new YLabel("Cognome:");
	private YTextField fieldNome = new YTextField();
	private YLabel labelNome = new YLabel("Nome:");

	/**
	 * Inizializza la vista e dispone gli elementi
	 */
	public ImpiegatoDettagliCorrentistaView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		getYProperty().put(YComponent.MVC_NAME, "clienteDettagliView");
		// campi dettagli
		fieldCodiceCliente.setMvcName("persona.idPersona");
		fieldIndirizzo.setMvcName("persona.indirizzo");
		fieldCognome.setMvcName("persona.cognome");
		fieldNome.setMvcName("persona.nome");
		buttonPrelievo.setMvcName("buttonCreaConto");
	}
	
	/**
	 * Imposta il pannello dei conti associati al correntista
	 * @param pannelloConti il nuovo pannello conti
	 */
	public void setWidgets(YIComponent pannelloConti ) {
		jPanel4.removeAll();
		jPanel4.add((Component) pannelloConti, BorderLayout.CENTER);
		jPanel4.validate();
		jPanel4.repaint();
	}
	
	private void initGUI() {
		{
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(600, 560));
			this.setOpaque(false);
			this.setSize(600, 560);
			{
				jPanel2 = new JPanel();
				GroupLayout jPanel2Layout = new GroupLayout((JComponent)jPanel2);
				jPanel2.setLayout(jPanel2Layout);
				jPanel2.setBorder(BorderFactory.createTitledBorder("Dettagli Correntista"));
				jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addGroup(jPanel2Layout.createSequentialGroup()
					        .addGroup(jPanel2Layout.createParallelGroup()
					            .addComponent(labelIndirizzo, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
					                .addComponent(labelNome, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					                .addGap(17))
					            .addComponent(labelCognome, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
					        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					        .addGroup(jPanel2Layout.createParallelGroup()
					            .addComponent(fieldNome, GroupLayout.Alignment.LEADING, 0, 458, Short.MAX_VALUE)
					            .addComponent(fieldCognome, GroupLayout.Alignment.LEADING, 0, 458, Short.MAX_VALUE)
					            .addComponent(fieldIndirizzo, GroupLayout.Alignment.LEADING, 0, 458, Short.MAX_VALUE)))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
					        .addComponent(labelCodiceCliente, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					        .addComponent(fieldCodiceCliente, 0, 420, Short.MAX_VALUE)))
					.addContainerGap());
				jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelCodiceCliente, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldCodiceCliente, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(0, 19, Short.MAX_VALUE)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, 19));
			}
			{
				jPanel4 = new JPanel();
				BorderLayout jPanel4Layout = new BorderLayout();
				jPanel4.setLayout(jPanel4Layout);
				jPanel4.setBorder(BorderFactory.createTitledBorder("Elenco conti intestati a cliente"));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(getJToolBar2(), GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jPanel4, 0, 294, Short.MAX_VALUE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(getJToolBar2(), GroupLayout.Alignment.LEADING, 0, 600, Short.MAX_VALUE)
				.addGroup(thisLayout.createSequentialGroup()
				    .addPreferredGap(getJToolBar2(), jPanel2, LayoutStyle.ComponentPlacement.INDENT)
				    .addGroup(thisLayout.createParallelGroup()
				        .addComponent(jPanel2, GroupLayout.Alignment.LEADING, 0, 576, Short.MAX_VALUE)
				        .addComponent(jPanel4, GroupLayout.Alignment.LEADING, 0, 576, Short.MAX_VALUE))
				    .addContainerGap(12, 12)));
		}
		fieldIndirizzo.setFocusable(false);
		fieldCognome.setFocusable(false);
		fieldNome.setFocusable(false);
		fieldCodiceCliente.setFocusable(false);

	}
	
	private JToolBar getJToolBar2() {
		if(jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(buttonPrelievo);
		}
		return jToolBar2;
	}
}
