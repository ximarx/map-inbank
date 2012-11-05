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

/**
 * Vista delle statistiche
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoStatisticheView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5258159675985038201L;
	private JPanel jPanel1;
	private YTextField fieldPCFido = new YTextField();
	private YLabel labelServerAddress = new YLabel("Indirizzo server:");
	private JPanel jPanel2;
	private YTextField fieldNImpiegati = new YTextField();
	private YLabel labelNImpiegati = new YLabel("Numero impiegati:");
	private YTextField fieldNBancomat = new YTextField();
	private YTextField fieldNCorrentisti = new YTextField();
	private YLabel labelNCorrentisti = new YLabel("Numero correntisti:");
	private YTextField fieldServiceName = new YTextField();
	private YLabel labelServiceName = new YLabel("Nome servizio:");
	private YTextField fieldServerPort = new YTextField();
	private YLabel labelServerPort = new YLabel("Porta server:");
	private YTextField fieldIndirizzoServer = new YTextField();
	private YLabel labelNBancomat = new YLabel("Numero bancomat:");
	private YLabel labelPercentuale = new YLabel("Percentuale conti con fido:");
	private YTextField fieldNCFido = new YTextField();
	private YLabel labelNCFido = new YLabel("Numero conti con fido:");
	private YTextField fieldNConti = new YTextField();
	private YLabel labelNConti = new YLabel("Numero conti totale:");

	/**
	 * Inizializza la vista
	 */
	public ImpiegatoStatisticheView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		fieldIndirizzoServer.setMvcName("serverAddress");
		fieldServerPort.setMvcName("serverPort");
		fieldServiceName.setMvcName("serviceName");
		fieldNConti.setMvcName("numeroConti");
		fieldNCFido.setMvcName("numeroFidi");
		fieldPCFido.setMvcName("percentualeFidi");
		fieldNBancomat.setMvcName("numeroBancomat");
		fieldNImpiegati.setMvcName("numeroImpiegati");
		fieldNCorrentisti.setMvcName("numeroClienti");
	}
	
	private void initGUI() {
		
		fieldIndirizzoServer.setEditable(false);
		fieldServerPort.setEditable(false);
		fieldServiceName.setEditable(false);
		fieldNConti.setEditable(false);
		fieldNCFido.setEditable(false);
		fieldPCFido.setEditable(false);
		fieldNBancomat.setEditable(false);
		fieldNImpiegati.setEditable(false);
		fieldNCorrentisti.setEditable(false);
		
		{
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setSize(600, 500);
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setBorder(BorderFactory.createTitledBorder("Statistiche"));
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(55, 55)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(labelNCorrentisti, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelNCFido, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelPercentuale, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelNBancomat, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelNImpiegati, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelNConti, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
					.addGap(54)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(fieldNConti, GroupLayout.Alignment.LEADING, 0, 184, Short.MAX_VALUE)
					    .addComponent(fieldNCFido, GroupLayout.Alignment.LEADING, 0, 184, Short.MAX_VALUE)
					    .addComponent(fieldPCFido, GroupLayout.Alignment.LEADING, 0, 184, Short.MAX_VALUE)
					    .addComponent(fieldNBancomat, GroupLayout.Alignment.LEADING, 0, 184, Short.MAX_VALUE)
					    .addComponent(fieldNImpiegati, GroupLayout.Alignment.LEADING, 0, 184, Short.MAX_VALUE)
					    .addComponent(fieldNCorrentisti, GroupLayout.Alignment.LEADING, 0, 184, Short.MAX_VALUE))
					.addContainerGap(61, 61));
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(fieldNConti, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelNConti, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNCFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNCFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelPercentuale, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldPCFido, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNBancomat, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNBancomat, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelNImpiegati, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldNImpiegati, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(fieldNCorrentisti, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelNCorrentisti, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(17, 17));
			}
			{
				jPanel2 = new JPanel();
				GroupLayout jPanel2Layout = new GroupLayout((JComponent)jPanel2);
				jPanel2.setLayout(jPanel2Layout);
				jPanel2.setBorder(BorderFactory.createTitledBorder("Preferenze"));
				jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap(54, 54)
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addComponent(labelServiceName, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelServerPort, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					    .addComponent(labelServerAddress, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(jPanel2Layout.createParallelGroup()
					    .addComponent(fieldIndirizzoServer, GroupLayout.Alignment.LEADING, 0, 270, Short.MAX_VALUE)
					    .addComponent(fieldServerPort, GroupLayout.Alignment.LEADING, 0, 270, Short.MAX_VALUE)
					    .addComponent(fieldServiceName, GroupLayout.Alignment.LEADING, 0, 270, Short.MAX_VALUE))
					.addContainerGap(65, 65));
				jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
					.addContainerGap(18, 18)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelServerAddress, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldIndirizzoServer, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelServerPort, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldServerPort, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(labelServiceName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					    .addComponent(fieldServiceName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(50, 50));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(22, 22)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
				.addGap(20)
				.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(59, 59));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(20, 20)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(jPanel1, GroupLayout.Alignment.LEADING, 0, 560, Short.MAX_VALUE)
				    .addComponent(jPanel2, GroupLayout.Alignment.LEADING, 0, 560, Short.MAX_VALUE))
				.addContainerGap(20, 20));
		}

	}
}