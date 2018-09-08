package com.fivecents.backends.clientbrandnew.rest.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fivecents.backends.clientbrandnew.enterprise.beans.Client;
/**
 * This bean corresponds to the entity returned on JAX-RS search
 * operations.
 * 
 * @author Laurent CAILLETEAU
 */
@XmlRootElement(name="clients", namespace="")
public class ClientListing {
	private List<Client> clients;

	/**
	 * Constructors.
	 * @return
	 */
	public ClientListing() {
		super();
	}
	public ClientListing(List<Client> clients) {
		this.clients = clients;
	}
	
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
}

