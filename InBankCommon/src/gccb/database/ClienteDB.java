package gccb.database;

import gccb.ContoCorrenteBancario;
import gccb.ContoCorrenteConFido;
import gccb.Data;
import gccb.IContoCorrenteBancario;
import gccb.ITransazione;
import gccb.Persona;
import gccb.Transazione;
import gccb.exception.db.DatiNonTrovatiException;
import gccb.exception.db.GccbDbException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Wrapper per le funzioni del DB accessibili dal client Cliente 
 * @author Francesco Capozzo
 * @author Michele Doronzo
 *
 */
public class ClienteDB extends RemoteProcedureAccessor {

	static private ClienteDB singleton = null;
	
	/**
	 * Costruttore private della classe
	 * @throws GccbDbException se la connessione al database non è stato inizializzata
	 */
	private ClienteDB() throws GccbDbException {
		if ( DbAccess.getConnection() == null) {
			throw new GccbDbException("Database non inizializzato");
		}
	}
	
	/**
	 * Ritorna l'istanza di default della classe
	 * @return una istanza di {@link ClienteDB}
	 * @throws GccbDbException se la connessione non è stata inizializzata
	 */
	public static ClienteDB getReference() throws GccbDbException {
		if ( singleton == null ) {
			singleton = new ClienteDB();
		}
		return singleton;
	}
	
	/**
	 * Esegue un bonifico fra due conti registrati
	 * @param idContoOrigine l'id del conto di origine
	 * @param idContoDestinazione l'id del conto di destinazione
	 * @param importo l'importo del bonifico
	 * @param idAgente l'agente che effettua il bonifico
	 * @return l'esito dell'operazione
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public boolean doBonifico(int idContoOrigine, int idContoDestinazione ,float importo, int idAgente) throws GccbDbException {
		try {
			ResultSet rs = execProcedureWO("doBonifico", new Integer(idContoOrigine), new Integer(idContoDestinazione), new Float(importo), new Integer(idAgente));
			rs.first();
			return rs.getBoolean("risultato");
		} catch ( SQLException e ) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
	
	/**
	 * Preleva gli ultimi movimenti registrati relativi ad un conto
	 * @param idConto l'id del conto del quale si è interessati alle informazioni
	 * @return un array di {@link ITransazione} con le informazioni relative a tutte le
	 * transazioni individuate
	 * @throws GccbDbException in caso di errori durante la query
	 */
	public ITransazione[] getUltimiMovimenti(int idConto) throws GccbDbException {
		
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
			return tt.toArray(new ITransazione[]{});
		} catch ( SQLException e ) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
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
		} catch ( SQLException e) {
			throw new GccbDbException("Errore durante l'operazione", e);
		}
	}
}
