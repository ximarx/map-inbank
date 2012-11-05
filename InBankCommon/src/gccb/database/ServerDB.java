package gccb.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gccb.IImpiegato;
import gccb.Impiegato;
import gccb.exception.db.GccbDbException;
import gccb.net.rmi.RMIClientType;

/**
 * Wrapper per le funzioni del DB accessibili dal SERVER
 * @author Francesco Capozzo
 * @author Michele Doronzo
 *
 */
public class ServerDB extends RemoteProcedureAccessor {

	static private ServerDB singleton = null;
	
	/**
	 * Costruttore privato di classe. Realizza il pattern singleton
	 * @throws DatabaseException se la connessione al server non è stata inizializzata
	 */
	private ServerDB() throws GccbDbException {
		if ( DbAccess.getConnection() == null) {
			throw new GccbDbException("Connessione al database non inizializzata");
		}
	}
	
	/**
	 * Ritorna l'istanza di default della classe. Realizza il pattern singleton
	 * @return L'istanza di default della classe
	 * @throws DatabaseException
	 */
	public static ServerDB getReference() throws GccbDbException {
		if ( singleton == null ) {
			singleton = new ServerDB();
		}
		return singleton;
	}
	
	/**
	 * Controlla che la coppia id-chiave sia valida per il tipo di client specificato
	 * @param id lo username
	 * @param chiave la password
	 * @param ctype il tipo di client
	 * @return l'id agente associato allo username
	 * @throws GccbDbException in caso di errore 
	 */
	public int checkAuth(String id, String chiave, int ctype) throws GccbDbException {
		try {
			if ( ctype == RMIClientType.BANCOMAT.ordinal() ) {
				// uso checkAuthBancomat
				ResultSet rs = execFunction("checkAuthBancomat", new Integer(id), chiave);
				if ( rs.first() ) {
					return rs.getInt(1);
				} else {
					throw new GccbDbException("checkAuthBancomat ritorna valori non attesi");
				}
			} else if ( ctype == RMIClientType.UTENTE.ordinal() ) {
				// uso checkAuthCliente
				ResultSet rs = execFunction("checkAuthCliente", new Integer(id), chiave);
				if ( rs.first() ) {
					return rs.getInt(1);
				} else {
					throw new GccbDbException("checkAuthClient ritorna valori non attesi");
				}
			} else if ( ctype == RMIClientType.IMPIEGATO.ordinal() ) {
				// uso checkAuthImpiegato
				ResultSet rs = execFunction("checkAuthImpiegato", id, chiave);
				if ( rs.first() ) {
					return rs.getInt(1);
				} else {
					throw new GccbDbException("checkAuthImpiegato ritorna valori non attesi");
				}
			} else {
				throw new GccbDbException("Tipo client non supportato");
			}
		} catch (SQLException e) {
			throw new GccbDbException(e);
		} catch (NumberFormatException e ) {
			throw new GccbDbException("Dati non validi");
		}
	}
	
	/**
	 * Restituisce la lista di impiegati registrati
	 * @return la lista di impiegati
	 * @throws SQLException in caso di errore durante la query
	 */
	public List<IImpiegato> getImpiegati() throws SQLException {
		ResultSet rs = execProcedureWO("getImpiegati");
		
		ArrayList<IImpiegato> tt = new ArrayList<IImpiegato>();
		IImpiegato tr = null;
		try {
			while ( rs.next() ) {
				tr = new Impiegato(
						rs.getInt("idImpiegato"),
						rs.getString("username"),
						"",
						rs.getString("visualizzato")
						);
				tt.add(tr);
			}
		} catch (NullPointerException npe ) {}
		return tt;
	}
	
	/**
	 * Modifica la password associata all'impiegato
	 * @param idImpiegato l'id dell'impiegato
	 * @param newPassword la nuova password dell'impiegato
	 * @throws SQLException in caso di errore durante la query
	 */
	public void modificaPasswordImpiegato(int idImpiegato, String newPassword) throws SQLException {
		execProcedure("modificaPasswordImpiegato", idImpiegato, newPassword);
	}
	
	/**
	 * Aggiunge un nuovo impiegato al database 
	 * @param nome username dell'impiegato
	 * @param password password dell'impiegato
	 * @param visualizzato etichetta dell'impiegato
	 * @return esito dell'operazione
	 * @throws SQLException in caso di errore durante la query
	 */
	public boolean aggiungiImpiegato(String nome, String password, String visualizzato) throws SQLException {
		ResultSet rs = execFunction("addImpiegato", nome, password, visualizzato);
		try {
			if ( rs.first() ) {
				return rs.getBoolean(1);
			} else {
				return false;
			}
		} catch (NullPointerException npe) {
			return false;
		}
	}
	
	/**
	 * Rimuove l'impiegato dalla lista degli autorizzati
	 * @param idImpiegato l'id dell'impiegato da rimuovere
	 * @throws SQLException in caso di errore durante la query
	 */
	public void rimuoviImpiegato(int idImpiegato) throws SQLException {
		execProcedure("rimuoviImpiegato", idImpiegato);
	}
	
    /**
     * Esegue il dump del database in una stringa
     * @return la stringa SQL
     * @throws GccbDbException in caso di errori durante la lettura del database
     */
	public String dumpDB() throws GccbDbException {
		// Default to not having a quote character
		DatabaseMetaData dbMetaData = null;
		Connection dbConn = DbAccess.getConnection();
		try {
			dbMetaData = dbConn.getMetaData();
			StringBuffer result = new StringBuffer();
			
			result.append("/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;\n");
			result.append("/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;\n");
			result.append("/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;\n");
			result.append("/*!40101 SET NAMES utf8 */;\n");
			
			result.append("/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;\n");
			result.append("/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;\n");
			result.append("/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;\n");

			ResultSet rs = dbMetaData.getTables(null,null,null,null);
			if (!rs.next()) {
				rs.close();
			} else {
				do {
					String tableName = rs.getString("TABLE_NAME");
					String tableType = rs.getString("TABLE_TYPE");
					if ("TABLE".equalsIgnoreCase(tableType)) {
						dumpTable(dbConn, result, tableName);
					}
				} while (rs.next());
				rs.close();
			}
			
			result.append("\n\n");
			result.append("/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;\n");
			result.append("/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;\n");
			result.append("/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;\n");
			result.append("/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n");
			result.append("/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;\n");
			result.append("/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;\n");
			result.append("/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n");
			return result.toString();
		} catch (Exception e) {
			throw new GccbDbException("Errore durante la lettura del database: " + e.getMessage(), e);
		}
	}

	/**
	 * Esegue il dump dei dati di una particolare tabella
	 * @param dbConn la connessione al database
	 * @param result lo string buffer in cui inserire il dump della tabella
	 * @param tableName il nome della tabella di cui fare il dump
	 * @throws GccbDbException in caso di errori durante il backup
	 */
	private void dumpTable(Connection dbConn, StringBuffer result,
			String tableName) throws GccbDbException {
		try {
			PreparedStatement stmt = dbConn.prepareStatement("SELECT * FROM "
					+ tableName);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			result.append("\n\n-- Data for " + tableName + "\n");
			while (rs.next()) {
				result.append("INSERT INTO " + tableName + " VALUES (");
				for (int i = 0; i < columnCount; i++) {
					if (i > 0) {
						result.append(", ");
					}
					Object value = rs.getObject(i + 1);
					if (value == null) {
						result.append("NULL");
					} else {
						String outputValue = value.toString();
						outputValue = outputValue.replaceAll("'", "\\'");
						result.append("'" + outputValue + "'");
					}
				}
				result.append(");\n");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new GccbDbException("Impossibile effettuare il backup della tabella " + tableName +": " + e.getMessage(), e);
		}
	}	
}
