package com.fivecents.backends.clientbrandnew.enterprise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import com.fivecents.backends.clientbrandnew.enterprise.beans.Client;
import com.fivecents.backends.clientbrandnew.enterprise.events.EnterpriseEvent;
import com.fivecents.backends.clientbrandnew.enterprise.events.EnterpriseEventNotifier;
import com.fivecents.backends.clientbrandnew.enterprise.loader.ClientStoreLoader;

/**
 * This service bean is the enterprise tier to access to clients in the system.
 * 
 * @author Laurent CAILLETEAU
 */
@Interceptors({	EnterpriseInterceptor.class })
@Named
@ApplicationScoped
public class ClientEnterpriseService {
	private List<Client> clients;
	
	@Inject
	private ClientStoreLoader clientResourceLoader;
	
	@Inject
	private EnterpriseEventNotifier enterpriseEventNotifier;
	
	@PostConstruct
	private void init() {
		clients = clientResourceLoader.loadClientsFromResource("clients.xml");
	}
	
	/**
	 * Return all clients by an order, and with a pagination.
	 * @return
	 */
	public Map<String, Object> getAllClients(String orderBy, int page, int perPage) {
		Map<String, Object> result = new HashMap<>();
		
		// Let's order the list, if required.
		if (orderBy == null) {
			orderBy = "id";
		}
		final String orderByToUse = orderBy;
		clients
			.sort((c1, c2) -> {
				switch(orderByToUse) {
					case "lastname":
						return c1.getLastName().compareTo(c2.getLastName());
					case "firstname":
						return c1.getFirstName().compareTo(c2.getFirstName());
					case "birthdate":
						return c1.getBirthDate().compareTo(c2.getBirthDate());
					default:
						return c1.getId() - c2.getId();
				}
			});
	
		// Now we can extract the required clients.
		int startingIndex = (page-1)*perPage;
		if (startingIndex < 0) { startingIndex = 0; }
		if (startingIndex > clients.size()-1) { startingIndex = clients.size()-1; }
		
		int endingIndex = Math.min(clients.size(), page*perPage);
		if (endingIndex < 0) { endingIndex = 0; }
		if (endingIndex > clients.size()) { endingIndex = clients.size(); }
		
		if (startingIndex > endingIndex) { endingIndex = startingIndex; }
		
		// Returns result.
		result.put("clients", clients.subList(startingIndex, endingIndex));
		result.put("total", clients.size());
		return result;
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
		client.setId(clients.size());
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
