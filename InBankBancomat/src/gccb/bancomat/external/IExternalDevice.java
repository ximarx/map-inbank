package gccb.bancomat.external;

/**
 * Interfaccia implementata dai driver per 
 * le periferiche esterne.
 * Permette al {@link BancomatExternalDeviceController} di
 * controllare il dispositivo esterno
 * @author Francesco Capozzo
 *
 */
public interface IExternalDevice {

	/**
	 * Memorizza una istanza del controller
	 * della periferica esterna
	 * @param controller
	 */
	void setDriverController(BancomatExternalDeviceController controller);
	/**
	 * Inizializza il dispositivo per accettare l'inserimento
	 * di una nuova carta bancomat
	 * Rimuove i blocchi (se presenti) del dispositivo
	 */
	void inizializza();
	/**
	 * Rilascia la carta bancomat trattenuta dal dispositivo
	 * @throws ExternalDeviceException in caso di errore
	 */
	void rilasciaCarta() throws ExternalDeviceException;
	/**
	 * Trattiene la carta bancomat in seguito a numerosi errori
	 * in fare di autenticazione
	 * @throws ExternalDeviceException in caso di errore
	 */
	void trattieniCarta() throws ExternalDeviceException;
	/**
	 * Stampa lo scontrino di estratto conto
	 * @param testo il testo dello scontrino
	 * @throws ExternalDeviceException in caso di errori
	 */
	void stampaScontrino(String testo) throws ExternalDeviceException;
	/**
	 * Emette l'importo indicato di banconote
	 * @param importo l'importo da emettere
	 * @throws ExternalDeviceException in caso di errore
	 */
	void emettiBanconote(float importo) throws ExternalDeviceException;
	/**
	 * Attiva la periferica e permette l'inserimento di una nuova
	 * carta bancomat
	 */
	void attiva();
	/**
	 * Disattiva il dispositivo impedendo l'inserimento
	 * di una nuova carta bancomat
	 */
	void disattiva();
	/**
	 * Controlla se il dispositivo è in grado di accettare
	 * l'inserimento di una nuova carta bancomat
	 * @return l'esito del controllo
	 */
	boolean isAttivo();
	
}
