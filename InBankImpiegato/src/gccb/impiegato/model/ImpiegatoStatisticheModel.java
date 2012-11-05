package gccb.impiegato.model;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.impiegato.net.ImpiegatoServiceDelegate;

/**
 * Modello per la gestione delle statistiche
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoStatisticheModel extends YModel {

	private int numeroConti;
	private int numeroClienti;
	private int numeroFidi;
	private float percentualeFidi;
	private int numeroBancomat;
	private int numeroImpiegati;
	
	private String serverAddress;
	private int serverPort;
	private String serviceName;
	
	/**
	 * Aggiorna le statistiche dal server
	 * @throws GccbNetException in caso di errori del server
	 * @throws DatiNonTrovatiException se i dati non vengono trovati
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public void updateStatistiche() throws DatiNonTrovatiException, GccbNetException, ServerNonDisponibileException {
		Object[] stats = ImpiegatoServiceDelegate.getStatistiche();
		setNumeroConti((Integer) stats[0]);
		setNumeroClienti((Integer) stats[1]);
		setNumeroFidi((Integer) stats[2]);
		setPercentualeFidi((Float) stats[3]);
		setNumeroBancomat((Integer) stats[4]);
		setNumeroImpiegati((Integer) stats[5]);
		notifyObservers("numeroConti");
		notifyObservers("numeroClienti");
		notifyObservers("numeroFidi");
		notifyObservers("percentualeFidi");
		notifyObservers("numeroBancomat");
		notifyObservers("numeroImpiegati");
	}
	
	/**
	 * Aggiorna le preferenze di connessione
	 */
	public void updatePreferences() {
		setServiceName(ImpiegatoServiceDelegate.getServiceName());
		setServerAddress(ImpiegatoServiceDelegate.getServerAddress());
		setServerPort(ImpiegatoServiceDelegate.getServerPort());
		notifyObservers("serviceName");
		notifyObservers("serverPort");
		notifyObservers("serverAddress");
	}
	
	/**
	 * Indica il numero dei conti presenti
	 * @return il numero
	 */
	public int getNumeroConti() {
		return numeroConti;
	}
	/**
	 * Imposta il numero di conti presenti
	 * @param numeroConti il nuovo numero di conti
	 */
	public void setNumeroConti(int numeroConti) {
		this.numeroConti = numeroConti;
	}
	/**
	 * Indica il numero di correntisti presenti
	 * @return il numero di correntisti
	 */
	public int getNumeroClienti() {
		return numeroClienti;
	}
	/**
	 * Imposta il numero di correntisti presenti
	 * @param numeroClienti il nuovo numero di correntisti
	 */
	public void setNumeroClienti(int numeroClienti) {
		this.numeroClienti = numeroClienti;
	}
	/**
	 * Indica il numero di fidi
	 * @return il numero di fidi
	 */
	public int getNumeroFidi() {
		return numeroFidi;
	}
	/**
	 * Imposta il numero di fidi
	 * @param numeroFidi il nuovo numero di fidi
	 */
	public void setNumeroFidi(int numeroFidi) {
		this.numeroFidi = numeroFidi;
	}
	/**
	 * Indica la percentuale di conti con fido sul numero totale di conti
	 * @return la percentuale di conti con fido
	 */
	public float getPercentualeFidi() {
		return percentualeFidi;
	}
	/**
	 * Imposta la nuova percentuale di conti con fido
	 * @param percentualeFidi la nuova percentuale
	 */
	public void setPercentualeFidi(float percentualeFidi) {
		this.percentualeFidi = percentualeFidi;
	}
	/**
	 * Indica il numero di bancomat registrati
	 * @return il numero di bancomat
	 */
	public int getNumeroBancomat() {
		return numeroBancomat;
	}
	/**
	 * Imposta il nuovo numero di bancomat attivi
	 * @param numeroBancomat il nuovo numero di bancomat
	 */
	public void setNumeroBancomat(int numeroBancomat) {
		this.numeroBancomat = numeroBancomat;
	}
	/**
	 * Indica il numero di impiegati presenti
	 * @return il numero di impiegati
	 */
	public int getNumeroImpiegati() {
		return numeroImpiegati;
	}
	/**
	 * Imposta il nuovo numero di impiegati
	 * @param numeroImpiegati il nuovo numero
	 */
	public void setNumeroImpiegati(int numeroImpiegati) {
		this.numeroImpiegati = numeroImpiegati;
	}
	/**
	 * Indica l'indirizzo del server
	 * @return l'indirizzo
	 */
	public String getServerAddress() {
		return serverAddress;
	}
	/**
	 * Imposta l'indirizzo del server visualizzato
	 * @param serverAddress l'indirizzo del server
	 */
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	/**
	 * Indica la porta utilizzata dal server
	 * @return la porta
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Imposta la porta da visualizzare
	 * @param serverPort la nuova porta da visualizzare
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * Nome del servizio verso cui il client è collegato
	 * @return il nome del servizio
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Imposta il nome del servizio visualizzato
	 * @param serviceName il nuovo nome
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
