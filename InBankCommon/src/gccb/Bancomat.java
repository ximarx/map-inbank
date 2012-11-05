package gccb;


/**
 * Rappresenta un bancomat
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public class Bancomat implements IBancomat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7776707182321120643L;
	/**
	 * L'id del bancomat
	 */
	private int idBancomat;
	/**
	 * Pin associato al bancomat
	 * <i>Viene avvalorato solo se la classe rappresenta un nuovo bancomat</i>
	 */
	private String chiave; 
	/**
	 * Il conto corrente associato al bancomat
	 */
	private IContoCorrenteBancario contoAssociato;
	/**
	 * Massima cifra prelevabile tramite il bancomat
	 */
	private float massimoPrelevabile;
	/**
	 * Il flag che indica se l'oggetto è presente gia nel database
	 */
	private boolean isNew;
	
	/**
	 * Inizializza l'oggetto impostando id, conto e massimo prelevabile del bancomat
	 * @param idBancomat id del bancomat del database
	 * @param conto il conto corrente associato al bancomat
	 * @param massimoPrelevabile Il massimo prelevabile tramite il bancomat
	 */
	public Bancomat(int idBancomat, IContoCorrenteBancario conto, float massimoPrelevabile) {
		this.idBancomat = idBancomat;
		this.contoAssociato = conto;
		this.massimoPrelevabile = massimoPrelevabile;
		this.isNew = false;
	}
	
	/**
	 * Inizializza l'oggetto come nuovo importando massimo prelevabile e password
	 * @param chiave la nuova password da associare al bancomat
	 * @param massimoPrelevabile la cifra massima prelevabile tramite il bancomat
	 */
	public Bancomat(String chiave, float massimoPrelevabile ) {
		this.chiave = chiave;
		this.massimoPrelevabile = massimoPrelevabile;
		isNew = true;
	}
	
	/**
	 * Controlla che l'importo indicato sia prelevabile
	 * @param importo l'importo che si vuole controllare
	 * @return true se l'importo è prelevabile, false altrimenti
	 */
	public boolean isPrelevabile(float importo) {
		return (importo <= massimoPrelevabile && contoAssociato.isPrelevabile(importo));
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IBancomat#getIdBancomat()
	 */
	public int getIdBancomat() {
		return idBancomat;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IBancomat#isNew()
	 */
	public boolean isNew() {
		return isNew;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IBancomat#getChiave()
	 */
	public String getChiave() {
		return chiave;
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IBancomat#getMassimoPrelevabile()
	 */
	public float getMassimoPrelevabile() {
		if ( !isNew() ) {
			float maxLiquidita = getContoAssociato().getSaldo();
			if ( getContoAssociato() instanceof IContoCorrenteConFido) {
				maxLiquidita += ((IContoCorrenteConFido) getContoAssociato()).getFido();
			}
			return ( massimoPrelevabile < maxLiquidita ? massimoPrelevabile : maxLiquidita );
		} else {
			return massimoPrelevabile;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see gccb.IBancomat#getContoAssociato()
	 */
	public IContoCorrenteBancario getContoAssociato() {
		return contoAssociato;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		try {
			return "Bancomat associato al conto " + contoAssociato.getIdConto() + ".\nMassimo prelevabile: " + massimoPrelevabile;
		} catch ( NullPointerException e) {
			return "Bancomat non definito";
		}
	}
	
}
