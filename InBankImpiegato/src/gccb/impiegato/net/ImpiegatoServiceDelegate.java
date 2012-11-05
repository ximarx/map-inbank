package gccb.impiegato.net;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.IPersona;
import gccb.ITransazione;
import gccb.Persona;
import gccb.database.ImpiegatoDB;
import gccb.exception.OltreLimitePrelievoException;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.CredenzialiNonValideException;
import gccb.exception.net.GccbNetException;
import gccb.exception.net.ServerNonDisponibileException;
import gccb.net.rmi.ILogoutAction;
import gccb.net.rmi.RMIClientFactory;
import gccb.net.rmi.RMIClientImpiegato;
import gccb.net.rmi.services.IImpiegatoService;
import gccb.settings.SettingsManager;

/**
 * Service delegete per il client Impiegato. Permette di accedere
 * in maniera trasparente dal tipo di connessione utilizzata
 * alle informazioni contenute nel server
 * @author Francesco Capozzo
 *
 */
public class ImpiegatoServiceDelegate {

	static String serverAddress;
	static int serverPort;
	static String serviceName;
	static {
		serverAddress = SettingsManager.getDefaultInstance().get("server.host.address");
		try {
			serverPort = Integer.valueOf(SettingsManager.getDefaultInstance().get("server.host.port"));
		} catch (NumberFormatException nme) {
			serverPort = 1099;
		}
		serviceName = SettingsManager.getDefaultInstance().get("server.service.name");
	}
	
	static private IImpiegatoService rmiService;
	static private RMIClientImpiegato rmiClient;
	
	/**
	 * L'istanziazione della classe viene impedita
	 */
	private ImpiegatoServiceDelegate() {}
	
	/**
	 * Crea la connessione al server 
	 * @param username username dell'impiegato
	 * @param password password dell'impiegato
	 * @throws GccbNetException in caso di errori di connessione
	 * @throws CredenzialiNonValideException se le credenziali inserite non sono valide
	 */
	public static void connect(String username, String password) throws GccbNetException, CredenzialiNonValideException {
		if ( rmiClient == null )
			rmiClient = RMIClientFactory.getClientImpiegato(serverAddress, serverPort, serviceName, username, password);
		rmiService = (IImpiegatoService) rmiClient.getService("gccb.net.rmi.services.ImpiegatoService");
	}
	
	/**
	 * Imposta l'azione da eseguire nel caso in cui il server
	 * richieda una disconnessione del client
	 * @param la la nuova vazione
	 */
	public static void setLogoutAction(ILogoutAction la) {
		if ( rmiClient != null ) {
			rmiClient.setLogoutAction(la);
		}
	}
	
	/**
	 * Controlla che la connessione la server sia attiva
	 * @return l'esito della connessione
	 */
	public static boolean isConnected() {
		return (rmiService != null && rmiClient != null);
	}
	/**
	 * Esegue la disconnessione dal server
	 */
	public static void disconnect() {
		try {
			rmiService = null;
			rmiClient.disconnetti();
			rmiClient = null;
		} catch (NullPointerException npe) {}
	}
	/**
	 * Recupera le informazioni del conto dal server
	 * @param idConto l'id del conto da recuperare
	 * @return una istanza di {@link IContoCorrenteBancario} con le informazioni trovate
	 * @throws GccbNetException In caso di errori interni del server
	 * @throws DatiNonTrovatiException se il conto non e' stato trovato
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public static IContoCorrenteBancario getConto(int idConto) throws DatiNonTrovatiException, GccbNetException {
		try {
			return rmiService.getConto(idConto, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Recupera dal server le statistiche
	 * @return un array di oggetti con le statistiche
	 * Per informazioni sulla composizione dell'array cercare in {@link ImpiegatoDB#getStatistiche()} 
	 * @throws GccbNetException in caso di errore del server
	 * @throws DatiNonTrovatiException se i dati non sono stati trovati
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public static Object[] getStatistiche() throws DatiNonTrovatiException, GccbNetException, ServerNonDisponibileException {
		try {
			return rmiService.getStatistiche(rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Ricerca un conto in base al suo id. Utilizza
	 * {@link #getConto(int)} per il ritrovamento della informazioni
	 * @param idConto l'id del conto da cercare
	 * @return una lista di conti (composta da 0 o 1 elemento)
	 * @throws GccbNetException in caso di errori interni del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 */
	public static List<IContoCorrenteBancario> search(int idConto) throws GccbNetException, ServerNonDisponibileException {
		try {
			return Arrays.asList(new IContoCorrenteBancario[] {rmiService.getConto(idConto, rmiClient)});
		} catch (DatiNonTrovatiException e) {
			return Arrays.asList(new IContoCorrenteBancario[0]);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Cerca un insieme di conti in base al nome e cognome dell'intestatario
	 * @param nome il nome
	 * @param cognome il cognome
	 * @param valoriSimili indica se inserire nei risultati anche risconti parziali
	 * @return una lista di conti
	 * @throws GccbNetException in caso di errori interni del server
	 * @throws ServerNonDisponibileException in caso di errori di connessioni
	 */
	public static List<IContoCorrenteBancario> search(String nome, String cognome, boolean valoriSimili) throws GccbNetException, ServerNonDisponibileException {
		try {
			return Arrays.asList(rmiService.searchConto(nome, cognome, valoriSimili, rmiClient));
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Ricerca i conti intestati ad una persona
	 * @param idPersona l'id dell'intestatario
	 * @return la lista dei conti trovati
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errore del server
	 */
	public static List<IContoCorrenteBancario> searchByIdPersona(int idPersona) throws ServerNonDisponibileException, GccbNetException {
		try {
			return Arrays.asList(rmiService.searchContoByPersona(idPersona, rmiClient));
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Ricerca i bancomat associati ad un conto
	 * @param idConto l'id del conto
	 * @return la lista dei bancomat trovati
	 * @throws GccbNetException in caso di errore del server
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws DatiNonTrovatiException se il conto non esiste
	 */
	public static List<IBancomat> getBancomats(int idConto) throws DatiNonTrovatiException, GccbNetException, ServerNonDisponibileException {
		try {
			return Arrays.asList(rmiService.getBancomatPerConto(idConto, rmiClient));
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Aggiunge un nuovo bancomat al conto indicato
	 * @param conto il conto (deve essere gia presente nel database)
	 * @param bancomat il nuovo bancomat da inserire
	 * @return una istanza di {@link IBancomat} corrispondente al bancomat appena inserito
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errore del server
	 */
	public static IBancomat addBancomat(IContoCorrenteBancario conto, IBancomat bancomat) throws ServerNonDisponibileException, GccbNetException {
		try {
			if ( bancomat.getChiave() == null || bancomat.getChiave().equals("") ) {
				throw new IllegalArgumentException("Pin non valido");
			}
			return rmiService.addBancomat(conto, bancomat, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Cancella il bancomat indicato
	 * @param bancomat il bancomat da rimuovere
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errori del server
	 */
	public static void delBancomat(IBancomat bancomat) throws ServerNonDisponibileException, GccbNetException{
		try {
			rmiService.rimuoviBancomat(bancomat, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Chiude il conto indicato
	 * @param conto il conto da chiudere
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errori interni del server
	 */
	public static void chiudiConto(IContoCorrenteBancario conto) throws ServerNonDisponibileException, GccbNetException {
		try {
			rmiService.chiudiConto(conto, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Modifica il fido del conto indicato
	 * @param conto il conto da modificate
	 * @param nuovoFido il valore del nuovo fido
	 * @return una istanza di {@link IContoCorrenteConFido} rappresentante il conto appena modificato
	 * @throws ServerNonDisponibileException in caso di errore
	 * @throws GccbNetException in caso di errori del server
	 * @throws DatiNonTrovatiException se il conto indicato non esiste
	 */
	public static IContoCorrenteConFido modificaConto(IContoCorrenteConFido conto, float nuovoFido) throws ServerNonDisponibileException, GccbNetException, DatiNonTrovatiException {
		try {
			return rmiService.modificaFido(conto, nuovoFido, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Aggiunge il nuovo conto indicato
	 * @param conto il conto da aggiungere
	 * @param password la password associata al conto
	 * @return una istanza di {@link IContoCorrenteBancario} rappresentante il conto appena aggiunto
	 * @throws ServerNonDisponibileException in caso di errore di connessione
	 * @throws GccbNetException inc aso di errore intenro del server
	 */
	public static IContoCorrenteBancario addConto(IContoCorrenteBancario conto, String password) throws ServerNonDisponibileException, GccbNetException {
		try {
			if ( conto.getIntestatario() == null || password == null || password.equals("") ) {
				throw new IllegalArgumentException("Dati specificati non validi");
			}
			return rmiService.addConto(conto, password, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Recupera la lista delle transazioni di un conto specifico
	 * @param idConto l'id del conto di cui cercare le transazioni
	 * @return la lista trovata
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errori del server
	 */
	public static List<ITransazione> getTransazioni(int idConto) throws GccbNetException, ServerNonDisponibileException {
		try {
			return Arrays.asList(rmiService.getTransazioni(idConto, rmiClient));
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Esegue un prelievo sul conto indicato
	 * @param conto il conto indicato
	 * @param importo l'ammontare del prelievo
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errori del server
	 * @throws Se l'importo non e' prelevabile
	 */
	public static void doPrelievo(IContoCorrenteBancario conto, float importo) throws ServerNonDisponibileException, GccbNetException, OltreLimitePrelievoException {
		try {
			if ( conto.isPrelevabile(importo) ) {
				rmiService.doAddebito(conto, importo, rmiClient);
			} else {
				throw new OltreLimitePrelievoException("Importo non prelevabile");
			}
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Esegue un versamento sul conto indicato
	 * @param conto il conto indicato
	 * @param importo l'ammontare del versamento
	 * @throws ServerNonDisponibileException in caso di errore di connessione
	 * @throws GccbNetException in caso di errori del server
	 */
	public static void doVersamento(IContoCorrenteBancario conto, float importo) throws ServerNonDisponibileException, GccbNetException {
		try {
			rmiService.doAccredito(conto, importo, rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Ricerca i correntisti in base al nome e cognome indicati
	 * @param nome il nome
	 * @param cognome il cognome
	 * @param valoriSimili indica se includere o meno anche riscontri parziali
	 * @return la lista di persone trovate
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	public static List<IPersona> searchPersone(String nome, String cognome, boolean valoriSimili) throws ServerNonDisponibileException, GccbNetException {
		try {
			return Arrays.asList(rmiService.searchPersone(nome, cognome, valoriSimili, rmiClient));
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Ricerca i correntisti in base al codice cliente.
	 * @param idPersona l'id della persona cercata
	 * @return la lista dei risconti (composta da 1 o 0 elementi)
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errori del server
	 */
	public static List<IPersona> searchPersone(int idPersona) throws ServerNonDisponibileException, GccbNetException {
		try {
			return Arrays.asList(new IPersona[] {rmiService.getPersona(idPersona, rmiClient)});
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * Aggiunge un nuova persona
	 * @param nome il nome
	 * @param cognome il cognome
	 * @param indirizzo l'indirizzo
	 * @return una istanza di {@link IPersona} che rappresenta la persona appena aggiunta
	 * @throws ServerNonDisponibileException in caso di errori di connessione
	 * @throws GccbNetException in caso di errori generici
	 */
	public static IPersona addPersona(String nome, String cognome, String indirizzo) throws ServerNonDisponibileException, GccbNetException {
		try {
			return rmiService.addPersona(new Persona(nome, cognome, indirizzo), rmiClient);
		} catch (RemoteException e) {
			throw new ServerNonDisponibileException("Il server non e' piu' disponibile", e);
		}
	}
	/**
	 * L'indirizzo del server 
	 * @return l'indirizzo
	 */
	public static String getServerAddress() {
		return serverAddress;
	}
	/**
	 * La porta del server
	 * @return la porta
	 */
	public static int getServerPort() {
		return serverPort;
	}
	/**
	 * Il nome del servizio corrispondente al ServiceFactory del server
	 * @return il nome del servizio
	 */
	public static String getServiceName() {
		return serviceName;
	}
}
