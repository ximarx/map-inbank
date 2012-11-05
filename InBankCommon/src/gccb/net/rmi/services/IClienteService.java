package gccb.net.rmi.services;

import gccb.IContoCorrenteBancario;
import gccb.ITransazione;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia del servizio con le comuni operazioni compiute dal un client id tipo Cliente
 * @author Francesco Capozzo
 *
 */
public interface IClienteService extends Remote, IRMIService {

	/**
	 * Esegue un bonifico fra conti
	 * @param idContoOrigine id del conto di origine
	 * @param idContoDestinazione id del conto di destinazione
	 * @param importo importo da trasferire
	 * @param client il client che esegue l'operazione
	 * @throws RemoteException in caso di errore di connessione
	 * @throws IllegalArgumentException se le  informazioni indicate non sono valide
	 * @throws GccbNetException in caso di errore interno del server
	 */
	void doBonifico(int idContoOrigine, int idContoDestinazione, float importo,
			IClient client) throws RemoteException, IllegalArgumentException,
			GccbNetException;

	/**
	 * Restituisce una lista degli ultimi movimento associati al conto
	 * @param idConto id del conto richiesto
	 * @param client il client che esegue l'operazione
	 * @return un array di {@link ITransazione} con le informazioni relative ad ogni transazione
	 * @throws RemoteException in caso di errore di connessione
	 * @throws IllegalArgumentException se il conto indicato non è valido
	 * @throws GccbNetException in caso di errore interno del server
	 */
	ITransazione[] getUltimiMovimentiConto(int idConto, IClient client)
			throws RemoteException, IllegalArgumentException, GccbNetException;

	/**
	 * Restituisce le informazioni relative al conto indicato
	 * @param idConto id del conto richiesto
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IContoCorrenteBancario} con le informazioni sul conto riesco
	 * @throws RemoteException in caso di errore di connessione
	 * @throws GccbNetException in caso di errore interno del server
	 * @throws DatiNonTrovatiException se il conto indicato non e' stato trovato
	 */
	IContoCorrenteBancario getConto(int idConto, IClient client) throws RemoteException, GccbNetException, DatiNonTrovatiException;
	
	
}
