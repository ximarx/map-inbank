package gccb.impiegato.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.FocusManager;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fi.mmm.yhteinen.swing.core.YComponent;
import fi.mmm.yhteinen.swing.core.component.YButton;
import fi.mmm.yhteinen.swing.core.component.YDialog;
import fi.mmm.yhteinen.swing.core.component.YFrame;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.component.menu.YMenu;
import fi.mmm.yhteinen.swing.core.component.menu.YMenuBar;
import fi.mmm.yhteinen.swing.core.component.menu.YMenuItem;
import fi.mmm.yhteinen.swing.core.component.text.YPasswordField;
import fi.mmm.yhteinen.swing.core.component.text.YTextField;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import gccb.common.gui.YKeyBasedEventPasswordField;
import gccb.common.gui.YKeyBasedEventTextField;

/**
 * Frame principale del client Impiegato
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoFrame extends YFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5515675562442626438L;

	private YMenuBar menuBar = new YMenuBar();
	
	private YMenu menuApplicazione = new YMenu();
	private YMenu menuRicerca = new YMenu();
	private YMenu menuAggiungi = new YMenu();
	private YMenu menuHelp = new YMenu();
	{
		menuApplicazione.setText("Applicazione");
		menuRicerca.setText("Ricerca");
		menuAggiungi.setText("Aggiungi");
		menuHelp.setText("Help");
	}

	private YMenuItem itemAggiungiConto = new YMenuItem();
	private YMenuItem itemAggiungiCorrentista = new YMenuItem();
	private YMenuItem itemRicercaConto = new YMenuItem();
	private YMenuItem itemRicercaCorrentista = new YMenuItem();
	private YMenuItem itemApplicazioneStatistiche = new YMenuItem();
	private YMenuItem itemApplicazioneLogout = new YMenuItem();
	private YMenuItem itemApplicazioneEsci = new YMenuItem();
	private YMenuItem itemHelpGuida = new YMenuItem();
	{
		itemAggiungiConto.setText("Aggiungi Conto");
		itemAggiungiCorrentista.setText("Aggiungi Correntista");
		itemRicercaConto.setText("Visualizza Conto");
		itemRicercaCorrentista.setText("Visualizza Correntista");
		itemApplicazioneStatistiche.setText("Statistiche");
		itemApplicazioneLogout.setText("Logout");
		itemApplicazioneEsci.setText("Esci");
		itemHelpGuida.setText("Guida in linea");
	}

	private YTextField fieldUsername = new YKeyBasedEventTextField();
	private YPasswordField fieldPassword = new YKeyBasedEventPasswordField();
	private YButton buttonAnnullaLogin = new YButton("Annulla");
	private YButton buttonConfermaLogin = new YButton("Login");
	
	private LoginDialog loginDialog = new LoginDialog(this);
	{
		loginDialog.setTitle("Autenticazione");
	}
	private CustomDialog customDialog;
	
	public ImpiegatoFrame() {
		setMvcName("impiegatoFrame");
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
		loginDialog.setLocationRelativeTo(this);
	}
	
	private void setMVCNames() {

		itemAggiungiConto.setMvcName("itemAggiungiConto");
		itemAggiungiCorrentista.setMvcName("itemAggiungiCorrentista");
		itemRicercaConto.setMvcName("itemRicercaConto");
		itemRicercaCorrentista.setMvcName("itemRicercaCorrentista");
		itemApplicazioneLogout.setMvcName("itemApplicazioneLogout");
		itemApplicazioneStatistiche.setMvcName("itemApplicazioneStatistiche");
		itemApplicazioneEsci.setMvcName("itemApplicazioneEsci");
		itemHelpGuida.setMvcName("itemHelpGuida");
		fieldUsername.setMvcName("username");
		fieldUsername.getYProperty().put(YComponent.CHECK_CHANGES, new Boolean(true));
		fieldUsername.setKeyEventsEnabled(true);
		fieldPassword.setMvcName("password");
		fieldPassword.getYProperty().put(YComponent.CHECK_CHANGES, new Boolean(true));
		fieldPassword.setKeyEventsEnabled(true);
		buttonAnnullaLogin.setMvcName("buttonAnnullaLogin");
		buttonConfermaLogin.setMvcName("buttonConfermaLogin");
		
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setSize(new java.awt.Dimension(800, 600));
			this.setPreferredSize(new java.awt.Dimension(800, 600));
			getContentPane().setLayout(thisLayout);

			setJMenuBar(menuBar);
			
			menuApplicazione.add(itemApplicazioneStatistiche);
			menuApplicazione.add(itemApplicazioneLogout);
			menuApplicazione.add(itemApplicazioneEsci);
			
			menuRicerca.add(itemRicercaConto);
			menuRicerca.add(itemRicercaCorrentista);
			
			menuAggiungi.add(itemAggiungiConto);
			menuAggiungi.add(itemAggiungiCorrentista);
			//menuAggiungi.add(itemAggiungiBancomat);
			
			menuHelp.add(itemHelpGuida);
			
			menuBar.add(menuApplicazione);
			menuBar.add(menuRicerca);
			menuBar.add(menuAggiungi);
			menuBar.add(menuHelp);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Imposta il contenuto del pannello principale
	 * @param newPanel il nuovo pannello
	 */
	public void setContent(YPanel newPanel) {
		getContentPane().removeAll();
		getContentPane().add(newPanel, BorderLayout.CENTER);
		getContentPane().validate();
		getContentPane().repaint();
	}
	
	/**
	 * Inizia se mostrare la finestra di login
	 * @param b mostrare?
	 */
	public void showLoginDialog(boolean b) {
		loginDialog.setVisible(b);
		this.setEnabled(!b);
		FocusManager.getCurrentManager().downFocusCycle(loginDialog);
		//this.setVisible(!b);
	}
	
	/**
	 * Visualizza una finestra personalizzata
	 * @param size la domensione della finestra
	 * @param dialogMVCName il nome MVC della finestra
	 * @param winTitle il titolo della finestra
	 * @param mainObj il contenuto del pannello principale
	 * @param buttonLabel le etichette dei pulsanti
	 * @param buttonsName i nomi MVC dei pulsanti
	 */
	public void showCustomDialog(Dimension size, String dialogMVCName, String winTitle, Component mainObj, String[] buttonLabel, String[] buttonsName) {
		if ( customDialog != null ) customDialog.setVisible(false);
		customDialog = new CustomDialog(this, dialogMVCName, mainObj, buttonLabel, buttonsName);
		customDialog.setTitle(winTitle);
		customDialog.setSize(size);
		customDialog.setLocationRelativeTo(this);
		customDialog.setVisible(true);
		customDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Nasconde l'ultima finestra personalizzata visualizzata
	 */
	public void hideCustomDialog() {
		customDialog.setVisible(false);
	}
	
	private class LoginDialog extends YDialog {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3024314782183997919L;
		public LoginDialog(YFrame parent) {
			super(parent);
			setMvcName("LoginDialog");
			initGUI();
			//YUIToolkit.guessViewComponents(this);
		}
		private void initGUI() {
			setSize(new Dimension(330,120));
			GridLayout gl = new GridLayout(3, 2, 10, 10);
			this.getContentPane().setLayout(gl);
			this.getContentPane().add(new JLabel("Username:"));
			this.getContentPane().add(fieldUsername);
			this.getContentPane().add(new JLabel("Password:"));
			this.getContentPane().add(fieldPassword);
			this.getContentPane().add(buttonAnnullaLogin);
			this.getContentPane().add(buttonConfermaLogin);
			
		}
		
	}
	
	private class CustomDialog extends YDialog {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -6298686248563679571L;

		public CustomDialog(final YFrame parent, String dialogMVCName, Component mainObj, String[] buttonLabel, String[] buttonsName) {
			super(parent);
			
			ArrayList<YButton> buttonL = new ArrayList<YButton>(buttonsName.length);
			int i = 0;
			for ( String buttonName : buttonsName ) {
				YButton b;
				try {
					b = new YButton(buttonLabel[i++]);
				} catch (ArrayIndexOutOfBoundsException e) {
					b = new YButton(buttonName);
				}
				// ricevuto da dialogNameButtonNamePressed
				final String methodName = dialogMVCName + buttonName.substring(0,1).toUpperCase() + buttonName.substring(1) + "Pressed";
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						YUIToolkit.getController(parent).invokeMethodIfFound(methodName);
					}
				});
				buttonL.add(b);
			}
			
			initGUI(mainObj, buttonL);
			setMvcName(dialogMVCName);
		}
		
		private void initGUI(Component mainObj, List<YButton> buttonList) {
			YPanel main = new YPanel();
			main.setLayout(new BorderLayout(5,5));
			
			YPanel buttonPanel = new YPanel();
			buttonPanel.setLayout(new GridLayout(1, buttonList.size(), 10, 5));
			
			for ( YButton b : buttonList) {
				buttonPanel.add(b);
			}
			
			main.add(mainObj, BorderLayout.CENTER);
			main.add(buttonPanel, BorderLayout.SOUTH);
			this.setContentPane(main);
		}
		
	}
}
