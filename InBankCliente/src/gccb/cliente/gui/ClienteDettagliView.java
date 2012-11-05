package gccb.cliente.gui;

import fi.mmm.yhteinen.swing.core.YComponent;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Pannello dettagli conto
 * @author Francesco Capozzo
 *
 */
public class ClienteDettagliView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6740501621691138867L;
	private YLabel labelNumeroConto = new YLabel("Numero conto:");
	private YTextField fieldNumeroConto = new YTextField();
	private YTextField fieldTipo = new YTextField();
	private YLabel labelFido = new YLabel("Fido:");
	private YTextField fieldUltimoMovimento = new YTextField();
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

	/**
	 * Inizializza il pannello
	 */
	public ClienteDettagliView() {
		initGUI();
		setMVCNames();
		//YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		getYProperty().put(YComponent.MVC_NAME, "clienteDettagliView");
		fieldNumeroConto.getYProperty().put(YComponent.MVC_NAME, "conto.idConto");
		fieldTipo.getYProperty().put(YComponent.MVC_NAME, "tipoContoCorrente");
		fieldUltimoMovimento.getYProperty().put(YComponent.MVC_NAME, "conto.ultimoMovimento");
		fieldIndirizzo.getYProperty().put(YComponent.MVC_NAME, "conto.intestatario.indirizzo");
		fieldCognome.getYProperty().put(YComponent.MVC_NAME, "conto.intestatario.cognome");
		fieldNome.getYProperty().put(YComponent.MVC_NAME, "conto.intestatario.nome");
		fieldFido.getYProperty().put(YComponent.MVC_NAME, "conto.fido");
		fieldImporto.getYProperty().put(YComponent.MVC_NAME, "conto.saldo");
	}

	private void initGUI() {
		{
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(349, 336));
			this.setOpaque(false);
			{
				jPanel2 = new JPanel();
				GroupLayout jPanel2Layout = new GroupLayout((JComponent)jPanel2);
				jPanel2.setLayout(jPanel2Layout);
				jPanel2.setBorder(BorderFactory.createTitledBorder("Dettagli intestatario"));
				jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
					        .addComponent(labelCognome, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					        .addGap(17))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
					        .addComponent(labelNome, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					        .addGap(33))
					    .addComponent(labelIndirizzo, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addComponent(fieldCognome, GroupLayout.Alignment.LEADING, 0, 197, Short.MAX_VALUE)
					    .addComponent(fieldIndirizzo, GroupLayout.Alignment.LEADING, 0, 197, Short.MAX_VALUE)
					    .addComponent(fieldNome, GroupLayout.Alignment.LEADING, 0, 197, Short.MAX_VALUE))
					.addContainerGap());
				jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldCognome, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldIndirizzo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(23, 23));
				jPanel2Layout.linkSize(SwingConstants.VERTICAL, new Component[] {fieldNome, fieldCognome, fieldIndirizzo});
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
					        .addComponent(labelMovimento, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
					        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					        .addComponent(fieldUltimoMovimento, 0, 100, Short.MAX_VALUE))
					    .addGroup(jPanel1Layout.createSequentialGroup()
					        .addGroup(jPanel1Layout.createParallelGroup()
					            .addComponent(labelImporto, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					                .addComponent(labelTipo, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
					            .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					                .addComponent(labelFido, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
					        .addGap(7)
					        .addGroup(jPanel1Layout.createParallelGroup()
					            .addComponent(fieldTipo, GroupLayout.Alignment.LEADING, 0, 250, Short.MAX_VALUE)
					            .addComponent(fieldFido, GroupLayout.Alignment.LEADING, 0, 250, Short.MAX_VALUE)
					            .addComponent(fieldImporto, GroupLayout.Alignment.LEADING, 0, 250, Short.MAX_VALUE)))
					    .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
					        .addComponent(labelNumeroConto, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					        .addComponent(fieldNumeroConto, 0, 207, Short.MAX_VALUE)))
					.addContainerGap());
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNumeroConto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNumeroConto, GroupLayout.Alignment.BASELINE, 0, 23, Short.MAX_VALUE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelTipo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldTipo, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelImporto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldImporto, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelMovimento, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldUltimoMovimento, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap());
				jPanel1Layout.linkSize(SwingConstants.VERTICAL, new Component[] {fieldNumeroConto, fieldTipo, fieldFido, fieldImporto, fieldUltimoMovimento});
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addComponent(jPanel1, 0, 202, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE));
			thisLayout.setHorizontalGroup(thisLayout.createParallelGroup()
				.addComponent(jPanel1, GroupLayout.Alignment.LEADING, 0, 349, Short.MAX_VALUE)
				.addComponent(jPanel2, GroupLayout.Alignment.LEADING, 0, 349, Short.MAX_VALUE));
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

	/**
	 * Il campo numero conto
	 * @return il riferimento al campo
	 */
	public YTextField getFieldNumeroConto() {
		return fieldNumeroConto;
	}

	/**
	 * Il campo tipo conto
	 * @return il riferimento al campo
	 */
	public YTextField getFieldTipo() {
		return fieldTipo;
	}

	/**
	 * Il campo ultimo movimento
	 * @return il riferimento al campo
	 */
	public YTextField getFieldUltimoMovimento() {
		return fieldUltimoMovimento;
	}

	/**
	 * Il campo indirizzo
	 * @return il riferimento al campo
	 */
	public YTextField getFieldIndirizzo() {
		return fieldIndirizzo;
	}

	/**
	 * Il campo cognome
	 * @return il riferimento al campo
	 */
	public YTextField getFieldCognome() {
		return fieldCognome;
	}

	/**
	 * Il campo nome
	 * @return il riferimento al campo
	 */
	public YTextField getFieldNome() {
		return fieldNome;
	}

	/**
	 * Il campo fido
	 * @return il riferimento al campo
	 */
	public YTextField getFieldFido() {
		return fieldFido;
	}

	/**
	 * Il campo saldo
	 * @return il riferimento al campo
	 */
	public YTextField getFieldImporto() {
		return fieldImporto;
	}
}
