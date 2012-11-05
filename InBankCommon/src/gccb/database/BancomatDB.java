package gccb.database;

import gccb.Bancomat;
import gccb.ContoCorrenteBancario;
import gccb.ContoCorrenteConFido;
import gccb.Data;
import gccb.IBancomat;
import gccb.IContoCorrenteBancario;
import gccb.Persona;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.db.GccbDbException;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Wrapper per le funzioni del DB accessibili dal client BANCOMAT 
 * @author Francesco Capozzo
 * @author Michele Doronzo
 *
 */
public class BancomatDB extends RemoteProcedureAccessor {

	/**
	 * Istanza di default
	 */
	static private BancomatDB singleton = null;
	
	/**
	 * Costruttore privato
	 * @throws DatabaseException se la connessione al database non è inizializzata
	 */
	private BancomatDB() throws GccbDbException {
		if ( DbAccess.getConnection() == null) {
			throw new GccbDbException("Connessione al database non inizializzata");
		}
	}
	
	/**
	 * Ritorna una istanza dell'oggetto. E' una implementazione del
	 * pattern singleton
	 * @return l'istanza stardard dell'oggetto
	 * @throws DatabaseException Se la connessione non è inizializzata
	 */
	public static BancomatDB getReference() throws GccbDbException {
		if ( singleton == null ) {
			singleton = new BancomatDB();
		}
		return singleton;
	}
	
	/**
	 * Esegue un prelievo. Il conto da cui prelevare l'importo è ricavato dall'idAgente
	 * del bancomat 
	 * @param idAgente L'agente che esegue il prelievo
	 * @param importo l'importo da prelevare
	 * @return esito del prelievo
	 * @throws GccbDbException in caso di errore nella query
	 */
	public boolean doPrelievo(int idAgente, float importo) throws GccbDbException {
		try {
			ResultSet rs = execFunction("doPrelievo", idAgente, importo);
			rs.first();
			return rs.getBoolean(1);
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	/**
	 * Preleva le informazioni riguardanti il conto associato all'idAgente del bancomat
	 * @param idAgente l'id agente del bancomat
	 * @return una istanza di {@link IContoCorrenteBancario} con le informazioni
	 * relative al conto associato al bancomat
	 * @throws GccbDbException Se il conto non viene trovato o in caso di errore durante la query
	 */
	public IContoCorrenteBancario doSaldo(int idAgente) throws GccbDbException, DatiNonTrovatiException {
		try {
			ResultSet rs = execProcedureWO("getContoBancomat", idAgente);
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
	 * Preleva le informazioni relative al bancomat associato al'idAgente specificato
	 * @param idAgente del bancomat
	 * @return una istanza di {@link IBancomat} che rappresenta
	 * il bancomat che possiede l'idAgente specificato
	 * @throws GccbDbException se nessun bancomat viene trovato o in caso di errore
	 */
	public IBancomat getBancomat(int idAgente) throws GccbDbException, DatiNonTrovatiException {
		try {
			IContoCorrenteBancario c = doSaldo(idAgente);
			ResultSet rs = execProcedureWO("getBancomat", idAgente);
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
}
