package gccb.net.rmi.services;

import java.rmi.Remote;

import javax.management.remote.rmi.RMIServer;

/**
 * Interfaccia che ogni servizio deve implementare perchè
 * sia consentita la registrazione tramite {@link RMIServer}
 * @author Francesco Capozzo
 *
 */
public interface IRMIService extends Remote {}
