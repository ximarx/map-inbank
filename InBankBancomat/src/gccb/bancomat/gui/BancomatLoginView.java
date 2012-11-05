package gccb.bancomat.gui;


import fi.mmm.yhteinen.swing.core.YComponent;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.component.text.YPasswordField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import gccb.exception.handler.ExceptionHandlerManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Pannello login. Permette di inserire la password
 * corrispondente alla carta bancomat inserita
 * @author Francesco Capozzo
 *
 */
public class BancomatLoginView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3464194054625729283L;
	private YPasswordField fieldPassword = new YPasswordField();
	private JPanel jPanel1;
	private YLabel labelCodice = new YLabel("Inserire il codice segreto");
	
	/**
	 * Inizializza il pannello
	 */
	public BancomatLoginView() {
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
		setMvcName("bancomatLoginView");
	}
	
	private void setMVCNames() {
		fieldPassword.setMvcName("password");
		fieldPassword.getYProperty().put(YComponent.CHECK_CHANGES, new Boolean(true));
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(640, 480));
			GroupLayout thisLayout = new GroupLayout((JComponent)this);
			this.setLayout(thisLayout);
			this.setSize(640, 480);
			{
				jPanel1 = new JPanel();
				GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Autenticazione richiesta", TitledBorder.LEADING, TitledBorder.TOP));
				
				labelCodice.setFont(new java.awt.Font("AlArabiya",3,14));
				labelCodice.setHorizontalAlignment(SwingConstants.CENTER);
				
				//fieldPassword.setKeyEventsEnabled(true);
				fieldPassword.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						super.keyPressed(e);
						passwordFieldKeyPressed(e);
					}
				});
				
				fieldPassword.requestFocusInWindow();
					
				jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(39, 39)
					.addGroup(jPanel1Layout.createParallelGroup()
					    .addComponent(fieldPassword, GroupLayout.Alignment.LEADING, 0, 230, Short.MAX_VALUE)
					    .addComponent(labelCodice, GroupLayout.Alignment.LEADING, 0, 230, Short.MAX_VALUE))
					.addContainerGap(42, 42));
				jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap(41, Short.MAX_VALUE)
					.addComponent(labelCodice, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(0, 24, GroupLayout.PREFERRED_SIZE)
					.addComponent(fieldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, 50));
				jPanel1.setFocusCycleRoot(true);
				//jPanel1.setFocusTraversalPolicy(new ArrayFocusTraversalPolicy(new java.awt.Component[] {fieldPassword}));
			}
			//this.setFocusTraversalPolicy(new ArrayFocusTraversalPolicy(new java.awt.Component[] {jPanel1}));
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(143, 143)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(155, 155));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(163, 163)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(156, 156));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void passwordFieldKeyPressed(KeyEvent evt) {
		if ( evt.getKeyCode() == KeyEvent.VK_ENTER ) {
			try {
				YUIToolkit.getController(this).copyToModel("password");
				YUIToolkit.getController(this).invokeMethodIfFound("passwordEnterPressed");
			} catch ( Exception e) {
				ExceptionHandlerManager.process(e);
			}
		}
	}
}
