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
 * Vista per la visualizzazione dei dettagli di un conto
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoDettagliContoView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8884058994324418153L;
	private YLabel labelNumeroConto = new YLabel("Numero conto:");
	private YTextField fieldNumeroConto = new YTextField();
	private YTextField fieldTipo = new YTextField();
	private YLabel labelFido = new YLabel("Fido:");
	private YTextField fieldUltimoMovimento = new YTextField();
	private YButton buttonPrelievo = new YButton("Esegui Prelievo");
	private YButton buttonVersamento = new YButton("Esegui Versamento");
	private YButton buttonChiudi = new YButton("Chiudi Conto");
	private YButton buttonModifica = new YButton("Modifica Fido");
	private JPanel jPanel4;
	private JToolBar jToolBar2;
	private JPanel jPanel3;
	private YLabel labelMovimento = new YLabel("Ultima operazione registrata:");
	private JPanel jPanel2;
	private YTextField fieldIndirizzo = new YTextField();
	private YLabel labelIndirizzo = new YLabel("Indirizzo:");
	private JPanel jPanel1;
	private YTextField fieldCognome = new YTextField();
	private YLabel labelCognome = new YLabel("Cognome:");
	private YTextField fieldNome = new YTextField();
	private YLabel labelNome = new YLabel("Nome:");
	private YTextField fieldFido = new YTextField();
	private YTextField fieldImporto = new YTextField();
	private YLabel labelImporto = new YLabel("Saldo:");
	private YLabel labelTipo = new YLabel("Tipo:");
	private YButton buttonDettagliIntestatario = new YButton("Dettagli intestatario");

	/**
	 * Inizializza la vista impostando i MVCNames e disponendo gli elementi
	 * nei pannelli
	 */
	public ImpiegatoDettagliContoView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		getYProperty().put(YComponent.MVC_NAME, "clienteDettagliView");
		// campi dettagli
		fieldNumeroConto.setMvcName("conto.idConto");
		fieldTipo.setMvcName("contoTipo");
		fieldUltimoMovimento.setMvcName("conto.ultimoMovimento");
		fieldIndirizzo.setMvcName("conto.intestatario.indirizzo");
		fieldCognome.setMvcName("conto.intestatario.cognome");
		fieldNome.setMvcName("conto.intestatario.nome");
		fieldFido.setMvcName("contoFido");
		fieldImporto.setMvcName("conto.saldo");
		buttonChiudi.setMvcName("buttonChiudiConto");
		buttonModifica.setMvcName("buttonModificaFido");
		buttonPrelievo.setMvcName("buttonPrelievo");
		buttonVersamento.setMvcName("buttonVersamento");
		buttonDettagliIntestatario.setMvcName("buttonDettagliIntestatario");
	}
	
	/**
	 * Imposta il pannello bancomat e quello per le transazioni
	 * @param pannelloBancomat il pannello bancomat associati al conto
	 * @param pannelloTransazioni il pannello delle transazioni sul conto
	 */
	public void setWidgets(YIComponent pannelloBancomat, YIComponent pannelloTransazioni ) {
		jPanel3.removeAll();
		jPanel3.add((Component) pannelloBancomat, BorderLayout.CENTER);
		jPanel3.validate();
		jPanel3.repaint();
		
		jPanel4.removeAll();
		jPanel4.add((Component) pannelloTransazioni, BorderLayout.CENTER);
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
				jPanel2.setBorder(BorderFactory.createTitledBorder("Dettagli intestatario"));
				jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
					        .addComponent(labelNome, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					        .addGap(17))
					    .addComponent(labelCognome, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelIndirizzo, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addComponent(fieldNome, GroupLayout.Alignment.LEADING, 0, 246, Short.MAX_VALUE)
					    .addComponent(fieldCognome, GroupLayout.Alignment.LEADING, 0, 246, Short.MAX_VALUE)
					    .addComponent(fieldIndirizzo, GroupLayout.Alignment.LEADING, 0, 246, Short.MAX_VALUE))
					.addContainerGap());
				jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, Short.MAX_VALUE)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(20, 20));
			}
			{
				jPanel3 = new JPanel();
				BorderLayout jPanel3Layout = new BorderLayout();
				jPanel3.setLayout(jPanel3Layout);
			}
			{
				jPanel4 = new JPanel();
				BorderLayout jPanel4Layout = new BorderLayout();
				jPanel4.setLayout(jPanel4Layout);
			}
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setBorder(BorderFactory.createTitledBorder("Dettagli conto"));
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(labelMovimento, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, GroupLayout.PREFERRED_SIZE)
					        .addComponent(fieldUltimoMovimento, 0, 130, Short.MAX_VALUE))
					    .addGroup(jPanel1Layout.createSequentialGroup()
					        .addGroup(jPanel1Layout.createParallelGroup()
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					                .addComponent(labelImporto, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					                .addComponent(fieldImporto, 0, 103, Short.MAX_VALUE))
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					                .addComponent(labelNumeroConto, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					                .addComponent(fieldNumeroConto, 0, 53, Short.MAX_VALUE)))
					        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, GroupLayout.PREFERRED_SIZE)
					        .addGroup(jPanel1Layout.createParallelGroup()
					            .addComponent(labelTipo, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					            .addComponent(labelFido, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					        .addGroup(jPanel1Layout.createParallelGroup()
					            .addComponent(fieldTipo, GroupLayout.Alignment.LEADING, 0, 104, Short.MAX_VALUE)
					            .addComponent(fieldFido, GroupLayout.Alignment.LEADING, 0, 104, Short.MAX_VALUE))))
					.addContainerGap());
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelTipo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldTipo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNumeroConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelNumeroConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldImporto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelImporto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelMovimento, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldUltimoMovimento, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(getJToolBar2(), GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
				    .addComponent(jPanel3, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel4, 0, 213, Short.MAX_VALUE)
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(getJToolBar2(), GroupLayout.Alignment.LEADING, 0, 600, Short.MAX_VALUE)
				.addGroup(thisLayout.createSequentialGroup()
				    .addPreferredGap(getJToolBar2(), jPanel1, LayoutStyle.ComponentPlacement.INDENT)
				    .addGroup(thisLayout.createParallelGroup()
				        .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				            .addGroup(thisLayout.createParallelGroup()
				                .addComponent(jPanel1, GroupLayout.Alignment.LEADING, 0, 364, Short.MAX_VALUE)
				                .addComponent(jPanel2, GroupLayout.Alignment.LEADING, 0, 364, Short.MAX_VALUE))
				            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				            .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
				        .addComponent(jPanel4, GroupLayout.Alignment.LEADING, 0, 576, Short.MAX_VALUE))
				    .addContainerGap(12, 12)));
		}
		fieldNumeroConto.setFocusable(false);
		fieldTipo.setFocusable(false);
		fieldUltimoMovimento.setFocusable(false);
		fieldIndirizzo.setFocusable(false);
		fieldCognome.setFocusable(false);
		fieldNome.setFocusable(false);
		fieldFido.setFocusable(false);
		fieldImporto.setFocusable(false);

	}
	
	private JToolBar getJToolBar2() {
		if(jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(buttonDettagliIntestatario);
			jToolBar2.addSeparator();
			jToolBar2.add(buttonPrelievo);
			jToolBar2.add(buttonVersamento);
			jToolBar2.addSeparator();
			jToolBar2.add(getButtonModifica());
			jToolBar2.addSeparator();
			jToolBar2.add(getButtonChiudi());
		}
		return jToolBar2;
	}
	
	private YButton getButtonModifica() {
		return buttonModifica;
	}
	
	private YButton getButtonChiudi() {
		return buttonChiudi;
	}

	/**
	 * Imposta lo stato di attivazione del pulsante per la modifica del fido
	 * @param equals
	 */
	public void setFidoModificabile(boolean equals) {
		buttonModifica.setEnabled(equals);
	}

}
