package gccb.net.rmi;

import java.rmi.RemoteException;

/**
 * Il client per progetto Cliente
 * @author Francesco Capozzo
 *
 */
public class RMIClientCliente extends RMIClientAdapter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8506278094910901033L;
	private int idConto = 0;
	
	/**
	 * Crea l'oggetto impostando il riferimento al server rmi
	 * @param rmiServer il service factory del server rmi
	 * @throws RemoteException in caso di errore di connessione
	 */
	RMIClientCliente(IServiceFactory rmiServer) throws RemoteException {
		super(rmiServer);
		clientType = RMIClientType.UTENTE;
	}

	/**
	 * Ritorna l'id del conto associato al client autenticato
	 * @return id del conto
	 */
	public int getIdConto() {
		return idConto;
	}
	
	/**
	 * Imposta l'id del conto associato al client
	 * @param idConto
	 */
	void setIdConto(String idConto) {
		Integer i = new Integer(idConto);
		if ( i.toString().equals(idConto) ) {
			this.idConto = i;
		} else {}
	}
}
