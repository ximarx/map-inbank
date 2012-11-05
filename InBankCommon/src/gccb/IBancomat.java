package gccb;

import java.io.Serializable;

/**
 * Interfaccia associata ai bancomat
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public interface IBancomat extends Serializable {

	/**
	 * Ritorna id del bancomat
	 * @return id del bancomat
	 */
	public int getIdBancomat();
	/**
	 * Indica se l'oggetto è un nuovo bancomat, o se è già presente del database
	 * @return true se il bancomat non è ancora presente nel database
	 */
	public boolean isNew();
	/**
	 * Ritorna la password associata al bancomat. Il valore di ritorno
	 * sarà non nullo solo se {@link IBancomat#isNew()} ritorna vero
	 * @return la password associata al bancomat
	 */
	public String getChiave();
	/**
	 * Ritorna la cifra massima prelevabile dal conto
	 * Il valore restituito corrisponde alla cifra minore fra quella
	 * del massimo prelevabile dal bancomat e quella dal prelievo massimo effettuabile
	 * sul conto (compresa di fido se presente)
	 * 
	 * Ad esempio se il massimo prelevabile dal bancomat e' 500
	 * e il saldo del conto corrisponde a 200 con un fido massimo di 200
	 * massimo prelevabile ritornerà 400 (e non 500, in quanto non sarebbe
	 * possibile prelevare dal conto 500)
	 * @return il massimo prelevabile
	 */
	public float getMassimoPrelevabile();
	
	/**
	 * Restituisce il conto associato al bancomat
	 * @return il conto
	 */
	public IContoCorrenteBancario getContoAssociato();
	
}
