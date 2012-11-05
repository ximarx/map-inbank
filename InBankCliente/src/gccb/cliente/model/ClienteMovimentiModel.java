package gccb.cliente.model;

import java.util.List;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.ITransazione;
import gccb.cliente.net.ClienteServiceDelegate;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;

/**
 * Modello relativo al pannello movimenti
 * @author Francesco Capozzo
 *
 */
public class ClienteMovimentiModel extends YModel {
	
	private List<ITransazione> ultimiMovimenti;
	
	/**
	 * Recupera dal server la lista di movimenti
	 * @throws GccbNetException in caso di errore del server
	 * @throws ServerNonDisponibileException in caso di errori di collegamento
	 * @throws IllegalArgumentException se i dati indicati non sono validi
	 */
	public void fetchMovimenti() throws IllegalArgumentException, ServerNonDisponibileException, GccbNetException {
		setUltimiMovimenti(ClienteServiceDelegate.findUltimiMovimenti());
		notifyObservers("ultimiMovimenti");
	}
	
	/**
	 * Ritorna la lista di movimenti
	 * @return la lista di movimenti
	 */
	public List<ITransazione> getUltimiMovimenti() {
		return ultimiMovimenti;
	}
	
	/**
	 * Imposta una nuova lista di movimenti
	 * @param ultimiMovimenti
	 */
	public void setUltimiMovimenti(List<ITransazione> ultimiMovimenti) {
		this.ultimiMovimenti = ultimiMovimenti; 
	}
	
}
