package gccb.bancomat.external;

import fi.mmm.yhteinen.swing.core.YApplicationEvent;
import fi.mmm.yhteinen.swing.core.YController;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.bancomat.BancomatFrameController;
import gccb.bancomat.InBankBancomat;
import gccb.bancomat.model.BancomatFrameModel;
import gccb.exception.handler.ExceptionHandlerManager;
import gccb.settings.SettingsManager;

/**
 * Controller per il terminale bancomat esterno
 * Interagisce con il {@link BancomatFrameController} per gestire
 * l'interazione fra l'utente e il terminale esterno per l'inserimento
 * e l'emissione della carta bancomat, per l'emissione delle banconote
 * e per la stampa dell'estratto conto
 * 
 * Il driver del dispositivo esterno può essere impostato tramite
 * la proprietà external.device.driver del {@link SettingsManager}
 * standard.
 * Se il driver indicato non risulterà valido (o non sarà trovato)
 * verrà utilizzato il driver standard che permette di simulare
 * l'interazione con un terminale virtuale tramite una finestra
 *  
 * @author Francesco Capozzo
 */
public class BancomatExternalDeviceController extends YController {

	private BancomatFrameModel model;
	private IExternalDevice externalDevice;
	{
		try {
			externalDevice = (IExternalDevice) Class.forName(
					SettingsManager.getDefaultInstance().get("external.device.driver", "gccb.bancomat.external.VirtualDeviceAdapterController").trim()
					).newInstance();
		} catch (Exception e) {
			ExceptionHandlerManager.process(new ExternalDeviceException("Il driver per il dispositivo esterno non e' valido"));
			externalDevice = new VirtualDeviceAdapterController();
		}
		externalDevice.setDriverController(this);
		
	}
	
	/**
	 * Inizializza il controller 
	 * memorizzando un riferimento al 
	 * modello del controller principale
	 * @param model il modello del controller principale
	 */
	public BancomatExternalDeviceController(BancomatFrameModel model) {
		this.model = model;
		register(InBankBancomat.CLIENT_DISCONNECTED);
		register(InBankBancomat.CARD_IN);
		register(InBankBancomat.READY);
		externalDevice.inizializza();
	}
	
	/**
	 * Gestisce l'evento di inserimento carta propagato dal
	 * terminale esterno
	 * @param bancomatCode l'identificativo della
	 * carta bancomat inserita (letta tramite un lettore
	 * di bande magnetiche)
	 */
	public void cartaInserted(String bancomatCode) {
		model.setUsername(bancomatCode);
		sendApplicationEvent(new YApplicationEvent(InBankBancomat.CARD_IN));
	}

	/*
	 * (non-Javadoc)
	 * @see fi.mmm.yhteinen.swing.core.YController#receivedApplicationEvent(fi.mmm.yhteinen.swing.core.YApplicationEvent)
	 */
	public void receivedApplicationEvent(YApplicationEvent event) {
		if ( event.getName().equals(InBankBancomat.CLIENT_DISCONNECTED) ) {
			try {
				externalDevice.rilasciaCarta();
				sendApplicationEvent(new YApplicationEvent(InBankBancomat.CARD_OUT));
			} catch (ExternalDeviceException e) {
				ExceptionHandlerManager.process(e);
			}
		} else if ( event.getName().equals(InBankBancomat.CARD_IN) ) {
			externalDevice.disattiva();
		} else if ( event.getName().equals(InBankBancomat.READY) ) {
			externalDevice.inizializza();
		}
	}
	
	/**
	 * Invia al terminale esterno il comando di effettuare
	 * la stampa dell'estratto conto
	 */
	public void stampaEstrattoConto() {
		IContoCorrenteBancario c = model.getConto();
		StringBuffer output = new StringBuffer("Numero conto: " + c.getIdConto() + "\n" +
			"Intestatario: " + c.getIntestatario() + "\n" +
			"Saldo attuale: " + c.getSaldo() + "\n" +
			"Numero conto: " + c.getIdConto() + "\n");
		if ( c instanceof IContoCorrenteConFido ) {
			output.append("Massimo scoperto: " + ((IContoCorrenteConFido)c).getFido() + "\n");
		}
		output.append("Ultimo movimento: " + c.getUltimoMovimento() + "\n");
		try {
			externalDevice.stampaScontrino(output.toString());
		} catch (ExternalDeviceException e) {
			ExceptionHandlerManager.process(e);
		}
	}
	
	/**
	 * Ordina al terminale esterno l'emissione delle banconote
	 * @param importo l'importo da emettere
	 */
	public void emettiBanconote(float importo) {
		try {
			externalDevice.emettiBanconote(importo);
		} catch ( ExternalDeviceException e) {
			ExceptionHandlerManager.process(e);
		}
	}
}
