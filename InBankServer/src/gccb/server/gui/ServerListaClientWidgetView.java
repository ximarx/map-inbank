package gccb.server.gui;
import fi.mmm.yhteinen.swing.core.component.YButton;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Pannello della vista della lista client connessi
 * @author Francesco Capozzo
 *
 */
public class ServerListaClientWidgetView extends YPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7671936956073381149L;
	private YColumn[] columnTableClienti = new YColumn[] {
			new YColumn("idClient", "ID Agente"),
			new YColumn("address", "Indirizzo remoto"),
			new YColumn("tipo", "Tipo Client")
	};
	private YTable tableClient = new YTable(columnTableClienti);
	private JPanel pannelloPulsanti = new JPanel();
	private YButton buttonDisconnetti = new YButton("Disconnetti Agente");
	private YButton buttonAggiorna = new YButton("Aggiorna lista");
	private JScrollPane jScrollPane1 = new JScrollPane(tableClient);


	/**
	 * Inizializza il pannello
	 */
	public ServerListaClientWidgetView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}

	/**
	 * Imposta lo stato di attivazione del pulsante disconnetti
	 * @param disconnetti il nuovo stato
	 */
	public void setStatoPulsanti(boolean disconnetti) {
		buttonDisconnetti.setEnabled(disconnetti);
	}
	
	/**
	 * Imposta i nominativi MVC
	 */
	private void setMVCNames() {
		tableClient.setMvcName("listaClient");
		buttonAggiorna.setMvcName("buttonAggiorna");
		buttonDisconnetti.setMvcName("buttonDisconnetti");
	}
	
	/**
	 * Dispone ed inizializza gli elementi grafici
	 */
	private void initGUI() {
		this.setPreferredSize(new java.awt.Dimension(517, 280));
		GroupLayout thisLayout = new GroupLayout((JComponent)this);
		this.setLayout(thisLayout);
		this.setBorder(BorderFactory.createTitledBorder("Pannello gestione Client"));
		GroupLayout pannelloPulsantiLayout = new GroupLayout((JComponent)pannelloPulsanti);
		pannelloPulsanti.setLayout(pannelloPulsantiLayout);
		pannelloPulsantiLayout.setVerticalGroup(pannelloPulsantiLayout.createSequentialGroup()
			.addGap(8)
			.addGroup(pannelloPulsantiLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    .addComponent(buttonDisconnetti, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
			    .addComponent(buttonAggiorna, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
			.addGap(7));
		pannelloPulsantiLayout.setHorizontalGroup(pannelloPulsantiLayout.createSequentialGroup()
			.addContainerGap()
			.addComponent(buttonDisconnetti, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
			.addGap(0, 127, Short.MAX_VALUE)
			.addComponent(buttonAggiorna, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
			.addContainerGap());
		thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
			.addComponent(pannelloPulsanti, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(jScrollPane1, 0, 199, Short.MAX_VALUE)
			.addGap(7));
		thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
			.addContainerGap()
			.addGroup(thisLayout.createParallelGroup()
			    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 483, Short.MAX_VALUE)
			    .addComponent(pannelloPulsanti, GroupLayout.Alignment.LEADING, 0, 483, Short.MAX_VALUE))
			.addContainerGap());
		pannelloPulsantiLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {buttonAggiorna, buttonDisconnetti});
	}
}
