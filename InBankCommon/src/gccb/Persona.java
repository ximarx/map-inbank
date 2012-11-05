package gccb;

/**
 * Realizzazione dell'interfaccia {@link IPersona}
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public class Persona implements IPersona {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1955766702430225139L;
	
	private int idPersona;
	
	private String nome = "";
	private String cognome = "";
	private String indirizzo = "";
	private boolean isNew;
	
	/**
	 * Crea un oggetto indicante una persona contenuta nel database.
	 * {@link #isNew()} ritornerà false se l'oggetto viene inizializzato
	 * mediante questo costruttore
	 * @param idPersona indice della persona
	 * @param nome Nome della persona
	 * @param cognome Cognome della persona
	 * @param indirizzo Indirizzo
	 */
	public Persona(int idPersona, String nome, String cognome, String indirizzo) {
		
		this.idPersona = idPersona;
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		isNew = false;
	}
	/**
	 * Crea una nuova persona. Creando l'oggetto tramite questo costruttore
	 * {@link #isNew()} ritornerà true
	 * @param nome Il nuovo nome della persona
	 * @param cognome il nuovo cognome della persona
	 * @param indirizzo il nuovo indirizzo
	 */
	public Persona(String nome, String cognome, String indirizzo) {
		
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		isNew = true;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IPersona#getIdPersona()
	 */
	public int getIdPersona() {
		return idPersona;
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.IPersona#getNome()
	 */
	public String getNome() {
		return nome;
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.IPersona#getCognome()
	 */
	public String getCognome() {
		return cognome;
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.IPersona#getIndirizzo()
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	
	/*
	 * Converte la persona in una stringa
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new String("" + nome + " " + cognome + "-" + indirizzo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(IPersona persona) {
		int ccogn = getCognome().toLowerCase().compareTo(persona.getCognome().toLowerCase());
		if ( ccogn != 0) {
			// se i cognomi hanno differenze, non ha senso continuare il confronto
			return ccogn; 
		} else {
			// se i cognomi sono uguali (a meno delle maiuscole) allora controllo i nomi
			return getNome().toLowerCase().compareTo(persona.getNome().toLowerCase());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.IPersona#isNew()
	 */
	public boolean isNew() {
		return isNew;
	}
}
