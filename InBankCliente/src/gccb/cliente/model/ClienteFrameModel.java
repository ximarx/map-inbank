package gccb.cliente.model;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.cliente.net.ClienteServiceDelegate;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;

/**
 * Modello relativo alla finestra principale e ai dettagli conto
 * @author Francesco Capozzo
 *
 */
public class ClienteFrameModel extends YModel {

	private IContoCorrenteBancario conto;
	private String tipoContoCorrente;

	/**
	 * Aggiorna le informazioni relative al conto
	 * @throws GccbNetException in caso di errore nel server
	 * @throws ServerNonDisponibileException se il server nn e' disponibile
	 * @throws DatiNonTrovatiException se il conto indicato non e' stato trovato
	 */
	public void updateConto() throws DatiNonTrovatiException, ServerNonDisponibileException, GccbNetException {
		setConto(ClienteServiceDelegate.findDatiConto());
		notifyObservers("conto");
		notifyObservers("tipoContoCorrente");
	}
	
	/**
	 * Indica il conto relativo alle informazioni di autenticazione
	 * @return una istanza di {@link IContoCorrenteBancario} con le informazioni sul conto
	 */
	public IContoCorrenteBancario getConto() {
		return conto;
	}
	
	/**
	 * Imposta le nuove informazioni sul conto
	 * @param conto una istanza di {@link IContoCorrenteBancario} cone le informazioni sul conto
	 */
	public void setConto(IContoCorrenteBancario conto) {
		this.conto = conto;
		if (conto instanceof IContoCorrenteConFido ) { 
			setTipoContoCorrente("Con fido");
		} else {
			setTipoContoCorrente("Normale"); 
		}
	}

	/**
	 * Indica il tipo di conto selezionato
	 * @return il tipo
	 */
	public String getTipoContoCorrente() {
		return tipoContoCorrente;
	}

	/**
	 * Imposta il tipo del conto selezionato
	 * @param tipoContoCorrente il nuovo tipo di conto
	 */
	public void setTipoContoCorrente(String tipoContoCorrente) {
		this.tipoContoCorrente = tipoContoCorrente;
	}
}
