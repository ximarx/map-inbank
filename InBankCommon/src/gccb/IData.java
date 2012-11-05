package gccb;

import java.io.Serializable;

/**
 * Interfaccia delle date
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public interface IData extends Serializable {

	/**
	 * Ritorna il giorno indicato dall'oggetto
	 * @return il giorno
	 */
	public int getGiorno();
	/**
	 * Ritorna il mese indicato dalla data
	 * @return l'indice del mese
	 */
	public String getMese();
	/**
	 * Ritorna l'anno indicato dall'oggetto
	 * @return l'anno
	 */
	public int getAnno();
	/**
	 * Trasforma la data indicata dall'oggetto in una stringa
	 * leggibile correttamente da mysql come una data
	 * @return una data valida mysql
	 */
	public String toMysqlString();
}
