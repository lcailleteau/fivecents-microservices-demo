package com.fivecents.backends.clientbrandnew.loader;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fivecents.backends.clientbrandnew.enterprise.beans.Client;

/**
 * List of clients.
 * 
 * @author Laurent CAILLETEAU
 */
@XmlRootElement(name="clients", namespace="")
public class ClientStore {
	private List<Client> clients;
	
	public ClientStore() {
		this.clients = new ArrayList<>();
	}
	public ClientStore(List<Client> clients) {
		this.clients = clients;
	}

	@XmlElement(name="client")
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
}

