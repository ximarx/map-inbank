package gccb;

import java.io.Serializable;

/**
 * Interfaccia che rappresenta un conto corrente bancario
 * 
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public interface IContoCorrenteBancario extends Serializable {

	/**
	 * Controlla che l'importo indicato sia prelevabile e ne ritorna l'esito
	 * @param importo Il valore da controllare
	 * @return l'esito del controllo
	 */
	public boolean isPrelevabile(float importo);
	/**
	 * Ritorna il saldo attuale del conto
	 * @return il valore corrispondente al saldo
	 */
	public float getSaldo();
	/**
	 * Ritorna una istanza di {@link IPersona} 
	 * rappresentante l'intestatario del conto
	 * @return l'intestatario
	 */
	public IPersona getIntestatario();
	/**
	 * Ritorna una istanza di {@link IData}
	 * rappresentante l'ultimo movimento registrato
	 * @return la data dell'ultimo movimento registrato
	 */
	public IData getUltimoMovimento();
	/**
	 * Ritorna l'identificativo del conto
	 * @return idConto
	 */
	public int getIdConto();
	/**
	 * Controlla se l'istanza rappresenta un nuovo conto
	 * o se è già presente nel database
	 * @return true se il conto è nuovo, false altrimenti
	 */
	public boolean isNew();
	
}
