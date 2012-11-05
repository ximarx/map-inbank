package gccb.database;

import gccb.Bancomat;
import gccb.ContoCorrenteBancario;
import gccb.ContoCorrenteConFido;
import gccb.Data;
import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.IContoCorrenteConFido;
import gccb.IPersona;
import gccb.ITransazione;
import gccb.Persona;
import gccb.Transazione;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.db.GccbDbException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Wrapper per le funzioni del DB accessibili dal client IMPIEGATO 
 * @author Francesco Capozzo
 * @author Michele Doronzo
 *
 */
public class ImpiegatoDB extends RemoteProcedureAccessor {

	static private ImpiegatoDB singleton = null;
	
	/**
	 * Costruttore privato di classe
	 * @throws DatabaseException se la connessione al database non è stata creata
	 */
	private ImpiegatoDB() throws GccbDbException {
		if ( DbAccess.getConnection() == null) {
			throw new GccbDbException("Database non inizializzato");
		}
	}
	
	/**
	 * Ritorna l'istanza di default della classe. Realizzazione del pattern singleton
	 * @return una istanza di {@link ImpiegatoDB}
	 * @throws DatabaseException se la connessione non è stata inizializzata
	 */
	public static ImpiegatoDB getReference() throws GccbDbException {
		if ( singleton == null ) {
			singleton = new ImpiegatoDB();
		}
		return singleton;
	}
	
	/**
	 * Recupera le informazioni relative al conto indicato
	 * @param idConto l'id del conto di cui si vogliono le informazioni
	 * @return una istanza di {@link IContoCorrenteBancario} con tutte le informazioni
	 * relative al conto richiesto
	 * @throws GccbDbException in caso di errore durante la query
	 * @throws DatiNonTrovatiException nessun conto trovato
	 */
	public IContoCorrenteBancario getConto(int idConto) throws GccbDbException, DatiNonTrovatiException {
		try {
			ResultSet rs = execProcedureWO("getConto", idConto);
			if ( rs.first() ) {
				Persona p = new Persona(
						rs.getInt("idPersona"),
						rs.getString("intestatarioNome"),
						rs.getString("intestatarioCognome"),
						rs.getString("intestatarioIndirizzo")
						);
				Data d = new Data(rs.getString("ultimoMovimento"));
				ContoCorrenteBancario c;
				if (rs.getFloat("fido") != 0f) {
					c = new ContoCorrenteConFido(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), rs.getFloat("fido"), d);
				} else {
					c = new ContoCorrenteBancario(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), d);
				}
				return c;				
			} else {
				throw new DatiNonTrovatiException("Nessun conto trovato");
			}
		} catch (NullPointerException e) {
			throw new DatiNonTrovatiException("Nessun conto trovato");
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Recupera le informazioni relative ad un correntista in base all'id indicato
	 * @param idPersona l'id per correntista da cercare
	 * @return ritorna una istanza di {@link IPersona} che incapsula le informazioni trovate
	 * @throws GccbDbException in caso di errore durante la query
	 * @throws DatiNonTrovatiException se nessuna persona trovata
	 */
	public IPersona getPersona(int idPersona) throws GccbDbException, DatiNonTrovatiException {
		try {
			ResultSet rs = execProcedureWO("getPersona", idPersona);
			if ( rs.first() ) {
				Persona p = new Persona(
						idPersona,
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("indirizzo")
						);
				return p;				
			} else {
				throw new DatiNonTrovatiException("Nessun cliente trovato");
			}
		} catch (NullPointerException e) {
			throw new DatiNonTrovatiException("Nessun cliente trovato");
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Aggiunge un bancomat al conto indicato
	 * @param idConto l'id del conto a cui si vuole aggiungere il bancomat
	 * @param idAgente l'id agente che effettua l'operazione
	 * @param codiceSegreto il codice segreto del bancomat
	 * @param massimoPrelevabile l'imposto massimo prelevabile dal bancomat
	 * @return una istanza di {@link IBancomat} rappresentante il bancomat appena inserito
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IBancomat addBancomat(int idConto, int idAgente, String codiceSegreto, float massimoPrelevabile) throws GccbDbException {
		try {
			ResultSet rs = execFunction("addBancomat", idConto, idAgente, codiceSegreto, massimoPrelevabile);
			if ( rs.first() ) {
				IContoCorrenteBancario conto = getConto(idConto);
				IBancomat bancomat = new Bancomat(rs.getInt(1), conto, massimoPrelevabile);
				return bancomat;
			} else {
				throw new GccbDbException("Errore durante l'inserimento del bancomat");
			}
		} catch (NullPointerException e) {
			throw new GccbDbException("Errore durante l'inserimento del bancomat");
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Recupera le informazioni relative ad un bancomat
	 * @param idBancomat l'id del bancomat da recuperare
	 * @param idConto l'id del conto associato al bancomat
	 * @return una istanza di {@link IBancomat} che incapsula le informazioni individuate
	 * @throws GccbDbException in caso di errore durante la query
	 * @throws DatiNonTrovatiException se nessun bancomat trovato
	 */
	public IBancomat getBancomat(int idBancomat, int idConto) throws GccbDbException, DatiNonTrovatiException {
		try {
			IContoCorrenteBancario c = getConto(idConto);
			ResultSet rs = execProcedureWO("getBancomatDaId", idConto);
			if ( rs.first() ) {
				IBancomat b = new Bancomat(
						rs.getInt("idBancomat"),
						c,
						rs.getFloat("massimoPrelevabile")
						);
				return b;
			} else {
				throw new DatiNonTrovatiException("Nessun bancomat trovato");
			}
		} catch (NullPointerException e) {
			throw new DatiNonTrovatiException("Nessun bancomat trovato");
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Esegue un accredito sul conto indicato
	 * @param idConto id del conto su cui effettuare l'accredito
	 * @param idAgente l'id dell'agente che esegue l'operazione
	 * @param importo l'importo da accreditare
	 * @return l'esito dell'operazione
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public boolean doAccredito(int idConto, int idAgente, float importo) throws GccbDbException {
		try {
			ResultSet rs = execFunction("doAccredito", idConto, idAgente, importo);
			rs.first();
			return rs.getBoolean(1);
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Esegue un addebito sul conto indicato
	 * @param idConto l'id del conto sul quale fare l'addebito
	 * @param idAgente l'id dell'agente che esegue l'operazione
	 * @param importo l'importo da addebitare
	 * @return l'esito dell'operazione
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public boolean doAddebito(int idConto, int idAgente, float importo) throws GccbDbException {
		try {
			ResultSet rs = execFunction("doAddebito", idConto, idAgente, importo);
			rs.first();
			return rs.getBoolean(1);
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Recupare l'insieme dei bancomat associati ad un conto specifico
	 * @param idConto id del conto di cui identificare i bancomat
	 * @return un array di {@link IBancomat} contenenti le informazioni sui bancomat trovati
	 * @throws GccbDbException in caso di errori durante la query
	 * @throws DatiNonTrovatiException se il conto indicato non e' stato trovato
	 */
	public IBancomat[] getBancomatPerConto(int idConto) throws GccbDbException, DatiNonTrovatiException {
		ArrayList<IBancomat> lb = new ArrayList<IBancomat>();
		try {
			IContoCorrenteBancario c = getConto(idConto);
			ResultSet rs = execProcedureWO("getBancomatConto", idConto);
			while ( rs.next() ) {
				IBancomat b = new Bancomat(
						rs.getInt("idBancomat"),
						c,
						rs.getFloat("massimoPrelevabile")
						);
				
				lb.add(b);
			}
			return lb.toArray(new IBancomat[]{});
		} catch (NullPointerException e) {
			return lb.toArray(new IBancomat[]{});
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Rimuove il bancomat specificato dal conto
	 * @param idBancomat id del bancomat da eliminare
	 * @param idConto il conto associato al bancomat
	 * @param idAgente l'agente che esegue la rimozione
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public void rimuoviBancomat(int idBancomat, int idConto, int idAgente) throws GccbDbException {
		try {
			execProcedure("rimuoviBancomat", idBancomat, idConto, idAgente);
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Modifica il fido associato ad un conto corrente
	 * @param idConto id del conto di cui modificare il fido
	 * @param nuovoFido il valore dl nuovo fido
	 * @param idAgente l'agente che esegue la modifica
	 * @return una istanza di {@link IContoCorrenteConFido} che rappresenta il nuovo conto modificato
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public IContoCorrenteConFido modificaFido(int idConto, float nuovoFido, int idAgente) throws GccbDbException {
		try {
			execProcedure("modificaFido", idConto, nuovoFido, idAgente);
			return (IContoCorrenteConFido) getConto(idConto);
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Ritorna la lista dei movimenti registrati nel database
	 * @return un array di {@link ITransazione} rappresentanti l'insieme di transazioni memorizzate
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public ITransazione[] getMovimenti() throws GccbDbException {
		try {
			ResultSet rs = execProcedureWO("getMovimenti");
			ArrayList<ITransazione> tt = new ArrayList<ITransazione>();
			ITransazione tr = null;
			try {
				while ( rs.next() ) {
					tr = new Transazione(false);
					tr.setIdTransazione(rs.getInt("idTransazione"));
					tr.setIdConto(rs.getInt("idConto"));
					tr.setIdAgente(rs.getInt("idAgente"));
					tr.setImporto(rs.getFloat("importo"));
					tr.setData(new Data(rs.getString("data")));
					((Transazione) tr).setTipoTransazione(rs.getString("tipoTransazione"));
					tt.add(tr);
				}
			} catch (NullPointerException npe ) {}
			return tt.toArray(new ITransazione[tt.size()]);
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Ritorna la lista dei movimenti registrati nel database inerenti ad un conto
	 * @param idConto l'id del conto di cui si vogliono le transazioni
	 * @return un array di {@link ITransazione} rappresentanti l'insieme di transazioni memorizzate
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public ITransazione[] getMovimenti(int idConto) throws GccbDbException {
		try {
			ResultSet rs = execProcedureWO("getUltimiMovimentiConto", new Integer(idConto));
			
			ArrayList<ITransazione> tt = new ArrayList<ITransazione>();
			ITransazione tr = null;
			try {
				while ( rs.next() ) {
					tr = new Transazione(false);
					tr.setIdTransazione(rs.getInt("idTransazione"));
					tr.setIdConto(rs.getInt("idConto"));
					tr.setIdAgente(rs.getInt("idAgente"));
					tr.setImporto(rs.getFloat("importo"));
					tr.setData(new Data(rs.getString("data")));
					((Transazione) tr).setTipoTransazione(rs.getString("tipoTransazione"));
					tt.add(tr);
				}
			} catch (NullPointerException npe ) {}
			return tt.toArray(new ITransazione[tt.size()]);
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}	

	/**
	 * Ritorna un insieme di statistiche. L'array sarà cosi costituito:
	 * <ol>
	 * <li>Numero conti: Integer</li>
	 * <li>Numero clienti: Integer</li>
	 * <li>Numero conti con fido: Integer</li>
	 * <li>Percentuale conti con fido: Float</li>
	 * <li>Numero bancomat: Integer</li>
	 * <li>Numero impiegati: Integer</li>
	 * </ol> 
	 * @return l'array contenente le statistiche
	 * @throws GccbDbException in caso di errori
	 * @throws DatiNonTrovatiException se la funzione per il calcolo delle statistiche
	 * non ritorna alcun risultato
	 */
	public Object[] getStatistiche() throws GccbDbException, DatiNonTrovatiException {
		try {
			ResultSet rs = execProcedureWO("getStatistiche");
			rs.first();
			return new Object[]{
					rs.getInt(1),
					rs.getInt(2),
					rs.getInt(3),
					rs.getFloat(4),
					rs.getInt(5),
					rs.getInt(6)
			};
		} catch (NullPointerException e) {
			throw new DatiNonTrovatiException("Statistiche non calcolabili");
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Ritorna la lista dei conti registrati nel database
	 * @return una array di {@link IContoCorrenteBancario} contente tutte le informazioni sui conti
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IContoCorrenteBancario[] getConti() throws GccbDbException {
		ArrayList<IContoCorrenteBancario> lc = new ArrayList<IContoCorrenteBancario>();
		try {
			ResultSet rs = execProcedureWO("getConti");
			while ( rs.next() ) {
				Persona p = new Persona(
						rs.getInt("idPersona"),
						rs.getString("intestatarioNome"),
						rs.getString("intestatarioCognome"),
						rs.getString("intestatarioIndirizzo")
						);
				Data d = new Data(rs.getString("ultimoMovimento"));
				ContoCorrenteBancario c;
				if (rs.getFloat("fido") > 0f) {
					c = new ContoCorrenteConFido(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), rs.getFloat("fido"), d);
				} else {
					c = new ContoCorrenteBancario(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), d);
				}
				lc.add(c);
			}
			return lc.toArray(new IContoCorrenteBancario[]{});
		} catch (NullPointerException e) {
			return lc.toArray(new IContoCorrenteBancario[]{});
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Ritorna la lista di clienti memorizzati nel database
	 * @return un array di {@link IPersona} contenenti le informazioni su tutti i clienti memorizzati
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IPersona[] getPersone() throws GccbDbException {
		ArrayList<IPersona> lc = new ArrayList<IPersona>();
		try {
			ResultSet rs = execProcedureWO("getPersone");
			while ( rs.next() ) {
				Persona p = new Persona(
						rs.getInt("idPersona"),
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("indirizzo")
						);
				lc.add(p);
			}
			return lc.toArray(new IPersona[]{});
		} catch (NullPointerException e) {
			return lc.toArray(new IPersona[]{});
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Chiude il conto indicato
	 * @param idConto id del conto da chiudere
	 * @param idAgente di dell'agente che esegue l'operazione
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public void chiudiConto(int idConto, int idAgente) throws GccbDbException {
		try {
			execProcedure("chiudiConto", idConto, idAgente);
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Aggiunge un nuovo conto con fido al database 
	 * @param idPersona l'id dell'intestatario del conto
	 * @param importo l'importo da versare come saldo
	 * @param fido il fido da associare al conto
	 * @param data una stringa sappresentante una data valida per mysql
	 * @param password la password associata al conto
	 * @param idAgente id dell'agente che esegue l'operazione
	 * @return una istanza di {@link IContoCorrenteBancario} rappresentante il nuovo conto
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IContoCorrenteBancario addConto(int idPersona, float importo, float fido, String data, String password, int idAgente) throws GccbDbException {
		try {
			ResultSet rs = execFunction("addConto", idPersona, importo, fido, data, password, idAgente);
			rs.first();
			IContoCorrenteBancario conto = getConto(rs.getInt(1));
			return conto;
		} catch (NullPointerException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		} catch (DatiNonTrovatiException e) {
			throw new GccbDbException("Errore durante l'inserimento del nuovo conto", e);
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	/**
	 * Aggiunge un nuovo conto al database
	 * @param idPersona l'id dell'intestatario
	 * @param importo l'importo da versare come saldo
	 * @param data la data di creazione del server sotto forma di stringa valida per mysql
	 * @param password la password associata al conto
	 * @param idAgente l'id dell'agente che esegue l'operazione
	 * @return una istanza di {@link IContoCorrenteBancario} rappresentante il nuovo conto
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IContoCorrenteBancario addConto(int idPersona, float importo, String data, String password, int idAgente) throws GccbDbException {
		try {
			ResultSet rs = execFunction("addConto", idPersona, importo, null, data, password, idAgente);
			rs.first();
			IContoCorrenteBancario conto = getConto(rs.getInt(1));
			return conto;
		} catch (NullPointerException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		} catch (DatiNonTrovatiException e) {
			throw new GccbDbException("Errore durante l'inserimento del nuovo conto", e);
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Aggiunge una nuova persona al database
	 * @param nome il nome della nuova persona
	 * @param cognome il cognome della nuova persona
	 * @param indirizzo l'indirizzo della nuova persona
	 * @return una istanza di {@link IPersona} rappresentante il cliente appena aggiunto
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IPersona addPersona(String nome, String cognome, String indirizzo) throws GccbDbException {
		try {
			ResultSet rs = execFunction("addPersona", nome, cognome, indirizzo);
			rs.first();
			IPersona persona = getPersona(rs.getInt(1));
			return persona;
		} catch (NullPointerException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		} catch (DatiNonTrovatiException e) {
			throw new GccbDbException("Errore durante l'inserimento del nuovo cliente", e);
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}

	/**
	 * Cerca i conti i cui intestatari hanno nome e cognome analogi a quelli indicati
	 * @param nome il nome del cliente da cercare
	 * @param cognome il cognome del cliente da cercare
	 * @param valoriSimili indica se accettare fra i risultati anche conti
	 * i cui intestatari contengono come parte delle anagrafiche) i valori indicati
	 * @return L'insieme di {@link IContoCorrenteBancario} trovati
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public IContoCorrenteBancario[] searchConti(String nome, String cognome, boolean valoriSimili) throws GccbDbException {
		if ( valoriSimili ) {
			nome = "%" + nome + "%";
			cognome = "%" + cognome + "%";
		}
		try {
			ResultSet rs = execProcedureWO("searchConti", nome, cognome);
			ArrayList<IContoCorrenteBancario> lc = new ArrayList<IContoCorrenteBancario>();
			while ( rs.next() ) {
				Persona p = new Persona(
						rs.getInt("idPersona"),
						rs.getString("intestatarioNome"),
						rs.getString("intestatarioCognome"),
						rs.getString("intestatarioIndirizzo")
						);
				Data d = new Data(rs.getString("ultimoMovimento"));
				ContoCorrenteBancario c;
				if (rs.getFloat("fido") > 0f) {
					c = new ContoCorrenteConFido(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), rs.getFloat("fido"), d);
				} else {
					c = new ContoCorrenteBancario(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), d);
				}
				lc.add(c);
			}
			return lc.toArray(new IContoCorrenteBancario[]{});
		} catch (NullPointerException e) {
			return new IContoCorrenteBancario[0];
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Ritorna l'insieme di conti associati ad una specifica persona
	 * @param idPersona l'id della persona di cui cercare i conti
	 * @return l'array di {@link IContoCorrenteBancario} rappresentanti i conti trovati
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IContoCorrenteBancario[] searchContiPersona(int idPersona) throws GccbDbException {
		try {
			ResultSet rs = execProcedureWO("getContiPersona", idPersona);
			ArrayList<IContoCorrenteBancario> lc = new ArrayList<IContoCorrenteBancario>();
			while ( rs.next() ) {
				Persona p = new Persona(
						rs.getInt("idPersona"),
						rs.getString("intestatarioNome"),
						rs.getString("intestatarioCognome"),
						rs.getString("intestatarioIndirizzo")
						);
				Data d = new Data(rs.getString("ultimoMovimento"));
				ContoCorrenteBancario c;
				if (rs.getFloat("fido") > 0f) {
					c = new ContoCorrenteConFido(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), rs.getFloat("fido"), d);
				} else {
					c = new ContoCorrenteBancario(rs.getInt("idConto"), p,
							rs.getFloat("saldo"), d);
				}
				lc.add(c);
			}
			return lc.toArray(new IContoCorrenteBancario[]{});
		} catch (NullPointerException e) {
			return new IContoCorrenteBancario[0];
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	/**
	 * Cerca i clienti che hanno nome e cognome analogi a quelli indicati
	 * @param nome il nome del cliente da cercare
	 * @param cognome il cognome del cliente da cercare
	 * @param valoriSimili indica se accettare fra i risultati anche conti
	 * i cui intestatari contengono come parte delle anagrafiche) i valori indicati
	 * @return la lista di {@link IPersona} con le persone trovate
	 * @throws GccbDbException in caso di errore durante la query
	 */
	public IPersona[] searchPersone(String nome, String cognome, boolean valoriSimili) throws GccbDbException {
		if ( valoriSimili ) {
			nome = "%" + nome + "%";
			cognome = "%" + cognome + "%";
		}
		try {
			ResultSet rs = execProcedureWO("searchPersone", nome, cognome);
			ArrayList<IPersona> lc = new ArrayList<IPersona>();
			while ( rs.next() ) {
				Persona p = new Persona(
						rs.getInt("idPersona"),
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("indirizzo")
						);
				lc.add(p);
			}
			return lc.toArray(new IPersona[]{});
		} catch (NullPointerException e) {
			return new IPersona[0];
		} catch (SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
}
