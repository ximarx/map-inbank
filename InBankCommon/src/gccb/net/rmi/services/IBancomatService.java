package gccb.net.rmi.services;

import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.exception.net.GccbNetException;
import gccb.net.rmi.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia del servizio esposto ai bancomat. Permette
 * di eseguire le operazioni necessarie da un bancomat
 * @author Francesco Capozzo
 *
 */
public interface IBancomatService extends Remote, IRMIService {

	/**
	 * Esegue un prelievo dell'importo indicato
	 * @param importo l'importo del prelievo
	 * @param client il client che esegue l'operazione
	 * @throws RemoteException in caso di errori di connessione
	 * @throws ErroreServerException in caso di errori interni del server
	 * @throws IllegalArgumentException se l'importo indicato non è valido
	 */
	void doPrelievo(float importo, IClient client) throws RemoteException, GccbNetException;
	/**
	 * Ritorna il conto associato al client connesso
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IContoCorrenteBancario} con le informazioni sul conto associato al bancomat
	 * @throws RemoteException in caso di errori di connessione 
	 * @throws GccbNetException in caso di errori interni al server o se il conto non e' trovato
	 */
	IContoCorrenteBancario doSaldo(IClient client) throws RemoteException, GccbNetException;
	/**
	 * Ritorna le informazioni relative al bancomat
	 * @param client il client che esegue l'operazione
	 * @return una istanza di {@link IBancomat} con le informazioni sul bancomat
	 * @throws RemoteException in caso di errore durante la connessione
	 * @throws GccbNetException se il bancomat non e' stato trovato
	 */
	IBancomat getBancomat(IClient client) throws RemoteException, GccbNetException;
}
