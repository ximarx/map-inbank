package gccb;

/**
 * Estende l'interfaccia di {@link IContoCorrenteBancario}
 * per rappresentare un conto corrente a cui sia associato
 * un fido
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public interface IContoCorrenteConFido extends IContoCorrenteBancario {
	/**
	 * Ritorna il valore del fido associato al conto
	 * @return il valore del fido
	 */
	public float getFido();
}
