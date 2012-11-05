package gccb;

/**
 * L'interfaccia rappresentante un impiegato memorizzato nel database
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public interface IImpiegato {

	/**
	 * Ritorna l'identificativo dell'impiegato
	 * @return idImpiegato identificativo
	 */
	public int getIdImpiegato();
	/**
	 * Imposta un nuovo id all'impiegato
	 * @param idImpiegato identificativo
	 */
	public void setIdImpiegato(int idImpiegato);
	/**
	 * Ritorna lo username dell'impiegato
	 * @return l'impiegato
	 */
	public String getUsername();
	/**
	 * Imposta uno username all'impiegato
	 * @param username il nuovo username
	 */
	public void setUsername(String username);
	/**
	 * Ritorna la password dell'impiegato
	 * Questo metodo ritorna un valore non nullo solo
	 * se l'impiegato non è ancora stato aggiunto al database
	 * @return la password
	 */
	public String getPassword();
	/**
	 * Imposta un nuovo valore di password
	 * Il metodo ha senso solo se l'impiegato
	 * non e' ancora stato aggiunto al database.
	 * In caso contrari le modifiche apportate andranno perse
	 * con la distruzione dell'oggetto
	 * @param password
	 */
	public void setPassword(String password);
	/**
	 * Ritorna l'etichetta dell'impiegato 
	 * @return l'etichetta
	 */
	public String getVisualizzato();
	/**
	 * Imposta un nuovo valore come etichetta dell'impiegato
	 * La modifica dell'oggetto tramite questo metodo
	 * ha senso solo se l'impiegato non è ancora stato aggiunta al database
	 * In caso contrario le modifiche non verranno inoltrate al database
	 * @param visualizzato
	 */
	public void setVisualizzato(String visualizzato);
	/**
	 * Indica se l'impiegato è nuovo
	 * @return Ritorna vero se l'impiegato non è ancora stato aggiunto al database
	 */
	public boolean isNew();
	
	
}
