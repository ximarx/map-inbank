package gccb.server.model;

import java.util.ArrayList;
import java.util.List;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.net.rmi.RMIClientType;
import gccb.server.net.ServerServiceDelegate;

/**
 * Modello del widget per il pannello di gestione servizi
 * @author Francesco Capozzo
 *
 */
public class ServerServiziWidgetModel extends YModel {

	private List<ServerService> servizi;
	private ServerService servizio;
	
	/**
	 * Inizializza la nuova lista servizi caricandoli dalla classpath
	 * e notifica gli osservatori
	 */
	public void inizializzaServizi() {
		servizi = new ArrayList<ServerService>();
		String[] sServizi = ServerServiceDelegate.getRmiRegistrableService();
		for ( String sServizio : sServizi ) {
			servizi.add(new ServerService(sServizio, ServerService.Stati.NON_ATTIVO));
		}
		notifyObservers("servizi");
	}
	
	/**
	 * Aggiorna lo stato dei servizi trovati e notifica
	 */
	public void aggiornaStatoServizi() {
		for ( ServerService cServizio : getServizi() ) {
			if ( ServerServiceDelegate.isAttivo(cServizio.getNome()) ) {
				cServizio.setStato(ServerService.Stati.ATTIVO);
				cServizio.setTipo(ServerServiceDelegate.tipoAttivazione(cServizio.getNome()));
			} else {
				cServizio.setStato(ServerService.Stati.NON_ATTIVO);
				cServizio.setTipo(null);
			}
		}
		notifyObservers("servizi");
		notifyObservers("sevizio");
	}
	
	/**
	 * Attiva il servizio indicato da {@link #getServizio()}
	 * @param modalita la modalità di attivazione
	 * @throws Exception in caso di errore durante l'attivazione
	 */
	public void attivaServizio(RMIClientType modalita) throws Exception {
		ServerServiceDelegate.attivaServizio(servizio.getNome(), modalita);
		servizio.setStato(ServerService.Stati.ATTIVO);
		servizio.setTipo(modalita);
		notifyObservers("servizio");
	}
	
	/**
	 * Disattiva il servizio indicato da {@link #getServizio()}
	 */
	public void disattivaServizio() {
		ServerServiceDelegate.disattivaServizio(servizio.getNome());
	}

	/**
	 * Preleva la lista dei servizi trovati
	 * @return la lista di {@link ServerService}
	 */
	public List<ServerService> getServizi() {
		return servizi;
	}

	/**
	 * Imposta una nuova lista di servizi
	 * @param servizi lanuova lista
	 */
	public void setServizi(List<ServerService> servizi) {
		this.servizi = servizi;
		notifyObservers("servizio");
	}

	/**
	 * Ritorna il servizio selezionato
	 * @return il servizio
	 */
	public ServerService getServizio() {
		return servizio;
	}

	/**
	 * Imposta un nuovo servizio come selezionato
	 * @param servizio il nuovo servizio
	 */
	public void setServizio(ServerService servizio) {
		this.servizio = servizio;
		notifyObservers("servizio");
	}
}
