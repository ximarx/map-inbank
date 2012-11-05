package gccb.server;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.common.gui.ErrorDialogView;
import gccb.database.ServerDB;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.handler.GuiHandler;
import gccb.server.gui.ServerFrame;
import gccb.server.net.ServerServiceDelegate;


/**
 * Controller principale dell'applicazione InBankServer
 * @author Francesco Capozzo
 *
 */
public class ServerFrameController extends YController {
	
	private ServerConnectionWidgetController wConnection = new ServerConnectionWidgetController();
	private ServerServiziWidgetController wServizi = new ServerServiziWidgetController();
	private ServerListaClientWidgetController wClient = new ServerListaClientWidgetController();
	private ServerListaImpiegatiWidgetController wImpiegati = new ServerListaImpiegatiWidgetController();
	
	private ServerFrame view = new ServerFrame();
	
	/**
	 * Inizializza il controller e visualizza la finestra principale
	 */
	public ServerFrameController() {
		setUpMVC(null, view);
		register(InBankServer.DB_CONNECTED);
		register(InBankServer.DB_DISCONNECTED);
		addChildren();
		setBottomWidget(wConnection);
		view.setTitle("InBank Service Server");
		view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		view.setStatoPulsanti(false);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
		// supporto per trayicon (per ora funzionante solo su windows e con java 1.6)
		if ( SystemTray.isSupported() ) {
			SystemTray tray = SystemTray.getSystemTray();
			Image img = Toolkit.getDefaultToolkit().getImage("images/images.gif");
			TrayIcon ti = new TrayIcon(img, "InBank Server");
			ti.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					view.setVisible(!view.isVisible());
				}
			});
			try {
				tray.add(ti);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addChildren() {
		addChild(wConnection);
		addChild(wServizi);
		addChild(wClient);
		addChild(wImpiegati);
	}
	
	/**
	 * Imposta il widget centrale
	 * @param c il controller del widget centrale
	 */
	private void setCenterWidget(YController c) {
		view.setWidget((Component) c.getView(), null);
	}
	/**
	 * Imposta il widget in basso
	 * @param c il controller del widget in basso
	 */
	private void setBottomWidget(YController c) {
		view.setWidget(null, (Component) c.getView());
	}
	
	/**
	 * Funzione per la visualizzazione degli errori
	 * Viene invocata da {@link GuiHandler} per la visualizzazione degli errori
	 * @param e l'eccezzione da visualizzare
	 */
	public void showError(Exception e) {
		ErrorDialogView.showError(e);
	}
	
	/**
	 * Gestisce la chiusura della finestra principale di nome MVC serverFrame
	 */
	public void serverFrameClosing() {
		if ( ServerServiceDelegate.isDbConnesso() ) {
			if ( JOptionPane.showConfirmDialog(view,
				"Tutti i  client verranno disconnessi. Continuare?",
				"Conferma chiusura",
				JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION ) {
				try {
					new YWorker().execute(new YWorkerCommand(){
						@Override
						public Object execute() throws Exception {
							ServerServiceDelegate.disattivaTuttiServizi();
							if ( ServerServiceDelegate.isDbConnesso() )
								ServerServiceDelegate.disconnettiDb();
							System.exit(1);
							return null;
						}
					}, "Chiusura in corso...");
				} catch (Exception e) {
					ExceptionHandlerManager.process(e);
				}
			}
		} else {
			System.exit(1);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	@Override
	public void receivedApplicationEvent(YApplicationEvent event) {
		view.setStatoPulsanti(ServerServiceDelegate.isDbConnesso());
	}
	
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina applicazione->client connessi
	 */
	public void appConnessiSelected() {
		setCenterWidget(wClient);
	}
	
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina applicazione->pannello servizi
	 */
	public void appServiziSelected() {
		setCenterWidget(wServizi);
	}
	
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina applicazione->esci
	 */
	public void appEsciSelected() {
		serverFrameClosing();
	}

	/**
	 * Funzione eseguita se viene cliccato il menu a tendina Database->connetti a database
	 */
	public void dbConnettiSelected() {
		try {
			new YWorker().execute(new YWorkerCommand(){
				@Override
				public Object execute() throws Exception {
					ServerServiceDelegate.connettiDb();
					return null;
				}
			}, "Connessione in corso...");
			sendApplicationEvent(new YApplicationEvent(InBankServer.DB_CONNECTED));
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
			sendApplicationEvent(new YApplicationEvent(InBankServer.DB_DISCONNECTED));
		}
	}
	
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina Database->disconnetti da database
	 */
	public void dbDisconnettiSelected() {
		if ( JOptionPane.showConfirmDialog(view,
				"Tutti i  client verranno disconnessi. Continuare?",
				"Conferma disconnessione",
				JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION ) {
			try {
				new YWorker().execute(new YWorkerCommand(){
					@Override
					public Object execute() throws Exception {
						ServerServiceDelegate.disattivaTuttiServizi();
						ServerServiceDelegate.disconnettiDb();
						return null;
					}
				}, "Disconnessione in corso...");
			} catch (Exception e) {
				ExceptionHandlerManager.process(e);
			}
			view.setWidget(new YPanel(), null);
			sendApplicationEvent(new YApplicationEvent(InBankServer.SERVICES_CHANGED));
			sendApplicationEvent(new YApplicationEvent(InBankServer.DB_DISCONNECTED));
		}
	}
	
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina Database->backup database
	 */
	public void dbBackupSelected() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmm");
		String filename = "Backup_" + sdf.format(new Date()) + ".sql";
		final String finalFilename = filename;
		
		if ( JOptionPane.showConfirmDialog(view,
				"Eseguire il backup dei dati nel file \""+filename+"\" ?",
				"Conferma Backup",
				JOptionPane.YES_OPTION)
				== JOptionPane.YES_OPTION ) {
			
			try {
				new YWorker().execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						//TimeUnit.SECONDS.sleep(10);
						ServerServiceDelegate.connettiDb();
						File f = new File(finalFilename);
						int i = 1; 
						while ( f.exists() ) {
							f = new File(finalFilename.substring(0, finalFilename.length() - 4) + "-" + i++ + ".sql");
						}
						FileWriter fw=null;
						fw = new FileWriter(f);
						String s = ServerDB.getReference().dumpDB();
						StringReader sr = new StringReader(s);
						char[] chars = new char[1024];
						int bytes = 0;
						while ( (bytes = sr.read(chars)) > 0) {
							fw.write(chars, 0, bytes);
						}
						fw.close(); 			
						return null;
					}
				});
			} catch (Exception e) {
				ExceptionHandlerManager.process(e);
			} finally {
				ServerServiceDelegate.disconnettiDb();
			}
		}
	}
	
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina Client->Elenco impiegati
	 */
	public void clientRegistratiSelected() {
		setCenterWidget(wImpiegati);
	}
	
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina Client->aggiungi impiegato
	 */
	public void clientAggiungiSelected() {
		wImpiegati.buttonAggiungiPressed();
	}
	/**
	 * Funzione eseguita se viene cliccato il menu a tendina Help-> help in linea
	 */
	
	public void helpGuidaInLineaSelected() {
		
		Desktop desk = Desktop.getDesktop();
        File help = new File("help/inbankserver.chm");
        try {
            desk.open(help);
        } catch (IOException ex) {
            ExceptionHandlerManager.process(new Exception("File della guida non trovato"));
        }	
	}

}
