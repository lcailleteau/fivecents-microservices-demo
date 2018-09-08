package com.fivecents.backends.clientbrandnew.enterprise.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.fivecents.backends.clientbrandnew.enterprise.beans.Client;

/**
 * Utility bean to pre-load clients from an xml file.
 * 
 * @author Laurent CAILLETEAU
 */
@Named
@ApplicationScoped
public class ClientStoreLoader {
	/**
	 * Search service.
	 * @param id
	 * @return
	 */
	public List<Client> loadClientsFromResource(String resourceFileName) {
		List<Client> clients = new ArrayList<>();
		
		// Loads the resource file.
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);
		
		// Extract JAXB objects from the resource InputStream.
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ClientStore.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			ClientStore clientResource = (ClientStore) unmarshaller.unmarshal(inputStream);
			clients = clientResource.getClients();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		// The clients inside the XML file have no id, we can set
		// the id now.
		int idIteration = 0;
		for (Client client : clients) {
			client.setId(idIteration);
			idIteration++;
		}
		
		return clients;
	}
}
