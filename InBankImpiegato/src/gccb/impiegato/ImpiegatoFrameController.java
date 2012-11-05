package gccb.impiegato;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.component.YPanel;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.IPersona;
import gccb.common.gui.YKeyBasedEventTextField;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoFrame;
import gccb.impiegato.model.ImpiegatoBancomatModel;
import gccb.impiegato.model.ImpiegatoContiModel;
import gccb.impiegato.model.ImpiegatoFrameModel;
import gccb.impiegato.model.ImpiegatoMovimentiModel;
import gccb.impiegato.model.ImpiegatoPersoneModel;
import gccb.impiegato.net.ImpiegatoServiceDelegate;
import gccb.net.rmi.ILogoutAction;

/**
 * Front Controller dell'applicazione. Inizializza la finestra principale
 * e tutti i sotto controller delle varie componenti. Inoltre
 * gestisce anche la finestra di login
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoFrameController extends YController {

	private ImpiegatoFrame view = new ImpiegatoFrame();
	private ImpiegatoFrameModel model = new ImpiegatoFrameModel();
	
	private ImpiegatoStatisticheController statisticheC = new ImpiegatoStatisticheController();
	private ImpiegatoDettagliContoController dettagliContoC = new ImpiegatoDettagliContoController();
	private ImpiegatoRicercaContoController ricercaC = new ImpiegatoRicercaContoController();
	private ImpiegatoListaBancomatController listaBancomatC = new ImpiegatoListaBancomatController();
	private ImpiegatoListaMovimentiController listaMovimentiC = new ImpiegatoListaMovimentiController();
	private ImpiegatoListaContiController listaContiC = new ImpiegatoListaContiController();
	private ImpiegatoRicercaPersonaController ricercaPC = new ImpiegatoRicercaPersonaController();
	private ImpiegatoDettagliCorrentistaController dettagliCorrentistaC = new ImpiegatoDettagliCorrentistaController();
	private ImpiegatoAddCorrentistaController addCorrentistaC = new ImpiegatoAddCorrentistaController();
	private ImpiegatoAddContoController addContoC = new ImpiegatoAddContoController();

	{
		dettagliContoC.setWidget(listaBancomatC.getView(), listaMovimentiC.getView());
		dettagliCorrentistaC.setWidget(listaContiC.getView());
	}
	
	/**
	 * Inizializza il sistema e l'interfaccia grafica visualizzando la finestra di login
	 */
	public ImpiegatoFrameController() {
		setUpMVC(model, view);
		addChildren();
		register(InBankImpiegato.CONTO_CLOSED);
		view.setLocationRelativeTo(null);
		view.setTitle("InBank Client Impiegato Bancario");
		defaultView();
		view.showLoginDialog(true);
	}
	
	/**
	 * Imposta il contenuto della finestra principale con la vista delle
	 * statistiche
	 */
	public void defaultView() {
		setContent(statisticheC.getView());
	}
	
	private void addChildren() {
		addChild(statisticheC);
		addChild(dettagliContoC);
		addChild(dettagliCorrentistaC);
		addChild(ricercaC);
		addChild(listaBancomatC);
		addChild(listaMovimentiC);
		addChild(listaContiC);
		addChild(addCorrentistaC);
		addChild(addContoC);
	}
	
	/**
	 * Gestisce l'evento di chiusura della finestra principale
	 * terminando l'applicazione
	 */
	public void impiegatoFrameClosing() {
		disconnect();
		System.exit(1);
	}
	
	/**
	 * Imposta l'elemento contenuto dal pannello principale della
	 * finestra principale
	 * @param newC il nuovo elemento
	 */
	public void setContent(Object newC) {
		try {
			view.setContent((YPanel) newC);
		} catch ( ClassCastException cce) {
			ExceptionHandlerManager.process(cce);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankImpiegato.CONTO_CLOSED) ) {
			defaultView();
			ImpiegatoContiModel.getInstace().resetAll();
			ImpiegatoMovimentiModel.getInstace().resetAll();
			ImpiegatoBancomatModel.getInstace().resetAll();
		}
	}

	/**
	 * Gestisce la visualizzazione degli errori
	 * Può invocata da GuiHandler per gestire la visualizzazione
	 * delle eccezioni tramite {@link ExceptionHandlerManager} 
	 * @param e l'eccezione da visualizzare
	 */
	public void showError(Exception e) {
		JOptionPane.showMessageDialog(view, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Esegue la disconnessione del client dal server
	 * inoltrando l'evento {@link InBankImpiegato#CLIENT_DISCONNECTED}
	 */
	public void disconnect() {
		YWorker yw = new YWorker();
		try {
			yw.execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					if ( ImpiegatoServiceDelegate.isConnected() ) {
						ImpiegatoServiceDelegate.disconnect();
					}
					return null;
				}
			});
			sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CLIENT_DISCONNECTED));
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
			System.exit(-1);
		}
	}
	
	/**
	 * Gestisce l'evento del click sul menu a tendina Applicazione->Statistiche
	 * della finestra principale
	 */
	public void itemApplicazioneStatisticheSelected() {
		if ( ImpiegatoServiceDelegate.isConnected() )
			statisticheC.handleApplicationEvent(new YApplicationEvent(InBankImpiegato.CLIENT_CONNECTED));
		setContent(statisticheC.getView());
	}
	/**
	 * Gestisce l'evento del click sul menu a tendina Applicazione->Logout
	 * della finestra principale
	 */
	public void itemApplicazioneLogoutSelected() {
		model.cleanAll();
		defaultView();
		disconnect();
		view.setVisible(false);
		view.showLoginDialog(true);
	}
	/**
	 * Gestisce l'evento del click sul menu a tendina Applicazione->Esci
	 * della finestra principale
	 */
	public void itemApplicazioneEsciSelected() {
		impiegatoFrameClosing();
	}

	/**
	 * Gestisce l'evento del click sul menu a tendina Aggiungi->Aggiungi Correntista
	 * della finestra principale
	 */
	public void itemAggiungiCorrentistaSelected() {
		view.showCustomDialog(new Dimension(500,250), "aggiungiCorrentistaDialog", "Aggiungi correntista",
				(Component) addCorrentistaC.getView(),
				new String[] {"Annulla", "Conferma"},
				new String[] {"Annulla", "Conferma"});
	}

	/**
	 * Gestisce l'evento della pressione del tasto annulla
	 * della finestra di aggiunta correntista
	 */
	public void aggiungiCorrentistaDialogAnnullaPressed() {
		view.hideCustomDialog();
	}
	
	/**
	 * Gestisce l'evento della pressione del tasto conferma
	 * della finestra di aggiunta correntista aggiungendo
	 * un nuovo correntista
	 */
	public void aggiungiCorrentistaDialogConfermaPressed() {
		try {
			addCorrentistaC.save();
			ricercaCorrentistaDialogSelezionaPressed();
			} catch ( Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}	
	
	/**
	 * Gestisce l'evento del click sul menu a tendina Aggiungi->Aggiungi Conto
	 * della finestra principale
	 */
	public void itemAggiungiContoSelected() {
		ImpiegatoContiModel.getInstace().resetCreazione();
		ImpiegatoContiModel.getInstace().setNewIntestatario(ImpiegatoPersoneModel.getInstace().getPersona());
		setContent(addContoC.getView());
	}
	
	/**
	 * Gestisce l'evento del click sul menu a tendina Ricerca->Ricerca Conto
	 * della finestra principale
	 */
	public void itemRicercaContoSelected() {
		ImpiegatoContiModel.getInstace().resetRicerca();
		view.showCustomDialog(new Dimension(500,350), "ricercaContoDialog", "Ricerca conto",
				(Component) ricercaC.getView(),
				new String[] {"Annulla", "Seleziona"},
				new String[] {"Annulla", "Seleziona"});
	}
	/**
	 * Gestisce l'evento del click sul pulsante di annullamento
	 * della finestra di ricerca conto
	 */
	public void ricercaContoDialogAnnullaPressed() {
		ImpiegatoContiModel.getInstace().resetRicerca();
		ImpiegatoContiModel.getInstace().resetVisualizzazione();
		view.hideCustomDialog();
	}
	
	/**
	 * Gestisce l'evento del click sul pulsante di conferma selezione
	 * della finestra di ricerca conto
	 */
	public void ricercaContoDialogSelezionaPressed() {
		if ( ImpiegatoContiModel.getInstace().getConto() != null ) {
			try {
				ImpiegatoBancomatModel.getInstace().setConto(ImpiegatoContiModel.getInstace().getConto());
				ImpiegatoBancomatModel.getInstace().aggiornaListaBancomat();
				ImpiegatoMovimentiModel.getInstace().setConto(ImpiegatoContiModel.getInstace().getConto());
				ImpiegatoMovimentiModel.getInstace().aggiornaListaTransazioni();
				setContent(dettagliContoC.getView());
				view.hideCustomDialog();
			} catch ( Exception e) {
				ExceptionHandlerManager.process(e);
			}
		} else {
			ExceptionHandlerManager.process(new IllegalArgumentException("Nessun conto selezionato"));
		}
	}
	
	/**
	 * Gestisce l'evento del click sul menu a tendina Ricerca->Ricerca Correntista
	 * della finestra principale
	 */
	public void itemRicercaCorrentistaSelected() {
		//setContent(dettagliContoC.getView());
		ImpiegatoPersoneModel.getInstace().resetSearch();
		ImpiegatoPersoneModel.getInstace().resetVisualizzazione();
		view.showCustomDialog(new Dimension(500,350), "ricercaCorrentistaDialog", "Ricerca correntista",
				(Component) ricercaPC.getView(),
				new String[] {"Annulla", "Seleziona"},
				new String[] {"Annulla", "Seleziona"});
	}
	
	/**
	 * Gestisce la pressione del pulsante di annullamento ricerca
	 * della finestra di ricerca correntista
	 */
	public void ricercaCorrentistaDialogAnnullaPressed() {
		ImpiegatoPersoneModel.getInstace().resetSearch();
		ImpiegatoPersoneModel.getInstace().resetVisualizzazione();
		view.hideCustomDialog();
	}
	/**
	 * Gestisce la pressione del pulsante di conferma selezione
	 * della finestra di ricerca correntista
	 */
	public void ricercaCorrentistaDialogSelezionaPressed() {
		if (ImpiegatoPersoneModel.getInstace().getPersona() != null ) {
			try {
				ImpiegatoContiModel cm = ImpiegatoContiModel.getInstace();
				IPersona p = ImpiegatoPersoneModel.getInstace().getPersona();
				cm.resetAll();
				cm.doSearchByIdPersona(p.getIdPersona());
				setContent(dettagliCorrentistaC.getView());
				view.hideCustomDialog();
			} catch ( Exception e) {
				ExceptionHandlerManager.process(e);
			}
		} else {
			ExceptionHandlerManager.process(new IllegalArgumentException("Nessun cliente selezionato"));
		}
	}

	/**
	 * Visualizza la finestra di ricerca correntista
	 * per l'aggiunta di un nuovo conto
	 */
	public void newContoRicercaCorrentista() {
		view.showCustomDialog(new Dimension(500,350), "newContoRicercaDialog", "Ricerca correntista",
				(Component) ricercaPC.getView(),
				new String[] {"Annulla", "Seleziona"},
				new String[] {"Annulla", "Seleziona"});
	}
	
	/**
	 * Gestisce la pressione del pulsante di conferma selezione
	 * della finesta di ricerca correntista visualizzata da
	 * {@link #newContoRicercaCorrentista()}
	 */
	public void newContoRicercaDialogSelezionaPressed() {
		if ( ImpiegatoPersoneModel.getInstace().getPersona() != null ) {
			ImpiegatoContiModel.getInstace().setNewIntestatario(ImpiegatoPersoneModel.getInstace().getPersona());
			view.hideCustomDialog();
		} else {
			ExceptionHandlerManager.process(new IllegalArgumentException("Nessun cliente selezionato"));
		}
	}

	/**
	 * Gestisce la pressione del pulsante di annullamento
	 * della finesta di ricerca correntista visualizzata da
	 * {@link #newContoRicercaCorrentista()}
	 */
	public void newContoRicercaDialogAnnullaPressed() {
		view.hideCustomDialog();
	}
	
	/**
	 * Visualizza la finestra per l'aggiunta di un nuovo correntista
	 */
	public void newContoAggiungiCorrentista() {
		view.showCustomDialog(new Dimension(500,250), "newContoAggiungiCorrentista", "Nuovo correntista", 
				(Component) addCorrentistaC.getView(),
				new String[] {"Annulla", "Conferma"},
				new String[] {"Annulla", "Conferma"});
	}

	/**
	 * Annulla l'aggiunta di un nuovo correntista
	 * nascondendo la finestra visualizzata da {@link #newContoAggiungiCorrentista()}
	 */
	public void newContoAggiungiCorrentistaAnnullaPressed() {
		view.hideCustomDialog();
	}
	
	/**
	 * Aggiunge il nuovo correntista con i dati inseriti nella finestra
	 * visualizzata da {@link #newContoAggiungiCorrentista()}
	 * e seleziona il correntista aggiunto come intestatario del nuovo
	 * conto
	 */
	public void newContoAggiungiCorrentistaConfermaPressed() {
		try {
			addCorrentistaC.save();
			ImpiegatoContiModel.getInstace().setNewIntestatario(ImpiegatoPersoneModel.getInstace().getPersona());
			view.hideCustomDialog();
		} catch ( Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}	
	
	/**
	 * Gestisce l'evento del click sul menu a tendina Help->Guida in linea
	 * della finestra principale
	 */
	public void itemHelpGuidaSelected() {
		Desktop desk = Desktop.getDesktop();
        File help = new File("help/inbankimpiegato.chm");
        try {
            desk.open(help);
        } catch (IOException ex) {
            ExceptionHandlerManager.process(new Exception("File della guida non trovato"));
        }	
	}
	
	
	/**
	 * Visualizza la finestra dei dettagli intestatario
	 * usando la vista associata a {@link ImpiegatoDettagliCorrentistaController}
	 */
	public void dettagliIntestatarioConto() {
		try {
			ImpiegatoContiModel cm = ImpiegatoContiModel.getInstace();
			IPersona p = cm.getConto().getIntestatario();
			ImpiegatoPersoneModel.getInstace().setPersona(p);
			cm.resetAll();
			cm.doSearchByIdPersona(p.getIdPersona());
			setContent(dettagliCorrentistaC.getView());
			//view.hideCustomDialog();
		} catch ( Exception e) {
			ExceptionHandlerManager.process(e);
		}
	}
	
	/**
	 * Gestisce la pressione del tasto Enter nel campo
	 * username della finestra di login spostando il focus all'elemento successivo
	 * @see YKeyBasedEventTextField
	 */
	public void username10KeyReleased() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
	}

	/**
	 * Gestisce la pressione del tasto Enter nel campo
	 * password della finestra di login eseguendo il login
	 * @see YKeyBasedEventTextField
	 */
	public void password10KeyReleased() {
		copyToModel("password");
		//copyToModel("username");
		try {
			new YWorker().execute(new YWorkerCommand() {
				@Override
				public Object execute() throws Exception {
					model.connect();
					view.setVisible(true);
					view.showLoginDialog(false);
					return null;
				}
			}, "Attendere prego...");
			
			ImpiegatoServiceDelegate.setLogoutAction(new ILogoutAction() {
				@Override
				public void execute() {
					JOptionPane.showMessageDialog(view, "Il server ha richiesto la disconnessione del Client", "Disconnessione", JOptionPane.WARNING_MESSAGE);
					System.exit(-1);
				}
			});
			defaultView();
			sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CLIENT_CONNECTED));
		} catch (Exception e) {
			ExceptionHandlerManager.process(e);
			model.cleanAll();
		}
	}
	
	/**
	 * Gestisce la pressione del tasto login della finestra
	 * di login invocando {@link #password10KeyReleased()}
	 */
	public void buttonConfermaLoginPressed() {
		copyToModel("username");
		copyToModel("password");
		password10KeyReleased();
	}
	
	/**
	 * Esce dall'applicazione invocando
	 * {@link #itemApplicazioneEsciSelected()};
	 */
	public void buttonAnnullaLoginPressed() {
		itemApplicazioneEsciSelected();
	}
	
}
