package gccb.bancomat.model;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.bancomat.BancomatFrameController;
import gccb.bancomat.net.BancomatServiceDelegate;
import gccb.exception.OltreLimitePrelievoException;
import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;

/**
 * Modello utilizzato da {@link BancomatFrameController}
 * per memorizzare i valori inseriti nell'interfaccia ed eseguire le operazioni
 * 
 * @author Francesco Capozzo
 *
 */
public class BancomatFrameModel extends YModel {

	private String username;
	private String password;
	
	private float importo;
	
	private IContoCorrenteBancario conto;
	private String tipoConto;
	private IBancomat bancomat;
	
	/**
	 * Resetta tutti i campi del modello
	 */
	public void resetAll() {
		setUsername(null);
		setPassword(null);
		setConto(null);
		setImporto(0);
		notifyObservers();
	}

	/**
	 * Esegue la connessione al server con le informazioni
	 * ottenute da {@link #getUsername()} e {@link #getPassword()}
	 * come credenziali
	 * @throws GccbNetException in caso di errore generico 
	 * @throws CredenzialiNonValideException se le credenziali fornite non sono valide
	 */
	public void connect() throws CredenzialiNonValideException, GccbNetException {
		BancomatServiceDelegate.connect(username, password);
	}
	
	/**
	 * Esegue un prelievo dell'importo indicato da
	 * {@link #getImporto()}
	 * @throws OltreLimitePrelievoException Se l'importo indicato non e' prelevabile
	 * @throws GccbNetException in caso di errore generico
	 * @throws ServerNonDisponibileException se il server non e' disponibile
	 */
	public void doPrelievo() throws OltreLimitePrelievoException, ServerNonDisponibileException, GccbNetException {
		if ( bancomat.getMassimoPrelevabile() >= importo &&
				bancomat.getContoAssociato().isPrelevabile(importo) ) {
			BancomatServiceDelegate.doPrelievo(importo);
			updateConto();
		} else {
			throw new OltreLimitePrelievoException("L'importo indicato non e' prelevabile");
		}
	}
	
	/**
	 * Aggiorna i dati relativi al conto tramite
	 * {@link #setConto(IContoCorrenteBancario)}
	 * @throws GccbNetException in caso di errore generico 
	 * @throws ServerNonDisponibileException se il server non e' disponibile
	 */
	public void updateConto() throws ServerNonDisponibileException, GccbNetException {
		setBancomat(BancomatServiceDelegate.getBancomat());
		setConto(bancomat.getContoAssociato());
		notifyObservers("conto");
		notifyObservers("tipoConto");
		notifyObservers("bancomat");
	}
	
	/**
	 * Imposta il nuovo conto di riferimento
	 * @param conto il nuovo conto
	 */
	public void setConto(IContoCorrenteBancario conto) {
		this.conto = conto;
		setTipoConto((conto instanceof IContoCorrenteConFido ? "CON FIDO" : "NORMALE"));
	}
	
	/**
	 * Restituisce il conto associato al bancomat inserito
	 * @return il conto di riferimento
	 */
	public IContoCorrenteBancario getConto() {
		return conto;
	}
	
	/**
	 * Imposta il tipo di conto relativo al bancomat di riferimento
	 * @param tipoConto tipo di conto
	 */
	public void setTipoConto(String tipoConto) {
		this.tipoConto = tipoConto;
	}
	
	/**
	 * Identifica il conto di riferimento
	 * @return il tipo del conto
	 */
	public String getTipoConto() {
		return tipoConto;
	}
	
	/**
	 * Indica lo codice del bancomat inserito
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Imposta il codice del bancomat inserito
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Recupera  la password corrispondente al bancomat inserito
	 * @return il codice segreto inserito
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Imposta la password associata al bancomat
	 * @param password la nuova password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Indica l'importo indicato per il prelievo
	 * @return l'importo
	 */
	public float getImporto() {
		return importo;
	}

	/**
	 * Imposta il nuovo valore da prelevare
	 * @param importo il nuovo importo
	 */
	public void setImporto(float importo) {
		this.importo = importo;
	}

	/**
	 * Recupera le informazioni relative al bancomat inserito
	 * @return una istanza di {@link IBancomat} rappresentante le informazioni trovate
	 */
	public IBancomat getBancomat() {
		return bancomat;
	}

	/**
	 * Imposta le informazioni relative al bancomat inserito
	 * @param bancomat il bancomat inserito
	 */
	public void setBancomat(IBancomat bancomat) {
		this.bancomat = bancomat;
	}
}
