package gccb.server.gui;

import fi.mmm.yhteinen.swing.core.component.YFrame;
import fi.mmm.yhteinen.swing.core.component.menu.YMenu;
import fi.mmm.yhteinen.swing.core.component.menu.YMenuItem;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JMenuBar;

/**
 * Finestra principale della gui del server
 * @author Francesco Capozzo
 *
 */
public class ServerFrame extends YFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6485785685940205019L;
	private JMenuBar menuBar = new JMenuBar();
	private YMenu menuApplicazione = new YMenu();
	private YMenu menuDatabase = new YMenu();
	private YMenu menuPostazioni = new YMenu();
	private YMenu menuHelp = new YMenu();
	{
		menuApplicazione.setText("Applicazione");
		menuDatabase.setText("Database");
		menuPostazioni.setText("Client");
		menuHelp.setText("Help");
	}
	private YMenuItem appEsci = new YMenuItem();
	private YMenuItem appServizi = new YMenuItem();
	private YMenuItem appConnessi = new YMenuItem();

	private YMenuItem dbConnetti = new YMenuItem();
	private YMenuItem dbDisconnetti = new YMenuItem();
	private YMenuItem dbBackup = new YMenuItem();
	
	private YMenuItem clientRegistrati = new YMenuItem();
	private YMenuItem clientAggiungi = new YMenuItem();
	
	private YMenuItem helpGuidaInLinea = new YMenuItem();
	
	{
		appEsci.setText("Esci");
		appServizi.setText("Pannello servizi");
		appConnessi.setText("Client connessi");
		dbConnetti.setText("Connetti a database");
		dbDisconnetti.setText("Disconnetti da database");
		dbBackup.setText("Backup database");
		clientAggiungi.setText("Aggiungi Impiegato");
		clientRegistrati.setText("Elenco impiegati");
		helpGuidaInLinea.setText("Guida in linea");
	}
	
	/**
	 * Inizializza i componenti, imposta i nominativi per il MVC e inizializza l'interfaccia
	 */
	public ServerFrame() {
		initComponents();
		setMVCNames();
		initGUI();
		YUIToolkit.guessViewComponents(this);
	}
	
	/**
	 * Inizializza i componenti
	 */
	private void initComponents() {
		
		menuApplicazione.add(appConnessi);
		menuApplicazione.add(appServizi);
		menuApplicazione.add(appEsci);
		
		menuDatabase.add(dbConnetti);
		menuDatabase.add(dbDisconnetti);
		menuDatabase.add(dbBackup);
		
		menuPostazioni.add(clientRegistrati);
		menuPostazioni.add(clientAggiungi);
		
		menuHelp.add(helpGuidaInLinea);
		
		menuBar.add(menuApplicazione);
		menuBar.add(menuDatabase);
		menuBar.add(menuPostazioni);
		menuBar.add(menuHelp);
		
	}
	
	/**
	 * Dispone gli elementi
	 */
	public void initGUI() {
		BorderLayout thisLayout = new BorderLayout(5,5);
		getContentPane().setLayout(thisLayout);
		setJMenuBar(menuBar);
		this.setSize(550, 350);
	}
	
	/**
	 * Imposta i nominativi per l'uso tramite MVC
	 */
	public void setMVCNames() {
		setMvcName("serverFrame");
		appConnessi.setMvcName("appConnessi");
		appServizi.setMvcName("appServizi");
		appEsci.setMvcName("appEsci");
		dbConnetti.setMvcName("dbConnetti");
		dbDisconnetti.setMvcName("dbDisconnetti");
		dbBackup.setMvcName("dbBackup");
		clientRegistrati.setMvcName("clientRegistrati");
		clientAggiungi.setMvcName("clientAggiungi");
		helpGuidaInLinea.setMvcName("helpGuidaInLinea");
	}
	
	/**
	 * Altera lo stato di attivazione dei pulsanti dei menu
	 * in base allo stato della connessione al database
	 * @param dbConnected il nuovo stato del database
	 */
	public void setStatoPulsanti( boolean dbConnected) {
		dbDisconnetti.setEnabled(dbConnected);
		appConnessi.setEnabled(dbConnected);
		appServizi.setEnabled(dbConnected);
		menuPostazioni.setEnabled(dbConnected);
		dbConnetti.setEnabled(!dbConnected);
		dbBackup.setEnabled(!dbConnected);
	}
	
	/**
	 * Aggiunge i componenti nelle posizioni
	 * @param centerWidget il pannello centrale
	 * @param bottomWidget il pannello in basso
	 */
	public void setWidget(Component centerWidget, Component bottomWidget) {
		substituteWidget(centerWidget, BorderLayout.CENTER);
		substituteWidget(bottomWidget, BorderLayout.SOUTH);
		getContentPane().validate();
		getContentPane().repaint();
	}
	
	/**
	 * Sostituisce un componente nella posizione indicata con un nuovo componente
	 * @param newWidget il nuovo componente
	 * @param position la posizione in cui inserirlo
	 */
	private void substituteWidget(Component newWidget, String position) {
		if ( newWidget != null ) {
			Container cp = getContentPane();
			try {
				cp.remove(((BorderLayout )cp.getLayout()).getLayoutComponent(position));
			} catch (Exception e) { /* magari non c'è nulla */}
			cp.add(newWidget, position);
		}
	}
}
