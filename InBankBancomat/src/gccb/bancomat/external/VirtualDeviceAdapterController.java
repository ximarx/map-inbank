package gccb.bancomat.external;

import fi.mmm.yhteinen.swing.core.YController;

/**
 * Dispositivo virtuale per l'interazione tramite interfaccia grafica
 * con un dispositivo esterno fittizio.
 * Permette di simulare le interazioni con il dispositivo esterno
 * tramite una interfaccia grafica e l'uso di mouse e tastiera
 * @author Francesco Capozzo
 *
 */
public class VirtualDeviceAdapterController extends YController implements IExternalDevice {

	private VirtualDeviceAdapterView view = new VirtualDeviceAdapterView();
	private BancomatExternalDeviceController parentController;
	private boolean isActive = false;
	
	/**
	 * Possibili stati assumibili dal dispositivo
	 * @author Francesco Capozzo
	 *
	 */
	public enum STATI {
		IN_ATTESA, ATTIVO, DISATTIVATO, IN_STAMPA, EMISSIONE_BANCONOTE, RILASCIO_CARTA, RITIRO_CARTA
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#setDriverController(gccb.bancomat.external.BancomatExternalDeviceController)
	 */
	public void setDriverController(BancomatExternalDeviceController controller) {
		parentController = controller;
	}
	
	/**
	 * Inizializza il controller e visualizza l'interfaccia grafica
	 */
	public VirtualDeviceAdapterController() {
		setUpMVC(null, view);
	}
	
	/**
	 * Simula l'inserimento della carta bancomat e inoltra a controller
	 * del dispositivo esterno il codice della carta bancomat
	 * inserita
	 */
	public void buttonSimulaPressed() {
		parentController.cartaInserted(String.valueOf(view.getCodiceCarta().getPassword()));
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#attiva()
	 */
	@Override
	public void attiva() {
		isActive = true;
		view.attiva();
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#disattiva()
	 */
	@Override
	public void disattiva() {
		isActive = false;
		view.disattiva();
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#emettiBanconote(float)
	 */
	@Override
	public void emettiBanconote(float importo) throws ExternalDeviceException {
		view.setTestoStato(STATI.EMISSIONE_BANCONOTE);
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#inizializza()
	 */
	@Override
	public void inizializza() {
		view.pulisci();
		view.setVisible(true);
		view.setTestoStato(STATI.IN_ATTESA);
		attiva();
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#isAttivo()
	 */
	@Override
	public boolean isAttivo() {
		return isActive;
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#rilasciaCarta()
	 */
	@Override
	public void rilasciaCarta() throws ExternalDeviceException {
		view.setTestoStato(STATI.RILASCIO_CARTA);
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#stampaScontrino(java.lang.String)
	 */
	@Override
	public void stampaScontrino(String testo) throws ExternalDeviceException {
		view.setTestoStato(STATI.IN_STAMPA);
		view.setStampanteVirtuale(testo);
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.bancomat.external.IExternalDevice#trattieniCarta()
	 */
	@Override
	public void trattieniCarta() throws ExternalDeviceException {
		view.setTestoStato(STATI.RITIRO_CARTA);
	}
}
