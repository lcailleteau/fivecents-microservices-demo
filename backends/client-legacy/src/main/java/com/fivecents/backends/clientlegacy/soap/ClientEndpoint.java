package com.fivecents.backends.clientlegacy.soap;

import com.fivecents.backends.clientlegacy.enterprise.ClientEnterpriseService;
import com.fivecents.backends.clientlegacy.enterprise.beans.Client;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * SOAP WebService endpoint for the client service.
 * 
 * @author Laurent CAILLETEAU
 */
@WebService(serviceName="clientService")
public class ClientEndpoint {

	@Inject
	private ClientEnterpriseService clientEnterpriseService;
	
	
	@WebMethod
	public List<Client> findAll() {

		return null;
	}
	
	@WebMethod
	public boolean create(Client client) {

		return true;
	}
}
