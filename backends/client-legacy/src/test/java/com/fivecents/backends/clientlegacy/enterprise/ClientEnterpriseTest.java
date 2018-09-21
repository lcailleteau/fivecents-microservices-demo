package com.fivecents.backends.clientlegacy.enterprise;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fivecents.backends.clientlegacy.enterprise.ClientEnterpriseService;
import com.fivecents.backends.clientlegacy.enterprise.beans.Address;
import com.fivecents.backends.clientlegacy.enterprise.beans.Client;
import com.fivecents.enterprise.loader.GenericXmlStoreLoader;

/**
 * Unit test for the enterprise client service.
 * 
 * @author Laurent CAILLETEAU
 */
@RunWith(CdiRunner.class)
@AdditionalClasses({ GenericXmlStoreLoader.class }) 
public class ClientEnterpriseTest {
	@Inject
	private ClientEnterpriseService clientEnterprise;
	
	/**
	 * Main test operation. We are using CDIUnit here, other possibility
	 * could have been JeeUnit.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testClientServices() throws Exception {
		// Get all clients.
		List<Client> clients = clientEnterprise.getAllClients();
		int clientsSize = clients.size();
		
		// Let's check that clients are loaded.
		assertTrue(clients.size() > 0);
		
		// We can also check that the creation of a client went well.
		Client newClient = new Client();
		Address newAddress = new Address();
		newAddress.setStreet("6 rue du panorama");
		newAddress.setCity("Chemire le Gaudin");
		newAddress.setZip("72210");
		newAddress.setCountry("FRANCE");
		newClient.setId(2000);
		newClient.setAddress(newAddress);
		Client newUpdatedClient = clientEnterprise.createClient(newClient);
		
		// To avoid ConcurrentModificationException on the client list operations,
		// we need to get it back.
		clients = clientEnterprise.getAllClients();

		assertTrue(clients.size() == clientsSize + 1);
		
		// Now, let's remove a given client.
		boolean clientRemoved = clientEnterprise.removeClient(2000);
		assertTrue(clientRemoved);
		
		// To avoid ConcurrentModificationException on the client list operations,
		// we need to get it back.
		clients = clientEnterprise.getAllClients();

		assertTrue(clients.size() == clientsSize);
		
		/*
		// Finally, we can try to update a client.
		int clientIdToUpdate = 2;
		Client updatedClient = new Client();
		updatedClient.setLastName("FORTIN");
		updatedClient.setFirstName("Fabrice");
		boolean clientUpdated = clientEnterprise.updateClient(clientIdToUpdate, updatedClient);
		assertTrue(clientUpdated);
		Client updatedClient2 = clientEnterprise.searchForClient(clientIdToUpdate);
		assertTrue("FORTIN".equals(updatedClient2.getLastName()) && "Fabrice".equals(updatedClient2.getFirstName()));
		*/
	}
}
