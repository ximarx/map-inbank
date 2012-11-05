package gccb.cliente.gui;

import fi.mmm.yhteinen.swing.core.YIComponent;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import javax.swing.BorderFactory;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 * Pannello movimenti conto
 * Visualizza la lista dei movimenti registrati relativi al conto
 * @author Francesco Capozzo
 *
 */
public class ClienteMovimentiView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8601879717124978972L;
	private YColumn[] movimentiTableColumn = {new YColumn("idTransazione", "ID", 10),
			new YColumn("data", "Data"),
			new YColumn("tipoTransazione", "Tipo operazione"),
			new YColumn("importo", "Importo", 20)
			};
	private YTable movimentiTable = new YTable(movimentiTableColumn);
	
	private JScrollPane jScrollPane1;
	
	/**
	 * Inizializza il pannello
	 */
	public ClienteMovimentiView() {
		initGUI();
		setMVCNames();
		//YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		movimentiTable.getYProperty().put(YIComponent.MVC_NAME,"ultimiMovimenti");
	}

	private void initGUI() {
		try {
			{
				GroupLayout thisLayout = new GroupLayout((JComponent)this);
				this.setLayout(thisLayout);
				this.setSize(446, 278);
				this.setBorder(BorderFactory.createTitledBorder(null, "Ultimi movimenti registrati", TitledBorder.LEADING, TitledBorder.TOP));
				{
					jScrollPane1 = new JScrollPane();
					jScrollPane1.setPreferredSize(new java.awt.Dimension(3, 3));
					{
						jScrollPane1.setViewportView(movimentiTable);
					}
				}
				thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
					.addContainerGap(12, 12)
					.addComponent(jScrollPane1, 0, 254, Short.MAX_VALUE)
					.addContainerGap(12, 12));
				thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
					.addContainerGap(12, 12)
					.addComponent(jScrollPane1, 0, 422, Short.MAX_VALUE)
					.addContainerGap(12, 12));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ritorna un riferimento alla tabella dei movimenti
	 * @return la tabella movimenti
	 */
	public YTable getMovimentiTable() {
		return movimentiTable;
	}

}
