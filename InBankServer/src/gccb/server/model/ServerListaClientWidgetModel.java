package gccb.server.model;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fi.mmm.yhteinen.swing.core.YModel;
import gccb.server.net.ServerServiceDelegate;

/**
 * Modello del widget della lista clienti
 * @author Francesco Capozzo
 *
 */
public class ServerListaClientWidgetModel extends YModel {

	private Object LOCK = new Object();
	private Timer refreshTimer = new Timer();
	
	private List<ServerClient> listaClient;
	private ServerClient client;
	
	/**
	 * Aggiorna la lista dei client connessi e resetta il timer
	 * di aggiornamento.
	 * La lista verrà riaggiornata ogni 5 minuti dall'esecuzione della funzione
	 */
	public void aggiornaListaClient() {
		synchronized (LOCK) {
			refreshTimer.cancel();
			refreshTimer = new Timer();
			setListaClient(ServerServiceDelegate.listaClientConnessi());
			// ripete l'azione ogni 5 minuti azzerando le richieste in attesa
			refreshTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					aggiornaListaClient();
				}
			}, 5 * 60 * 1000, 5 * 60 * 1000);
		}
	}
	
	/**
	 * Blocca il timer di autoaggiornamento della lista dei client
	 */
	public void stopAutoRefresh() {
		refreshTimer.cancel();
	}

	/**
	 * Disconnette il client indicato da {@link #getClient()}
	 */
	public void disconnettiClient() {
		ServerServiceDelegate.disconnettiClient(client);
	}
	
	/**
	 * Restituisce la lista dei client connessi
	 * @return una lista di {@link ServerClient}
	 */
	public List<ServerClient> getListaClient() {
		return listaClient;
	}

	/**
	 * Imposta la lista di client connessi
	 * @param listaClient una nuova lista client
	 */
	public void setListaClient(List<ServerClient> listaClient) {
		this.listaClient = listaClient;
		notifyObservers("listaClient");
	}

	/**
	 * Indica il client selezionato
	 * @return un client collegato
	 */
	public ServerClient getClient() {
		return client;
	}

	/**
	 * Imposta un nuovo client come selezionato
	 * @param client il nuovo client
	 */
	public void setClient(ServerClient client) {
		this.client = client;
	}
}
