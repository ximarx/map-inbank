package gccb.cliente.gui;
import fi.mmm.yhteinen.swing.core.YComponent;
import fi.mmm.yhteinen.swing.core.component.YButton;

import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.label.YLabel;
import fi.mmm.yhteinen.swing.core.component.text.YPasswordField;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Pannello di login
 * Permette di inserire username e password ed eseguire il login
 * @author Francesco Capozzo
 *
 */
public class ClienteLoginView extends YPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1497869561437292771L;
	private YLabel labelUsername = new YLabel("Username: ");
	private YButton buttonLogin = new YButton("Login");
	private YPanel jPanel1 = new YPanel();
	private YPasswordField fieldPassword = new YPasswordField();
	private YTextField fieldUsername = new YTextField();
	private YLabel passwordLabel = new YLabel("Password: ");;
	
	/**
	 * Inizializza il pannello
	 */
	public ClienteLoginView() {
		setMVCNames();
		initGUI();
        getYProperty().put(YComponent.MVC_NAME, "clienteLoginView");
        // using automatic view components registration (via reflection): 
        //YUIToolkit.guessViewComponents(this);
	}

	private void setMVCNames() {
		buttonLogin.getYProperty().put(YComponent.MVC_NAME, "buttonLogin");
		fieldUsername.getYProperty().put(YComponent.MVC_NAME, "username");
		fieldPassword.getYProperty().put(YComponent.MVC_NAME, "password");
	}
	
	private void initGUI() {
		
		this.setSize(446, 268);
		GroupLayout thisLayout = new GroupLayout((JComponent)this);
		this.setLayout(thisLayout);
		this.setPreferredSize(new java.awt.Dimension(446, 268));
		{
			GroupLayout jPanel1Layout = new GroupLayout((JComponent)jPanel1);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap(22, 22)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(fieldUsername, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
				    .addComponent(labelUsername, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
				.addGap(31)
				.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(passwordLabel, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				    .addComponent(fieldPassword, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(24, 24));
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addComponent(labelUsername, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
				    .addComponent(passwordLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(jPanel1Layout.createParallelGroup()
				    .addComponent(fieldUsername, GroupLayout.Alignment.LEADING, 0, 177, Short.MAX_VALUE)
				    .addComponent(fieldPassword, GroupLayout.Alignment.LEADING, 0, 177, Short.MAX_VALUE))
				.addContainerGap(22, 22));
			jPanel1Layout.linkSize(SwingConstants.VERTICAL, new Component[] {fieldUsername, fieldPassword});
			jPanel1.setBorder(BorderFactory.createTitledBorder("Login"));
		thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
			.addContainerGap(22, 22)
			.addGroup(thisLayout.createParallelGroup()
			    .addComponent(jPanel1, GroupLayout.Alignment.LEADING, 0, 396, Short.MAX_VALUE)
			    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
			        .addGap(232, 232, GroupLayout.PREFERRED_SIZE)
			        .addComponent(buttonLogin, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
			        .addGap(49)))
			.addContainerGap(28, 28));
		thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap(38, 38)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(buttonLogin, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(53, 53));
		}
	}

	/**
	 * Il pulsante login 
	 * @return il riferimento al pulsante
	 */
	public YButton getButtonLogin() {
		return buttonLogin;
	}

	/**
	 * Il campo password
	 * @return il riferimento al campo
	 */
	public YPasswordField getFieldPassword() {
		return fieldPassword;
	}

	/**
	 * Il campo username
	 * @return il riferimento al campo
	 */
	public YTextField getFieldUsername() {
		return fieldUsername;
	}
}
