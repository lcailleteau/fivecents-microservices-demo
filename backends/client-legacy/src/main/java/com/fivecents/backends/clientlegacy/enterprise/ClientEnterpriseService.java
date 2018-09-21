package com.fivecents.backends.clientlegacy.enterprise;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fivecents.backends.clientlegacy.enterprise.beans.Client;
import com.fivecents.backends.clientlegacy.loader.ClientStore;
import com.fivecents.enterprise.loader.GenericXmlStoreLoader;

/**
 * This service bean is the enterprise tier to access to clients in the system.
 * 
 * @author Laurent CAILLETEAU
 */
@Named
@ApplicationScoped
public class ClientEnterpriseService {
	private List<Client> clients;
	
	@Inject
	private GenericXmlStoreLoader storeLoader;
	
	@PostConstruct
	private void init() {
		// Let's initialize our clients from xml resource.
		ClientStore clientStore = storeLoader.loadFromResource("clients.xml", ClientStore.class);
		clients = clientStore.getClients();
	}
	
	/**
	 * Return all clients by an order, and with a pagination.
	 * @return
	 */
	public List<Client> getAllClients() {
		return clients;
	}
	
	/**
	 * Search for a client.
	 * @param id
	 * @return
	 */
	public Client searchForClient(int id) {
		// Search client.
		Client searchedClient =
			clients
				.stream()
				.filter(client -> client.getId() == id)
				.findAny()
				.orElse(null);		
		return searchedClient;
	}
	
	/**
	 * Create a client.
	 */
	public Client createClient(Client client) {
		clients.add(client);
		return client;
	}
	
	/**
	 * Delete a client.
	 */
	public boolean removeClient(int clientId) {
		return clients.removeIf(c -> c.getId() == clientId);
	}
	
	/**
	 * Update a client.
	 */
	public boolean updateClient(int clientId, Client client) {
		client.setId(clientId);
		Client registeredClient = searchForClient(clientId);
		if (registeredClient == null) {
			return false;
		}
		clients.remove(registeredClient);
		clients.add(client);
		return true;
	}
}
