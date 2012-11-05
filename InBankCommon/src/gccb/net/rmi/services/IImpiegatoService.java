package gccb.net.rmi.services;

import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.IPersona;
import gccb.ITransazione;
import gccb.database.ImpiegatoDB;
import gccb.exception.OltreLimitePrelievoException;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia del servizio con le comuni operazioni compiute dal un client id tipo Impiegato
 * @author Francesco Capozzo
 *
 */
public interface IImpiegatoService extends Remote, IRMIService {

	/**
	 * Recuper la informazioni relative ad una persona con id uguale a quello indicato
	 * @param idPersona l'id della persona cercata
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IPersona} che rappresenta i dati trovati
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IPersona getPersona(int idPersona, IClient client)
		throws RemoteException,GccbNetException;

	/**
	 * Recupera la lista di clienti registrati nel database
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link IPersona} con le informazioni sulle persone trovate
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IPersona[] getPersone(IClient client)
		throws RemoteException, GccbNetException;
	
	/**
	 * Aggiunge un nuovo cliente nel database
	 * @param persona una istanza di {@link IPersona}
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IPersona} che rappresenta la persona appena inserita
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException caso di errore interno del server
	 */
	IPersona addPersona(IPersona persona, IClient client)
			throws RemoteException, GccbNetException;

	/**
	 * Recupera le informazioni relative al conto selezionato
	 * @param idConto il conto cercato
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IContoCorrenteBancario} che rappresenta il conto cercato
	 * @throws RemoteException in caso di errore di connessione
	 * @throws DatiNonTrovatiException se il conto cercato non esiste
	 * @throws ErroreServerException in caso di errore interno del server
	 */
	IContoCorrenteBancario getConto(int idConto, IClient client)
			throws RemoteException, DatiNonTrovatiException,
			GccbNetException;

	/**
	 * Recupera la lista dei bancomat associati al conto indicato
	 * @param idConto il conto indicato
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link IBancomat} con le informazioni sui bancomat cercati
	 * @throws RemoteException in caso di errore di connessione
	 * @throws DatiNonTrovatiException se il conto indicato non è valido
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IBancomat[] getBancomatPerConto(int idConto, IClient client)
			throws RemoteException, DatiNonTrovatiException,
			GccbNetException;

	/**
	 * Recupera le informazioni relative al bancomat indicato
	 * @param idBancomat l'id del bancomat richiesto
	 * @param idConto l'id del conto richiesto
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IBancomat} corrispondente al bancomat cercato
	 * @throws RemoteException in caso di errore di connessione
	 * @throws DatiNonTrovatiException se il bancomat cercato non è valido
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IBancomat getBancomat(int idBancomat, int idConto, IClient client)
			throws RemoteException, DatiNonTrovatiException,
			GccbNetException;

	/**
	 * Aggiunge un nuovo conto
	 * @param conto una istanza di {@link IContoCorrenteBancario} con le informazioni relative al conto da inserire
	 * @param password la password associata al conto
	 * @param client il client che esegue l'operazione
	 * @return una nuova istanza di {@link IContoCorrenteBancario} con le informazioni sul nuovo conto aggiunto
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IContoCorrenteBancario addConto(IContoCorrenteBancario conto,
			String password, IClient client) throws RemoteException,
			GccbNetException;

	/**
	 * Chiude il conto indicato
	 * @param conto il conto che si vuole chiudere
	 * @param client il client che esegue l'operazione
	 * @throws RemoteException in caso di errore di connessione
	 * @throws ErroreServerException in caso di errore interno del server
	 */
	void chiudiConto(IContoCorrenteBancario conto, IClient client)
			throws RemoteException, GccbNetException;

	/**
	 * Ritorna la lista di tutte le transazioni memorizzate
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link ITransazione} con le informazioni sulle transazioni trovate
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	ITransazione[] getTransazioni(IClient client) throws RemoteException,
			GccbNetException;

	/**
	 * Ritorna la lista di tutte le transazioni memorizzate relative ad un conto specifico
	 * @param idConto l'id del conto di cui si vogliono le transazioni
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link ITransazione} cone le informazioni trovate
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	ITransazione[] getTransazioni(int idConto, IClient client) throws RemoteException,
		GccbNetException;

	/**
	 * Recupera le statistiche del server
	 * @param client il client che esegue le operazioni
	 * @return una lista di oggetti rappresentanti le statistiche. Il formato
	 * dell'array è il medesimo di {@link ImpiegatoDB#getStatistiche()}
	 * @throws RemoteException in caso di errore di connessione
	 * @throws DatiNonTrovatiException se il calcolo delle statistiche non ritorna risultati
	 * @throws GccbNetException in caso di errore interno del server
	 */
	Object[] getStatistiche(IClient client) throws RemoteException,
			DatiNonTrovatiException, GccbNetException;

	/**
	 * Modifica il fido associato al conto
	 * @param conto il conto con fido di cui si vuole alterare il fido
	 * @param nuovoFido il valore del nuovo fido
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IContoCorrenteConFido} che rappresenta il conto appena modificato
	 * @throws RemoteException in caso di errore di connessione
	 * @throws DatiNonTrovatiException se il conto indicato non esiste
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IContoCorrenteConFido modificaFido(IContoCorrenteConFido conto,
			float nuovoFido, IClient client) throws RemoteException,
			GccbNetException, DatiNonTrovatiException;

	/**
	 * Aggiunge un nuovo bancomat ad un conto
	 * @param conto il conto a cui viene aggiunto il bancomat
	 * @param bancomat il nuovo bancomat da aggiungere
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IBancomat} con le informazioni sul bancomat appena aggiunte
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IBancomat addBancomat(IContoCorrenteBancario conto, IBancomat bancomat,
			IClient client) throws RemoteException, GccbNetException;

	/**
	 * Esegue un accredito sul conto corrente indicato
	 * @param conto il conto indicato
	 * @param importo l'ammontare dell'accredito
	 * @param client il client che esegue l'operazione
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	void doAccredito(IContoCorrenteBancario conto, float importo, IClient client)
			throws RemoteException, GccbNetException;

	/**
	 * Esegue un addebito sul conto indicato
	 * @param conto il conto indicato
	 * @param importo l'ammontare dell'addebito
	 * @param client il client che esegue lpoperazione
	 * @throws RemoteException in caso di errore di connessione
	 * @throws OltreLimitePrelievoException se l'importo non e' prelevabile
	 * @throws GccbNetException in caso di errore interno del server
	 */
	void doAddebito(IContoCorrenteBancario conto, float importo, IClient client)
			throws RemoteException, OltreLimitePrelievoException, GccbNetException;

	/**
	 * Recupera l'insieme dei conti aperti
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link IContoCorrenteBancario} con le informazioni trovate
	 * @throws RemoteException in caso di errore dell'operazione
	 * @throws GccbNetException in caso di errore intenro del server
	 */
	IContoCorrenteBancario[] getConti(IClient client) throws RemoteException,
			GccbNetException;
	
	/**
	 * Cerca un conto in base alle informazioni indicate
	 * @param nome il nome dell'intestatario
	 * @param cognome il cognome dell'intestatario
	 * @param valoriSimili se aggiungere o meno anche riscontri parziali nell'insieme dei risultati 
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link IContoCorrenteBancario} con i valori trovati
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IContoCorrenteBancario[] searchConto(String nome, String cognome, boolean valoriSimili, IClient client)
			throws RemoteException,	GccbNetException;
	
	/**
	 * Recupera la lista di conti associati all'intestatario indicato
	 * @param idPersona l'id del cliente
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link IContoCorrenteBancario} con i conti intestati all'utente
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	IContoCorrenteBancario[] searchContoByPersona(int idPersona, IClient client)
			throws RemoteException,GccbNetException;
	
	/**
	 * Cerca i clienti in base ai parametri indicati
	 * @param nome il nome
	 * @param cognome il cognome
	 * @param valoriSimili indica se includere o meno anche riscontri parziali
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link IPersona} con i valori trovati
	 * @throws RemoteException in caso errori di connessione
	 * @throws GccbNetException in caso di un errore interno del server
	 */
	IPersona[] searchPersone(String nome, String cognome, boolean valoriSimili, IClient client)
			throws RemoteException, GccbNetException;
	
	/**
	 * Rimuove il bancomat indicato
	 * @param bancomat il bancomat da rimuovere
	 * @param client il client che esegue l'operazione
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 */
	void rimuoviBancomat(IBancomat bancomat, IClient client)
			throws RemoteException, GccbNetException;
}
