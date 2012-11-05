package gccb;

/**
 * Realizzazione dell'interfaccia IContoCorrenteBancario rappresentate
 * un conto corrente bancario
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public class ContoCorrenteBancario implements IContoCorrenteBancario {

	private static final long serialVersionUID = 1326172899192380739L;
	/**
	 * Importo depositato sul conto
	 */
	protected float saldo = 0f;
	/**
	 * Data ultima operazione effettuata
	 */
	protected IData dataUltimoMovimento = null;
	/**
	 * Identificativo univoco del conto
	 */
	private int idConto;
	/**
	 * Intestatario del conto
	 */
	private IPersona correntista = null;
	
	private boolean isNew;
	
	/**
	 * Costruisce l'oggetto impostando id, intestatario, saldo e data
	 * Poichè viene specificato l'id del conto {@link #isNew()} ritornerà false
	 * @param idConto l'id del conto nel database
	 * @param ncorr una istanza di {@link IPersona} rappresentante l'intestatario
	 * @param nsaldo il saldo del conto
	 * @param data una istanza di {@link IData} rappresentante l'ultimo movimento registrato
	 */
	public ContoCorrenteBancario(int idConto, IPersona ncorr, float nsaldo, IData data) {
		this.idConto = idConto;
		correntista = ncorr;
		saldo = nsaldo;
		dataUltimoMovimento = data;
		isNew = false;
	}

	/**
	 * Crea un nuovo conto (non ancora presente nel database)
	 * specificando un intestatario, un saldo e la data di creazione del conto
	 * @param ncorr l'intestatario del co nto
	 * @param nsaldo il saldo del conto
	 * @param data la data di registrazione
	 */
	public ContoCorrenteBancario(IPersona ncorr, float nsaldo, IData data) {
		correntista = ncorr;
		saldo = nsaldo;
		dataUltimoMovimento = data;
		isNew = true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.IContoCorrenteBancario#isPrelevabile(float)
	 */
	public boolean isPrelevabile(float importo) {
		if ( importo < 0f) {
			throw new IllegalArgumentException("Importo deve essere un valore NON-NEGATIVO");
		}
		if ( importo < saldo ) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IContoCorrenteBancario#getSaldo()
	 */
	public float getSaldo() {
		return saldo;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IContoCorrenteBancario#getIdConto()
	 */
	public int getIdConto() {
		return idConto;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IContoCorrenteBancario#isNew()
	 */
	public boolean isNew() {
		return isNew;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IContoCorrenteBancario#getIntestatario()
	 */
	public IPersona getIntestatario() {
		return correntista;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IContoCorrenteBancario#getUltimoMovimento()
	 */
	public IData getUltimoMovimento() {
		return dataUltimoMovimento;
	}
	/**
	 * Rappresenta il conto nella forma di stringa
	 * @return una descrizione del conto
	 */
	public String toString() {
		return "Conto " + idConto + "\nIntestato a \"" + correntista + "\".\nSaldo attuale: "+saldo+".\nUltimo movimento registrato: "+dataUltimoMovimento; 
	}
}
