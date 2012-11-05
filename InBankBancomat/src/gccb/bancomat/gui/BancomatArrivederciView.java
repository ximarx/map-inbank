package gccb.bancomat.gui;

import fi.mmm.yhteinen.swing.core.component.YPanel;

import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Pannello dell'arrivederci.
 * Visualizza un avviso di ultimazione dell'operazione
 * @author Francesco Capozzo
 *
 */
public class BancomatArrivederciView extends YPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4849856281742730589L;
	private JLabel label1;
	private JPanel jPanel1;
	private JLabel l3;
	private JLabel l2;
	
	/**
	 * Inizializza il pannello
	 */
	public BancomatArrivederciView() {
		initGUI();
	}
	
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				{
					label1 = new JLabel();
					label1.setText("Operazione eseguita");
					label1.setHorizontalAlignment(SwingConstants.CENTER);
					label1.setFont(new java.awt.Font("AlArabiya",1,36));
				}
				{
					l2 = new JLabel();
					l2.setText("Grazie per aver utilizzato il terminale Bancomat");
					l2.setHorizontalAlignment(SwingConstants.CENTER);
					l2.setFont(new java.awt.Font("AlArabiya",0,18));
				}
				{
					l3 = new JLabel();
					l3.setText("Si ricordi di prelevare il denaro e la carta");
					l3.setHorizontalAlignment(SwingConstants.CENTER);
					l3.setFont(new java.awt.Font("AlArabiya",0,18));
				}
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup()
					.addComponent(label1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE)
					.addComponent(l2, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE)
					.addComponent(l3, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE));
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addComponent(label1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addComponent(l2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(l3, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE));
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(83, 83)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(141, 141));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(70, 70)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(74, 74));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
