package gccb.bancomat;

import java.util.Timer;
import java.util.TimerTask;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.tools.YUIToolkit;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.bancomat.external.BancomatExternalDeviceController;
import gccb.bancomat.gui.BancomatFrame;
import gccb.bancomat.model.BancomatFrameModel;
import gccb.bancomat.net.BancomatServiceDelegate;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.ILogoutAction;

/**
 * Controller principale dell'applicazione
 * Gestisce l'interazione suddividendola in vari sottocontroller figli
 * @author Francesco Capozzo
 *
 */
public class BancomatFrameController extends YController {

	private BancomatFrame view = new BancomatFrame();
	private BancomatFrameModel model = new BancomatFrameModel();
	
	private LoginController loginC = new LoginController();
	private OperazioniController operazioniC = new OperazioniController();
	private ImportiController importiC = new ImportiController();
	private ConfermaController confermaC = new ConfermaController();
	private ErrorController errorC = new ErrorController(); 
	
	private BancomatExternalDeviceController externalC = new BancomatExternalDeviceController(model);
	
	private Timer timer = new Timer();
	
	private boolean opSaldoToo = false;
	

	/**
	 * Gestisce la chiusura della finestra principale disconnettendo
	 * il client e chiudend l'applicazione
	 */
	public void bancomatFrameClosing() {
		if ( BancomatServiceDelegate.isConnected() ) {
			BancomatServiceDelegate.disconnect();
		}
		System.exit(1);
	}

	/**
	 * Inizializza il controller impostando modello e vista
	 * e visualizzando al finestra principale
	 */
	public BancomatFrameController() {
		YUIToolkit.setUpMVC(model, view, this);
		register(InBankBancomat.CLIENT_CONNECTED);
		register(InBankBancomat.CLIENT_DISCONNECTED);
		register(InBankBancomat.CARD_IN);
		register(InBankBancomat.CARD_OUT);
		addChildren();
		setContent(view.BENVENUTO);
		view.setLocationRelativeTo(null);
		view.setVisible(true);
	}
	
	private void addChildren() {
		addChild(externalC);
		addChild(loginC);
		addChild(operazioniC);
		addChild(importiC);
		addChild(confermaC);
		addChild(errorC);
	}
	
	/**
	 * Imposta il contenuto del pannello principale della finestra
	 * @param newView il nuovo contenuto
	 */
	public void setContent(YPanel newView) {
		view.setContent(newView);
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankBancomat.CLIENT_CONNECTED)  ) {
			setContent(view.OPERAZIONI);
		} else if ( event.getName().equals(InBankBancomat.CLIENT_DISCONNECTED) ) {
			model.resetAll();
		} else if ( event.getName().equals(InBankBancomat.CARD_IN) ) {
			setContent(view.LOGIN);
		} else if ( event.getName().equals(InBankBancomat.CARD_OUT) ) {
			// lanciato da BancomatExternalDeviceController
			setContent(view.ARRIVEDERCI);
			timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					setContent(view.BENVENUTO);
					sendApplicationEvent(new YApplicationEvent(InBankBancomat.READY));
				};
			}, 15 * 1000);
		}
	}
	
	/**
	 * Gestisce l'evento di annullamento dell'operazione
	 * visualizzando la schermata di fine operazione ed
	 * emettendo la carta
	 */
	public void commonButtonAnnullaPressed() {
		YWorker yw = new YWorker();
		try {
			yw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					if ( BancomatServiceDelegate.isConnected() ) {
						BancomatServiceDelegate.disconnect();
					}
					sendApplicationEvent(new YApplicationEvent(InBankBancomat.CLIENT_DISCONNECTED));
					return null;
				}
			}, "Annullamento in corso...");
		} catch ( Exception e) {
			yw.interrupt();
			ExceptionHandlerManager.process(e);
			sendApplicationEvent(new YApplicationEvent(InBankBancomat.CLIENT_DISCONNECTED));
		}
	}
	
	/**
	 * Restituisce una istanza di {@link YController} corrispondente
	 * al controller delegato alla visualizzazione degli errori
	 * @return l'errorController
	 */
	public YController getErrorController() {
		return errorC;
	}
	
	private class LoginController extends YController {
		public LoginController() {
			setUpMVC(model, view.LOGIN);
		}
		public void passwordEnterPressed() {
			YWorker yw = new YWorker();
			try {
				yw.execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						//try {
							model.connect();
							model.updateConto();
							sendApplicationEvent(new YApplicationEvent(InBankBancomat.CLIENT_CONNECTED));
						//} catch ( CredenzialiNonValideException cnve ) {
							//ExceptionHandlerManager.process(cnve);
							/*
							timer.schedule(new TimerTask() {
								public void run() {
									sendApplicationEvent(new YApplicationEvent(InBankBancomat.CLIENT_DISCONNECTED));
								};
							}, 10 * 1000);
							*/
						//}
						return null;
					}
				}, "Verifica credenziali in corso...");
				BancomatServiceDelegate.setLogoutAction(new ILogoutAction() {
					@Override
					public void execute() {
						ExceptionHandlerManager.process(new GccbNetException("Il server ha richiesto la disconnessione."));
					}
				});
			} catch (Exception e) {
				//yw.interrupt();
				ExceptionHandlerManager.process(e);
			}
		}
	}
	
	private class OperazioniController extends YController {
		public OperazioniController() {
			setUpMVC(model, view.OPERAZIONI);
		}
		public void buttonSaldoPressed() {
			opSaldoToo = true;
			view.IMPORTI.setMaxPrelevabile(model.getBancomat().getMassimoPrelevabile() );
			setContent(view.IMPORTI);
		}
		
		public void buttonPrelievoPressed() {
			opSaldoToo = false;
			view.IMPORTI.setMaxPrelevabile(model.getBancomat().getMassimoPrelevabile());
			setContent(view.IMPORTI);
		}		
	}
	
	private class ImportiController extends YController {
		public ImportiController() {
			setUpMVC(model, view.IMPORTI);
		}
		public void button50Pressed() {
			model.setImporto(50);
			model.notifyObservers("importo");
			setContent(view.CONFERMA);
		}
		
		public void button100Pressed() {
			model.setImporto(100);
			model.notifyObservers("importo");
			setContent(view.CONFERMA);
		}

		public void button150Pressed() {
			model.setImporto(150);
			model.notifyObservers("importo");
			setContent(view.CONFERMA);
		}

		public void button250Pressed() {
			model.setImporto(250);
			model.notifyObservers("importo");
			setContent(view.CONFERMA);
		}
		
		public void button500Pressed() {
			model.setImporto(500);
			model.notifyObservers("importo");
			setContent(view.CONFERMA);
		}
		
		public void buttonMaxPressed() {
			model.setImporto(model.getBancomat().getMassimoPrelevabile());
			model.notifyObservers("importo");
			setContent(view.CONFERMA);
		}
		
		public void buttonAnnullaPressed() {
			commonButtonAnnullaPressed();
		}
	}
	
	private class ConfermaController extends YController {
		
		public ConfermaController() {
			setUpMVC(model, view.CONFERMA);
		}
		
		public void buttonAnnullaPressed() {
			commonButtonAnnullaPressed();
		}
		
		public void buttonConfermaPressed() {
			YWorker yw = new YWorker();
			try {
				yw.execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						model.doPrelievo();
						externalC.emettiBanconote(model.getImporto());
						if ( opSaldoToo ) {
							externalC.stampaEstrattoConto();
						}
						if ( BancomatServiceDelegate.isConnected() ) {
							BancomatServiceDelegate.disconnect();
						}
						sendApplicationEvent(new YApplicationEvent(InBankBancomat.CLIENT_DISCONNECTED));
						return null;
					}
				});
			} catch ( Exception e) {
				yw.interrupt();
				ExceptionHandlerManager.process(e);
			}
		}
	}
	
	private class ErrorController extends YController {
		public ErrorController() {
			setUpMVC(null, view.ERRORE);
		}
		public void buttonAnnullaPressed() {
			commonButtonAnnullaPressed();
		}
		public void showError(String header, String msg) {
			view.ERRORE.setIntestazione(header);
			view.ERRORE.setMessaggio(msg);
			setContent(view.ERRORE);
		}
		
	}
	
}
