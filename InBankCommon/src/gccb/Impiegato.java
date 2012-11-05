package gccb;

/**
 * Realizzazione dell'interfaccia {@link IImpiegato}. Rappresenta
 * un impiegato
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public class Impiegato implements IImpiegato {

	private String username;
	private String password;
	private String visualizzato;
	private int idImpiegato;

	/**
	 * Crea l'oggetto associato ad un nuovo impiegato
	 */
	public Impiegato() {

	}
	/**
	 * Crea un nuovo impiegato specificando username, password ed etichetta
	 * @param user username dell'impiegato
	 * @param pass password dell'impiegato
	 * @param visual etichetta associata all'impiegato
	 */
	public Impiegato(String user, String pass, String visual) {
		username = user;
		password = pass;
		visualizzato = visual;
	}
	/**
	 * Crea l'oggetto rappresentante l'impiegato aggiunto al database con
	 * l'identificativo indicato
	 * @param idImpiegato id dell'impiegato
	 * @param user username dell'impiegato
	 * @param pass password dell'impiegato
	 * @param visual etichetta visualizzata
	 */
	public Impiegato(int idImpiegato, String user, String pass, String visual) {
		this(user, pass, visual);
		this.idImpiegato = idImpiegato; 
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#getUsername()
	 */
	public String getUsername() {
		return username;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#setUsername(java.lang.String)
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#getPassword()
	 */
	public String getPassword() {
		return password;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#getVisualizzato()
	 */
	public String getVisualizzato() {
		return visualizzato;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#setVisualizzato(java.lang.String)
	 */
	public void setVisualizzato(String visualizzato) {
		this.visualizzato = visualizzato;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#isNew()
	 */
	public boolean isNew() {
		return idImpiegato <= 0;
	}
	public String toString() {
		return new String("" + idImpiegato + " - " + username + " - " + visualizzato );
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#getIdImpiegato()
	 */
	public int getIdImpiegato() {
		return idImpiegato;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IImpiegato#setIdImpiegato(int)
	 */
	public void setIdImpiegato(int idImpiegato) {
		this.idImpiegato = idImpiegato;
	}
}
