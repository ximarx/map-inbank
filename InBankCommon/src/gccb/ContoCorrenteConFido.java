package gccb;


/**
 * Realizzazione dell'interfaccia {@link IContoCorrenteConFido} ed estensione della
 * classe {@link ContoCorrenteBancario}. Rappresenta un conto a cui sia associato un fido
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public class ContoCorrenteConFido extends ContoCorrenteBancario implements IContoCorrenteConFido {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8402613753526089373L;
	
	/**
	 * Il valore del fido associato al conto
	 */
	private float fido = 0f;

	/**
	 * Crea un oggetto che rappresenta un conto con fido contenuto nel database
	 * @param idConto id del conto
	 * @param ncorr l'intestatario del conto
	 * @param nsaldo il saldo del conto
	 * @param nfido il valore del fido
	 * @param data la data dell'ultimo movimento associato
	 */
	public ContoCorrenteConFido(int idConto, IPersona ncorr, float nsaldo, float nfido, IData data) {
		super(idConto, ncorr, nsaldo, data);
		fido = nfido;
	}

	/**
	 * Inizializza l'oggetto per rappresentare un nuovo conto
	 * non ancora inserito nel database
	 * @param ncorr l'intestatario del conto
	 * @param nsaldo il saldo associato al conto
	 * @param nfido il valore del fido
	 * @param data la data dell'ultimo movimento
	 */
	public ContoCorrenteConFido(IPersona ncorr, float nsaldo, float nfido, IData data) {
		super(ncorr, nsaldo, data);
		fido = nfido;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.ContoCorrenteBancario#isPrelevabile(float)
	 */
	@Override
	public boolean isPrelevabile(float importo) {
		if ( importo < 0f ) {
			throw new IllegalArgumentException("Importo deve essere un valore NON-NEGATIVO");
		}
		if ( importo <= getSaldo() + fido ) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.IContoCorrenteConFido#getFido()
	 */
	public float getFido() {
		return fido;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.ContoCorrenteBancario#toString()
	 */
	public String toString() {
		return super.toString() + ".\nFido: " + fido;
	}
}
