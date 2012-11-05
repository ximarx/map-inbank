package gccb.server.gui;
import fi.mmm.yhteinen.swing.core.component.YButton;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import fi.mmm.yhteinen.swing.core.component.table.YTableFormatter;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import gccb.net.rmi.RMIClientType;
import gccb.server.model.ServerService;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;

/**
 * Vista per il widget della gestione servizi
 * @author Francesco Capozzo
 *
 */
public class ServerServiziWidgetView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540669283155103686L;

	private YTableFormatter tableCellFormatter = new YTableFormatter() {
		@Override
		public String format(Object arg0, int arg1, int arg2) {
			String finalString = "";
			switch (arg2) {
				case 0:
					String originalString = (String) arg0;
					finalString = originalString.substring(originalString.lastIndexOf(".") + 1);
					break;
				case 1:
					finalString = ((ServerService.Stati) arg0) == ServerService.Stati.ATTIVO ? "Attivo" : "Non Attivo";
					break;
				case 2:
					
					RMIClientType clientTipo = (RMIClientType) arg0;
					if ( clientTipo == RMIClientType.UTENTE ) {
						finalString = "Cliente";
					} else if ( clientTipo == RMIClientType.IMPIEGATO ) {
						finalString = "Impiegato";
					} else if ( clientTipo == RMIClientType.BANCOMAT ) {
						finalString = "Bancomat";
					} else {
						finalString = "N/D";
					}
					
					break;
				default:
					finalString = "Caso strano";
					break;
			}
			return finalString;
		}
	};
	
	private YColumn[] tableServiziColumn = new YColumn[] {
		new YColumn("nome", "Nome servizio", 80, false, tableCellFormatter),
		new YColumn("stato", "Stato", 10, false, tableCellFormatter),
		new YColumn("tipo", "Modalita'",10, false, tableCellFormatter),
	};
	private YTable tableServizi = new YTable(tableServiziColumn);
	private JScrollPane jScrollPane1 = new JScrollPane(tableServizi);
	private YButton buttonBancomat = new YButton("Bancomat");
	private JPanel panelDisattiva = new JPanel();
	private YButton buttonImpiegato = new YButton("Impiegato");
	private YButton buttonCliente = new YButton("Cliente");
	private YButton buttonDisattiva = new YButton("Disattiva");
	private JPanel pannelloAzioni = new JPanel();

	/**
	 * Inizializza il pannello impostando di default i pulsanti come disattivati
	 */
	public ServerServiziWidgetView() {
		setMVCNames();
		setStatoPulsanti(false, false);
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}

	private void setMVCNames() {
		tableServizi.setMvcName("servizi");
		buttonDisattiva.setMvcName("buttonDisattiva");
		buttonBancomat.setMvcName("buttonAttivaBancomat");
		buttonCliente.setMvcName("buttonAttivaCliente");
		buttonImpiegato.setMvcName("buttonAttivaImpiegato");
	}
	
	/**
	 * Setta lo stato dei pulsanti di attivazione e disattivazione
	 * @param attiva stato dei pulsanti di modalità
	 * @param disattiva stato del pulsante di disattivazione
	 */
	public void setStatoPulsanti(boolean attiva, boolean disattiva) {
		buttonBancomat.setEnabled(attiva);
		buttonCliente.setEnabled(attiva);
		buttonImpiegato.setEnabled(attiva);
		buttonDisattiva.setEnabled(disattiva);
	}
	
	private void initGUI() {
		{
			GroupLayout panelDisattivaLayout = new GroupLayout((JComponent)panelDisattiva);
			panelDisattiva.setLayout(panelDisattivaLayout);
			panelDisattivaLayout.setVerticalGroup(panelDisattivaLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(buttonDisattiva, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(18, 18));
			panelDisattivaLayout.setHorizontalGroup(panelDisattivaLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(buttonDisattiva, 0, 106, Short.MAX_VALUE)
				.addContainerGap());
			panelDisattiva.setBorder(BorderFactory.createTitledBorder("Disattiva"));

			this.setPreferredSize(new java.awt.Dimension(439, 268));
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setBorder(BorderFactory.createTitledBorder("Pannello gestione Servizi"));
			
			GroupLayout pannelloAzioniLayout = new GroupLayout((JComponent)pannelloAzioni);
			pannelloAzioni.setLayout(pannelloAzioniLayout);
			pannelloAzioniLayout.setVerticalGroup(pannelloAzioniLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(buttonBancomat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(buttonCliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(buttonImpiegato, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(16, 16));
			pannelloAzioniLayout.setHorizontalGroup(pannelloAzioniLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(pannelloAzioniLayout.createParallelGroup()
				    .addComponent(buttonBancomat, GroupLayout.Alignment.LEADING, 0, 82, Short.MAX_VALUE)
				    .addComponent(buttonCliente, GroupLayout.Alignment.LEADING, 0, 82, Short.MAX_VALUE)
				    .addComponent(buttonImpiegato, GroupLayout.Alignment.LEADING, 0, 82, Short.MAX_VALUE))
				.addContainerGap());
			pannelloAzioni.setBorder(BorderFactory.createTitledBorder("Attiva"));
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(pannelloAzioni, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, GroupLayout.PREFERRED_SIZE)
				        .addComponent(panelDisattiva, 0, 65, Short.MAX_VALUE))
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 219, Short.MAX_VALUE))
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jScrollPane1, 0, 254, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(pannelloAzioni, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
				    .addComponent(panelDisattiva, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
				.addContainerGap());

		}
	}
}
