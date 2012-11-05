package gccb.impiegato;

import javax.swing.JOptionPane;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import fi.mmm.yhteinen.swing.core.YIComponent;
import fi.mmm.yhteinen.swing.core.YModelChangeEvent;
import fi.mmm.yhteinen.swing.core.worker.YWorker;
import fi.mmm.yhteinen.swing.core.worker.YWorkerCommand;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.impiegato.gui.ImpiegatoDettagliContoView;
import gccb.impiegato.model.ImpiegatoContiModel;

/**
 * Controller per la visualizzazione dei dettagli associati ad
 * un conto specifico
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoDettagliContoController extends YController {

	private ImpiegatoDettagliContoView view = new ImpiegatoDettagliContoView();
	//private ImpiegatoDettagliContoModel model;// = new ImpiegatoDettagliContoModel();
	private ImpiegatoContiModel model;
	{
		model = ImpiegatoContiModel.getInstace();
	}
	
	/**
	 * Inizializza il controller impostando la vista e il modello
	 */
	public ImpiegatoDettagliContoController() {
		setUpMVC(model, view);
	}
	
	/**
	 * Imposta i widget nella vista
	 * @param leftWidget il widget da inserire nel pannello laterale
	 * @param bottomWidget il widget da inserire nel pannello in basso
	 */
	public void setWidget(YIComponent leftWidget, YIComponent bottomWidget ) {
		view.setWidgets(leftWidget, bottomWidget);
	}
	
	/**
	 * Esegue la chiusura del conto selezionato nel modello
	 */
	public void buttonChiudiContoPressed() {
		
		Object[] opzioni = { "Si", "No"};
		int n = JOptionPane.showOptionDialog(view,
			    "L'operazione di chiusura e' irreversibile. Tutti i fondi andranno persi.\n"
			    + "Continuare?",
			    "Conferma chiusura conto",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    opzioni,
			    opzioni[1]);
		
		if ( n == 0 ) {
			YWorker yw = new YWorker();
			try {
				yw.execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						model.doChiudiConto();
						sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CONTO_CLOSED));
						return null;
					}
				}, "Attendere prego...");
			} catch (Exception e) {
				yw.interrupt();
				ExceptionHandlerManager.process(e);
			}
		}
	}
	
	/**
	 * Visualizza la finestra di modifica fido
	 */
	public void buttonModificaFidoPressed() {
		try {
			Float newFido = Float.valueOf(JOptionPane.showInputDialog(
	                view,
	                "Nuovo fido:",
	                "Richiesta nuovo fido",
	                JOptionPane.PLAIN_MESSAGE));
			model.setContoFido(newFido);
			YWorker yw = new YWorker();
			try {
				yw.execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						model.doModificaConto();
						return null;
					}
				});
				sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CONTO_CHANGED));
			} catch (Exception e) {
				yw.interrupt();
				ExceptionHandlerManager.process(e);
			}
		} catch (NumberFormatException cce) {
			JOptionPane.showMessageDialog(view,
				    "Il valore inserito non è valido!",
				    "Errore",
				    JOptionPane.ERROR_MESSAGE);
			buttonModificaFidoPressed();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#modelChanged(fi.mmm.yhteinen.swing.core.YModelChangeEvent)
	 */
	@Override
	public void modelChanged(YModelChangeEvent event) {
		super.modelChanged(event);
		try {
			view.setFidoModificabile(model.getContoTipo().equals("CON FIDO"));
		} catch (Exception e) {
			view.setFidoModificabile(false);
		}
	}
	
	/**
	 * Esegue un prelievo sul conto selezionato
	 */
	public void buttonPrelievoPressed() {
		try {
			Float newPrelievo = Float.valueOf(JOptionPane.showInputDialog(
	                view,
	                "Importo da prelevare:",
	                "Nuovo Prelievo",
	                JOptionPane.PLAIN_MESSAGE));
			model.setNewPrelievo(newPrelievo);
			YWorker yw = new YWorker();
			try {
				yw.execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						model.doPrelievo();
						model.aggiornaConto();
						return null;
					}
				});
				sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CONTO_CHANGED));
			} catch (Exception e) {
				yw.interrupt();
				ExceptionHandlerManager.process(e);
			}
		} catch (NumberFormatException cce) {
			JOptionPane.showMessageDialog(view,
				    "Il valore inserito non è valido!",
				    "Errore",
				    JOptionPane.ERROR_MESSAGE);
			buttonPrelievoPressed();
		}
	}	
	
	/**
	 * Esegue un versamento sul conto selezionato nel modello
	 */
	public void buttonVersamentoPressed() {
		try {
			Float newVersamento = Float.valueOf(JOptionPane.showInputDialog(
	                view,
	                "Importo da versare:",
	                "Nuovo Versamento",
	                JOptionPane.PLAIN_MESSAGE));
			model.setNewVersamento(newVersamento);
			YWorker yw = new YWorker();
			try {
				yw.execute(new YWorkerCommand() {
					@Override
					public Object execute() throws Exception {
						model.doVersamento();
						model.aggiornaConto();
						return null;
					}
				});
				sendApplicationEvent(new YApplicationEvent(InBankImpiegato.CONTO_CHANGED));
			} catch (Exception e) {
				yw.interrupt();
				ExceptionHandlerManager.process(e);
			}
		} catch (NumberFormatException cce) {
			JOptionPane.showMessageDialog(view,
				    "Il valore inserito non è valido!",
				    "Errore",
				    JOptionPane.ERROR_MESSAGE);
			buttonVersamentoPressed();
		}
	}
	
	/**
	 * Visualizza i dettagli dell'intestatario del
	 * conto selezionato invocando la funzione
	 * {@link ImpiegatoFrameController#dettagliIntestatarioConto()}
	 */
	public void buttonDettagliIntestatarioPressed() {
		try {
			getParent().invokeMethodIfFound("dettagliIntestatarioConto");
		} catch (NullPointerException npe) {}
	}
}
