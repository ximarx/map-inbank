package gccb.cliente.model;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.cliente.net.ClienteServiceDelegate;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;

/**
 * Modello relativo al pannello bonifico
 * @author Francesco Capozzo
 *
 */
public class ClienteBonificoModel extends YModel {

	private int contoDestinazione;
	private float importo;

	
	/**
	 * Esegue un bonifico con le informazioni indicate in
	 * {@link #getContoDestinazione()} e {@link #getImporto()}
	 * @throws GccbNetException in caso di errore del server
	 * @throws IllegalArgumentException se i dati indicati non sono validi
	 * @throws ServerNonDisponibileException in caso di errore di connessione
	 */
	public void doBonifico() throws ServerNonDisponibileException, IllegalArgumentException, GccbNetException {
		ClienteServiceDelegate.doBonifico(contoDestinazione, importo);
	}
	
	/**
	 * Ritorna l'id del conto di destinazione
	 * @return il conto di destinazione
	 */
	public int getContoDestinazione() {
		return contoDestinazione;
	}
	/**
	 * Setta il conto di destinazione
	 * @param contoDestinazione l'id del conto di destinazione
	 */
	public void setContoDestinazione(int contoDestinazione) {
		this.contoDestinazione = contoDestinazione;
		notifyObservers("contoDestinazione");
	}
	/**
	 * Indica l'importo da trasferire
	 * @return l'importo selezionato
	 */
	public Float getImporto() {
		return importo;
	}
	/**
	 * Imposta un nuovo importo da trasferire
	 * @param importo il nuovo valore
	 */
	public void setImporto(Float importo) {
		this.importo = importo;
		notifyObservers("importo");
	}
}
