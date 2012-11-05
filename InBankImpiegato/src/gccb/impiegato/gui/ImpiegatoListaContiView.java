package gccb.impiegato.gui;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import fi.mmm.yhteinen.swing.core.YIComponent;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.table.YColumn;
import fi.mmm.yhteinen.swing.core.component.table.YTable;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

/**
 * Vista per la visualizzazione della lista dei conti
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoListaContiView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2737240399717598311L;
	private YColumn[] contiTableColumn = {new YColumn("idConto", "Numero conto", 10),
			new YColumn("saldo", "Saldo Attuale"),
			new YColumn("ultimoMovimento", "Ultimo Movimento", 20),
			new YColumn("fido", "Fido")
			};
	private YTable contiTable = new YTable(contiTableColumn);
	
	private JScrollPane jScrollPane1;
	
	/**
	 * Inizializza la vista 
	 */
	public ImpiegatoListaContiView() {
		initGUI();
		setMVCNames();
		YUIToolkit.guessViewComponents(this);
	}
	
	private void setMVCNames() {
		contiTable.getYProperty().put(YIComponent.MVC_NAME,"searchConti");
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
						jScrollPane1.setViewportView(contiTable);
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
	
}
