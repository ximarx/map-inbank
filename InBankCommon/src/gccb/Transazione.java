package gccb;

/**
 * Realizzazione dell'interfaccia {@link ITransazione}, rappresenta
 * una transazione bancaria
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public class Transazione implements ITransazione {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8759155319563753331L;
	private int idTransazione;
	private int idConto;
	private int idAgente;
	private float importo;
	private IData data;
	private ITransazione.TIPO tipoTransazione;
	private boolean n;
	
	/**
	 * Crea una nuova transazione in modo che {@link #isNew()} restituisca vero
	 */
	public Transazione() {
		this.n = true;
	}
	
	/**
	 * Crea una transazione specificando se è una nuova transazione
	 * @param isNew indica se la transazione è nuova
	 */
	public Transazione(boolean isNew) {
		this.n = isNew;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#getIdTransazione()
	 */
	public int getIdTransazione() {
		return idTransazione;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#setIdTransazione(int)
	 */
	public void setIdTransazione(int idTransazione) {
		this.idTransazione = idTransazione;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#getIdConto()
	 */
	public int getIdConto() {
		return idConto;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#setIdConto(int)
	 */
	public void setIdConto(int idConto) {
		this.idConto = idConto;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#getIdAgente()
	 */
	public int getIdAgente() {
		return idAgente;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#setIdAgente(int)
	 */
	public void setIdAgente(int idAgente) {
		this.idAgente = idAgente;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#getImporto()
	 */
	public float getImporto() {
		return importo;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#setImporto(float)
	 */
	public void setImporto(float importo) {
		this.importo = importo;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#getData()
	 */
	public IData getData() {
		return data;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#setData(gccb.IData)
	 */
	public void setData(IData data) {
		this.data = data;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#getTipoTransazione()
	 */
	public ITransazione.TIPO getTipoTransazione() {
		return tipoTransazione;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#setTipoTransazione(gccb.ITransazione.TIPO)
	 */
	public void setTipoTransazione(ITransazione.TIPO tipoTransazione) {
		this.tipoTransazione = tipoTransazione;
	}
	/**
	 * Imposta il tipo di transazione in base al valore della stringa
	 * @param tipo il tipo di transazione
	 */
	public void setTipoTransazione(String tipo) {
		if ( tipo.equalsIgnoreCase("APERTURA_CONTO")  ) {
			this.tipoTransazione = ITransazione.TIPO.APERTURA_CONTO;
		} else if ( tipo.equalsIgnoreCase("CHIUSURA_CONTO")  ) {
			this.tipoTransazione = ITransazione.TIPO.CHIUSURA_CONTO;
		} else if ( tipo.equalsIgnoreCase("ACCREDITO")  ) {
			this.tipoTransazione = ITransazione.TIPO.ACCREDITO;
		} else if ( tipo.equalsIgnoreCase("ADDEBITO")  ) {
			this.tipoTransazione = ITransazione.TIPO.ADDEBITO;
		} else if ( tipo.equalsIgnoreCase("MODIFICA_FIDO")  ) {
			this.tipoTransazione = ITransazione.TIPO.MODIFICA_FIDO;
		} else if ( tipo.equalsIgnoreCase("PRELIEVO")  ) {
			this.tipoTransazione = ITransazione.TIPO.PRELIEVO;
		} else if ( tipo.equalsIgnoreCase("ABILITAZIONE_BANCOMAT")  ) {
			this.tipoTransazione = ITransazione.TIPO.ABILITAZIONE_BANCOMAT;
		} else if ( tipo.equalsIgnoreCase("RIMOZIONE_BANCOMAT")  ) {
			this.tipoTransazione = ITransazione.TIPO.RIMOZIONE_BANCOMAT;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#isNew()
	 */
	public boolean isNew() {
		return n;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ITransazione#setNew(boolean)
	 */
	public void setNew(boolean n) {
		this.n = n;
	}
	/**
	 * Converte la transazione in una stringa che la descriva
	 */
	public String toString() {
		String s = "Transazione " + getIdTransazione() + " di tipo " + getTipoTransazione() + " eseguita in data " + getData() + " di importo pari a " + getImporto();
		return s;
	}
	
}
