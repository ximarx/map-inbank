package gccb.impiegato.gui;

import fi.mmm.yhteinen.swing.core.YIComponent;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * Vista per la lista dei movimenti associati al conto
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoListaMovimentiView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2743667989266612527L;
	private YColumn[] movimentiTableColumn = {new YColumn("idTransazione", "ID", 10),
			new YColumn("data", "Data"),
			new YColumn("tipoTransazione", "Tipo operazione"),
			new YColumn("importo", "Importo", 20)
			};
	private YTable movimentiTable = new YTable(movimentiTableColumn);
	
	private JScrollPane jScrollPane1;
	
	/**
	 * Inizializza la vista
	 */
	public ImpiegatoListaMovimentiView() {
		initGUI();
		setMVCNames();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		movimentiTable.getYProperty().put(YIComponent.MVC_NAME,"transazioni");
	}

	private void initGUI() {
		try {
			{
				GroupLayout thisLayout = new GroupLayout((JComponent)this);
				this.setLayout(thisLayout);
				this.setSize(446, 278);
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
		YUIToolkit.guessViewComponents(this);
	}

	/**
	 * Ritorna la tabella dei movimenti
	 * @return la tabella dei movimenti
	 */
	public YTable getMovimentiTable() {
		return movimentiTable;
	}

}
