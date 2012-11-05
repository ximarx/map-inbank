package gccb.server.model;

import java.rmi.server.UnicastRemoteObject;

import gccb.net.rmi.RMIClientType;

/**
 * Racchiude le informazioni relative ad un client connesso al server 
 * @author Francesco Capozzo
 *
 */
public class ServerClient {

	private String idClient;
	private String address;
	private RMIClientType tipo;
	
	/**
	 * Crea l'oggetto impostanto id del client, indirizzo remoto e porta e tipo di client
	 * @param idClient id del client
	 * @param clientAddress indirizzo remoto + porta
	 * @param tipoClient tipo di client connesso
	 */
	public ServerClient(String idClient, String clientAddress, RMIClientType tipoClient) {
		this.idClient = idClient;
		address = clientAddress;
		tipo = tipoClient;
	}

	/**
	 * Ritorna l'id agente del client
	 * @return id agente
	 */
	public String getIdClient() {
		return idClient;
	}

	/**
	 * Imposta un nuovo id agente
	 * @param idClient il nuovo idagente
	 */
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	
	/**
	 * Restituisce la stringa rappresentante il client
	 * @return la strina rappresentante il client
	 * @see UnicastRemoteObject#toString()
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Imposta un nuovo indirizzo remoto
	 * @param address il nuovo indirizzo
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Indica il tipo di client
	 * @return tipo
	 */
	public RMIClientType getTipo() {
		return tipo;
	}

	/**
	 * Imposta il nuovo tipo memorizzato
	 * @param tipo un nuovo tipo di client
	 */
	public void setTipo(RMIClientType tipo) {
		this.tipo = tipo;
	}
	
}
