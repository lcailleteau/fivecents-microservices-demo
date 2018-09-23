package com.fivecents.backends.clientlegacy.soap;

import com.fivecents.backends.clientlegacy.enterprise.ClientEnterpriseService;
import com.fivecents.backends.clientlegacy.enterprise.beans.Client;

import java.util.List;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * SOAP WebService endpoint for the client service.
 * 
 * @author Laurent CAILLETEAU
 */
@WebService(
		portName="clientPort",
		serviceName="clientService",
		targetNamespace = "http://clientlegacy.fivecents.com/")
public class ClientEndpoint{

	@Inject
	private ClientEnterpriseService clientEnterpriseService;
	
	@WebMethod(operationName = "findAllClients")
	@WebResult(name = "client")
	public List<Client> findAll() {
		return clientEnterpriseService.getAllClients();
	}
		
	@WebMethod
	@WebResult(name = "client")
	public Client create(@WebParam(name = "client") Client client) {
		return clientEnterpriseService.createClient(client);
	}
	
	@WebMethod
	@WebResult(name = "removalSucceeded")
	public boolean remove(@WebParam(name = "id") int clientId) {
		return clientEnterpriseService.removeClient(clientId);
	}
	
	@WebMethod
	@WebResult(name = "client")
	public Client search(@WebParam(name = "id") int clientId) {
		return clientEnterpriseService.searchForClient(clientId);
	}
	
	@WebMethod
	@WebResult(name = "updateSucceeded")
	public boolean update(@WebParam(name = "client") Client client) {
		return clientEnterpriseService.updateClient(client);
	}
}
