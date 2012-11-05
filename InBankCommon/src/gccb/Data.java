package gccb;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Realizzazione della interfaccia {@link IData}. Rappresenta una data
 * valida 
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public class Data implements IData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2263590173640926496L;
	
	/**
	 * Il giorno indicato dall'oggetto
	 */
	private int giorno = 0;
	/**
	 * Il mese indicato dall'oggetto
	 */
	private String mese = "";
	/**
	 * L'anno indicato dall'oggetto00
	 */
	private int anno = 0;
	
	/**
	 * Array dei mesi utilizzato per creare una corrispondenza Nome-indice
	 */
	final static public String[] MESI = {
		"Gennaio","Febbraio","Marzo",
		"Aprile","Maggio","Giugno",
		"Luglio","Agosto","Settembre",
		"Ottobre","Novembre","Dicembre"
	};

	/**
	 * Crea una data rappresentate il giorno di creazione
	 */
	public Data() {
		Calendar now = new GregorianCalendar();
		this.anno = now.get(Calendar.YEAR);
		this.giorno = now.get(Calendar.DAY_OF_MONTH);
		this.mese = Data.MESI[now.get(Calendar.MONTH)];
	}
	/**
	 * Crea un oggetto rappresentante la data indicata fra i parametri 
	 * @param giorno il giorno
	 * @param mese il mese
	 * @param anno l'anno
	 */
	public Data(int giorno, String mese, int anno) {
		setGiorno(giorno);
		setAnno(anno);
		setMese(mese);
	}

	/**
	 * Crea un oggetto rappresentante la data indicata come stringa
	 * @param dataMysql una stringa rappresentante un tipo DATE in mysql
	 */
	public Data(String dataMysql) {
		try {
			String[] datapart = dataMysql.split("-");
			this.anno = new Integer(datapart[0]);
			int mi = (new Integer(datapart[1]) - 1);
			this.mese = Data.MESI[mi];
			this.giorno = new Integer(datapart[2]);
		} catch (NullPointerException e) {
			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.IData#getGiorno()
	 */
	public int getGiorno() {
		return giorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.IData#getMese()
	 */
	public String getMese() {
		return mese;
	}

	/*
	 * (non-Javadoc)
	 * @see gccb.IData#getAnno()
	 */
	public int getAnno() {
		return anno;
	}

	/**
	 * Imposta il giorno
	 * @param giorno
	 */
	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	/**
	 * Imposta il mese
	 * @param mese
	 */
	public void setMese(String mese) {
		this.mese = mese;
	}

	/**
	 * Imposta l'anno
	 * @param anno
	 */
	public void setAnno(int anno) {
		this.anno = anno;
	}
	static public Data today() {
		return new Data();
	}
	
 	/**
	 * Ritorna true se la data passata è antecedente
	 * @param d La data da comparare con this
	 * @return true se d è antecedente
	 */
	public boolean compare(Data d) {
		try {
			if ( d.getAnno() < anno ) {
				return true;
			} else if ( d.getAnno() == anno ) {
				if ( Data.getMonthIndex(d.getMese()) <
				Data.getMonthIndex(mese) ) {
					return true;
				} else if ( Data.getMonthIndex(d.getMese()) ==
						Data.getMonthIndex(mese) ) {
					if ( d.getGiorno() < giorno ) {
						return true;
					}
				}
			}
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	/**
	 * Converte la data in stringa
	 */
	public String toString() {
		if ( giorno == 0 || mese == "" || anno == 0 ) {
			return "Non definito";
		} else {
			return new String(giorno + "/" + mese + "/" + anno );
		}
	}
	
	/**
	 * Ritorna l'indice del mese corrispondente alla stringa indicata.
	 * L'indice varia fra 0 (Gennario) e 11 (Dicembre)
	 * @param month Il mese
	 * @return l'indica corrispondente alla stringa
	 * @throws IllegalArgumentException se la stringa indicata non rappresenta un mese valido
	 */
	static private int getMonthIndex(String month) throws IllegalArgumentException {
		for (int i = 0; i < Data.MESI.length; i++ ) {
			if ( month.equalsIgnoreCase(Data.MESI[i]) ) {
				return i;
			}
		}
		throw new IllegalArgumentException("'" + month + "' is a month name");
	}
	
	/*
	 * (non-Javadoc)
	 * @see gccb.IData#toMysqlString()
	 */
	public String toMysqlString() {
		return ""+anno+"-"+ Arrays.asList(MESI).indexOf(mese)+"-"+giorno;
	}
}
