package gccb.server.model;

import gccb.net.rmi.RMIClientType;

/**
 * Ingloba le informazioni relative ad un servizio registrabile individuato nella classpath
 * @author Francesco Capozzo
 *
 */
public class ServerService {

	/**
	 * Stato di attivazione del servizio
	 * @author Francesco Capozzo
	 *
	 */
	public enum Stati {
		ATTIVO, NON_ATTIVO
	}
	
	private String nome;
	private Stati stato;
	private RMIClientType tipo;

	/**
	 * Crea un nuovo servizio con nome e stato indicato
	 * @param nome nome del servizio
	 * @param stato stato di attivazione
	 */
	public ServerService(String nome, Stati stato) {
		this.nome = nome;
		this.stato = stato;
		this.tipo = null;
	}

	/**
	 * Indica il nome del servizio
	 * @return il nome del servizio
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta il nome del servizio
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Ritorna lo stato di attivazione del servizio
	 * @return lo stato di attivazione
	 */
	public Stati getStato() {
		return stato;
	}

	/**
	 * Imposta un nuovo stato
	 * @param stato il nuovo stato
	 */
	public void setStato(Stati stato) {
		this.stato = stato;
	}
	/**
	 * Indica il tipo di client verso cui è registrato il servizio
	 * @return il tipo di client
	 */
	public RMIClientType getTipo() {
		return tipo;
	}
	/**
	 * Imposta un nuovo tipo di client verso cui è registrato il servizio
	 * @param tipo il nuovo tipo
	 */
	public void setTipo(RMIClientType tipo) {
		this.tipo = tipo;
	}
	
}
