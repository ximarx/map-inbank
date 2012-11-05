package gccb;

import java.io.Serializable;

/**
 * Interfaccia che rappresenta una transazione bancaria contenuta nel database
 * @author Francesco Capozzo
 * @author Antonietta Gorgoglione
 *
 */
public interface ITransazione extends Serializable {

	/**
	 * Ritorna l'identificativo della transazione
	 * @return idTransazione l'id di transazione
	 */
	int getIdTransazione();
	/**
	 * Imposta l'id di transazione per una nuova transazione
	 * da aggiungere al database
	 * @param idTransazione il nuovo id di transazione
	 */
	void setIdTransazione(int idTransazione);
	/**
	 * Ritorna l'id del conto su cui è stata eseguita la transazione
	 * @return id del conto
	 */
	int getIdConto();
	/**
	 * Imposta l'id del conto su cui verrà assegnata la transazione
	 * @param idConto
	 */
	void setIdConto(int idConto);
	/**
	 * Ritorna l'importo trasferito nell'operazione.
	 * @return Se l'operazione non ha un importo di riferimento
	 * (ad esempio chiusura_conto o rimozione_bancomat)
	 * questo metodo ritorna 0
	 */
	float getImporto();
	/**
	 * Setta l'importo trasferito dalla transazione
	 * @param importo il nuovo importo
	 */
	void setImporto(float importo);
	/**
	 * Restituisce la data in cui la transazione è stata eseguita 
	 * @return istanza di {@link IData}
	 */
	IData getData();
	/**
	 * Imposta la data della transazione
	 * @param data una istanza di {@link IData} che rappresenti la data in cui la transazione è eseguita
	 */
	void setData(IData data);
	/**
	 * Ritorna il tipo di transazione
	 * @return ritorna un elemento di {@link ITransazione.TIPO}
	 */
	ITransazione.TIPO getTipoTransazione();
	/**
	 * Importa il tipo di transazione
	 * @param tipo un elemento di {@link ITransazione.TIPO}
	 */
	void setTipoTransazione(ITransazione.TIPO tipo);
	/**
	 * Ritorna l'agente che ha eseguito la transazione
	 * @return id dell'agente
	 */
	int getIdAgente();
	/**
	 * Setta l'id dell'agente che esegue la transazione
	 * @param idAgente il nuovo idAgente
	 */
	void setIdAgente(int idAgente);
	/**
	 * Indica se è un nuova transazione o se è recuperata dal database
	 * @return true se la transazione non è ancora stata inserita nel database
	 */
	boolean isNew();
	/**
	 * Imposta l'oggetto come nuovo
	 * @param n
	 */
	void setNew(boolean n);
	
	/**
	 * Tipi di transazioni
	 * @author Francesco Capozzo
	 *
	 */
	public enum TIPO {
		APERTURA_CONTO,
		CHIUSURA_CONTO, 
		ACCREDITO,
		ADDEBITO,
		MODIFICA_FIDO,
		PRELIEVO,
		ABILITAZIONE_BANCOMAT,
		RIMOZIONE_BANCOMAT
	}
}
