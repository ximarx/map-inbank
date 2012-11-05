package gccb.database;


import gccb.exception.db.GccbDbException;
import gccb.log.LogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe Helper per la gestione della connessione al database
 * @author Francesco Capozzo
 * @author Michele Doronzo
 *
 */
public class DbAccess {

	static private Connection conn;
	
	static private String SERVER = "";
	static private String DATABASE = "";
	static private String USER_ID = "";
	static private String PASSWORD = "";
	
	private DbAccess() { }
	
	/**
	 * Inizializza la connessione al server mysql in base ai dati specificati
	 * @param SERVER indirizzo del server
	 * @param DATABASE nome del database
	 * @param USER_ID username
	 * @param PASSWORD password 
	 * @throws GccbDbException in caso di errori durante la connessione
	 */
	static public void initializeConnection(String SERVER, String DATABASE, String USER_ID, String PASSWORD) throws GccbDbException {
		DbAccess.SERVER = SERVER;
		DbAccess.DATABASE = DATABASE;
		DbAccess.USER_ID = USER_ID;
		DbAccess.PASSWORD = PASSWORD;
		initializeConnection();
	}
	
	/**
	 * Inizializza la connessione al database
	 * @return l'esito della connessione
	 * @throws GccbDbException in caso di errore durante la connessione
	 */
	static private boolean initializeConnection() throws GccbDbException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			LogManager.error("Errore durante la connessione a database: driver mancante.\n" +
					"Errore:"+e.getMessage());			
			throw new GccbDbException("Driver per la connessione al database non trovato (Mysql-jdbc)", e);
		}
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://"+SERVER+"/"+DATABASE,
					USER_ID,
					PASSWORD
				);
			LogManager.info("Connessione al database inizializzata");
			return true;
		} catch (SQLException sqle) {
			LogManager.error("Errore durante la connessione a database.\n" +
					"Errore:"+sqle.getMessage());			
			throw new GccbDbException("Errore durante il tentativo di connessione al server", sqle);
		}
		
	}
	/**
	 * Ritorna la connessione al database
	 * @return una istanza di {@link Connection}
	 */
	static Connection getConnection() {
		return conn;
	}
	/**
	 * Chiude la connessione al server e ritorna l'esito dell'operazione
	 * @return l'esito dell'operazione di chiusura della connessione
	 */
	static public boolean closeConnection() {
		try {
			conn.close();
			LogManager.info("Connessione al database interrotta");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * Indica se la connessione al database è attiva
	 * @return true se la connessione è attiva
	 */
	static public boolean isConnected() {
		try {
			return (conn != null && !conn.isClosed());
		} catch (SQLException e) {
			return false;
		}
	}
}
