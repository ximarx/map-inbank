package gccb;

import java.io.Serializable;

/**
 * Interfaccia di un oggetto rappresentante un cliente 
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public interface IPersona extends Comparable<IPersona>, Serializable {

	/**
	 * Ritorna l'identificativo del cliente
	 * @return idPersona
	 */
	public int getIdPersona();
	/**
	 * Ritorna il nome della persona
	 * @return il nome
	 */
	public String getNome();
	/**
	 * Ritorna il cognome della persona
	 * @return il cognome
	 */
	public String getCognome();
	/**
	 * Ritorna l'indirizzo della persona
	 * @return l'indirizzo
	 */
	public String getIndirizzo();
	/**
	 * Indica se la persona è gia stata inserita nel database
	 * @return true se la persona non è presente nel database
	 */
	public boolean isNew();
}
