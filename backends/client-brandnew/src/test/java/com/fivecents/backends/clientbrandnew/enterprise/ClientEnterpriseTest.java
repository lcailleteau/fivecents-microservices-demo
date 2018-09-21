package com.fivecents.backends.clientbrandnew.enterprise;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.constraints.AssertTrue;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fivecents.backends.clientbrandnew.enterprise.beans.Client;
import com.fivecents.backends.clientbrandnew.enterprise.loader.ClientStoreLoader;

/**
 * Unit test for the enterprise client service.
 * 
 * @author Laurent CAILLETEAU
 */
@RunWith(CdiRunner.class)
@AdditionalClasses(ClientStoreLoader.class) 
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
		Map<String, Object> clientsMap = clientEnterprise.getAllClients("lastname", 2, 3);
		List<Client> clients = (List<Client>) clientsMap.get("clients");
		int clientsSize = clients.size();
		
		// Let's check that clients are loaded.
		assertTrue(clients.size() > 0);
		
		// Let's assert that every client has the correct id.
		int iteration = 0;
		for (Client client : clients) {
			if (client.getId() != iteration) {
				fail("A client was found with an incorrect id : " + client.getId());
			}
			iteration++;
		}
		
		// We can also check that the creation of a client went well.
		Client newClient = new Client();
		newClient.setFirstName("Martin");
		newClient.setLastName("PICHON");
		Client newUpdatedClient = clientEnterprise.createClient(newClient);		
		assertTrue(clients.size() == clientsSize + 1);
		assertTrue(clients.size() == newUpdatedClient.getId() + 1);
		
		// Now, let's remove a given client.
		boolean clientRemoved = clientEnterprise.removeClient(1);
		assertTrue(clientRemoved);
		assertTrue(clients.size() == clientsSize);
		
		// Finally, we can try to update a client.
		int clientIdToUpdate = 2;
		Client updatedClient = new Client();
		updatedClient.setLastName("FORTIN");
		updatedClient.setFirstName("Fabrice");
		boolean clientUpdated = clientEnterprise.updateClient(clientIdToUpdate, updatedClient);
		assertTrue(clientUpdated);
		Client updatedClient2 = clientEnterprise.searchForClient(clientIdToUpdate);
		assertTrue("FORTIN".equals(updatedClient2.getLastName()) && "Fabrice".equals(updatedClient2.getFirstName()));
	}
}
