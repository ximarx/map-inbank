package gccb.database;

import gccb.log.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe astratta per l'accesso alle stored procedure e stored function del database
 * @author Francesco Capozzo
 * @author Michele Doronzo
 *
 */
abstract class RemoteProcedureAccessor {

	/**
	 * Controlla se il conto specificato è stato chiuso
	 * @param idConto il conto da controllare
	 * @return true se il conto è chiuso
	 * @throws SQLException in caso di errore durante la query
	 */
	public boolean isContoChiuso(int idConto) throws SQLException {
		ResultSet rs = execFunction("isChiuso", new Integer(idConto));
		if ( rs.first() ) {
			return rs.getBoolean(1);
		} else {
			return true;
		}
	}

	/**
	 * Controlla se il conto specificato esiste
	 * @param idConto il conto da controllare
	 * @return true se il conto esiste
	 * @throws SQLException in caso di errore durante la query
	 */
	public boolean existConto(int idConto) throws SQLException {
		ResultSet rs = execFunction("existConto", new Integer(idConto));
		if ( rs.first() ) {
			return rs.getBoolean(1);
		} else {
			return false;
		}
	}

	/**
	 * Controlla se è possibili effettuare l'addebito dell'importo specificato sul conto
	 * @param idConto id del conto
	 * @param importo valore dell'importo da addebitare
	 * @return true se l'importo è addebitabile
	 * @throws SQLException in caso di errore durante la query
	 */
	public boolean isAddebitoPossibile(int idConto, float importo) throws SQLException {
		ResultSet rs = execFunction("isAddebitoPossibile", new Integer(idConto), new Float(importo));
		if ( rs.first() ) {
			return rs.getBoolean(1);
		} else {
			return false;
		}
	}
	
	/**
	 * Esegue la stored procedure specificata con l'elenco dei parametri specificato
	 * tralasciando i valori di ritorno
	 * @param procedureName il nome delle stored procedure da chiamare
	 * @param param l'elenco dei parametri della procedura
	 * @throws SQLException in caso di errore durante la query
	 */
	protected void execProcedure(String procedureName, Object...param ) throws SQLException {
		try {
			Statement sql = DbAccess.getConnection().createStatement();
			sql.executeUpdate("CALL " +procedureName + "(" + createSqlString(param) + ")");
		} catch (SQLException e) {
			LogManager.error("Errore durante richiesta a database\n" +
					"Procedura: "+procedureName+"\n" +
					"Parametri: " + createSqlString(param) + "\n" +
					"Errore: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Esegue la stored procedure specificata con l'elenco dei parametri specificato
	 * restituendo il resultset corrispondente alla query
	 * @param procedureName il nome delle stored procedure da chiamare
	 * @param param l'elenco dei parametri della procedura
	 * @throws SQLException in caso di errore durante la query
	 */
	protected ResultSet execProcedureWO(String procedureName, Object...param ) throws SQLException {
		try {
			Statement sql = DbAccess.getConnection().createStatement();
			return sql.executeQuery("CALL " +procedureName + "(" + createSqlString(param) + ")");
		} catch (SQLException e) {
			LogManager.error("Errore durante richiesta a database\n" +
					"Procedura(WO): "+procedureName+"\n" +
					"Parametri: " + createSqlString(param) + "\n" +
					"Errore: " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Esegue la stored function specificata con l'elenco dei parametri specificato
	 * restituendo il resultset corrispondente alla query
	 * @param functionName il nome delle stored function da chiamare
	 * @param param l'elenco dei parametri della procedura
	 * @throws SQLException in caso di errore durante la query
	 */
	protected ResultSet execFunction(String functionName, Object...param ) throws SQLException {
		try {
			Statement sql = DbAccess.getConnection().createStatement();
			return sql.executeQuery("SELECT " +functionName + "(" + createSqlString(param) + ")");
		} catch (SQLException e) {
			LogManager.error("Errore durante richiesta a database\n" +
					"Funzione: "+functionName+"\n" +
					"Parametri: " + createSqlString(param) + "\n" +
					"Errore: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Prepara la stringa di parametri da un array di oggetti generici
	 * @param param l'array di oggetti
	 * @return la stringa formattata
	 */
	private String createSqlString(Object...param) {
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < param.length; i++) {
			Object o = param[i];
			if ( i != 0 )
				sb.append(",");
			try {
				if ( o.getClass() == String.class ) {
					sb.append(" '"+(String) o+"'");
				} else if (o.getClass() == Integer.class || o.getClass() == Float.class ) {
					sb.append(" "+ o);
				}
			} catch (NullPointerException npe) {
				sb.append(" "+ "NULL");
			}
		}
		return sb.toString();
	}
}
