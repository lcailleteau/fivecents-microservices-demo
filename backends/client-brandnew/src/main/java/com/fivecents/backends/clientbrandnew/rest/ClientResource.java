package com.fivecents.backends.clientbrandnew.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fivecents.backends.clientbrandnew.enterprise.ClientEnterpriseService;
import com.fivecents.backends.clientbrandnew.enterprise.beans.Client;
import com.fivecents.backends.clientbrandnew.rest.beans.ClientListing;
import com.fivecents.backends.clientbrandnew.rest.pagination.Paginated;
import com.fivecents.backends.clientbrandnew.rest.pagination.PaginatedImpl;
import com.fivecents.backends.clientbrandnew.rest.pagination.PaginationConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * REST endpoint for the client service.
 * 
 * Because of monitoring activated, we can access :
 *	 http://localhost:8080/node
 * 
 * Samples :
 *   curl http://localhost:8080/api/v1/client -H "Accept:application/json"
 *   curl http://localhost:8080/api/v1/client -H "Accept:application/xml"
 *   curl http://localhost:8080/api/v1/client/1 -H "Accept:application/json"
 *   curl http://localhost:8080/api/v1/client/1 -H "Accept:application/xml"
 *   curl -X POST http://localhost:8080/api/v1/client -i -H "Content-Type: text/json" --data "{\"firstname\": \"Isabelle\", \"lastname\": \"PIVOT\", \"legacy-id\": 1009}"
 *      We have as a result the Location header, for instance : http://localhost: 8080/api/v1/client/5
 *   curl "http://localhost:8080/api/v1/client?page=2&per_page=3" -H "Accept:application/json" -i
 * 
 * - Explain that by default, we give JSON, because first entry in @Produces
 * - Java EE 7 supports JSON binding automatically, and uses Xml annotation,
 * example with 'legacy-id' ! Great !
 * 
 * URL to load :
 *   http://localhost:8080/swagger-ui/
 *   http://localhost:8080/api/v1/swagger.json
 * Cf. https://wildfly-swarm.gitbooks.io/wildfly-swarm-users-guide/advanced/swagger.html
 * 
 * Detailed explanation on wildfly swarm with swagger :
 *   https://wildfly-swarm.gitbooks.io/wildfly-swarm-users-guide/advanced/swagger.html
 * 
 * Explain classifier possibility, cf. https://wildfly-swarm.gitbooks.io/wildfly-swarm-users-guide/server/swagger_ui.html
 * 
 * Very interesting article on HATEOAS pagination links :
 * https://blog.arkey.fr/2017/10/02/pagination_hateoas_link/
 * To implement here and explain !
 * totalcount, min, max...
 * Cf. https://developer.github.com/v3/guides/traversing-with-pagination/
 * 
 * To do also :
 * - provide a simple GUI in JSF to display the elements
 * - provide a websocket to update the list of elements when modified
 *   by REST request
 * - add an address to the client bean
 * 
 * 
 * 
 * @author Laurent CAILLETEAU
 */
@Path("/client")
@Api(value = "/client", description = "Client brand new REST service")
@SwaggerDefinition (
	info = @Info (
        title = "Example Service",
        description = "A simple example here",
        version = "1.0.0",
        contact = @Contact (
            name = "Laurent CAILLETEAU",
            email = "lll@gmail.com",
            url = "http://lcailleteau.wordpress.com"
        )
    ),
    host = "localhost",
    basePath = "/api/v1",
    schemes = {SwaggerDefinition.Scheme.HTTP}
)
public class ClientResource {

	@Inject
	private ClientEnterpriseService clientEnterpriseService;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/")
	@ApiOperation(value = "Get all clients", notes = "This operation returns clients in the database")
	public Paginated<ClientListing> searchClients(
			@DefaultValue("id") @QueryParam("order_by") String orderBy,
			@DefaultValue("1") @QueryParam(PaginationConstants.QUERY_PARAM_PAGE) int page,
			@DefaultValue("10") @QueryParam(PaginationConstants.QUERY_PARAM_PER_PAGE) int perPage) {
		// Call the enterprise service.
		List<Client> clients = clientEnterpriseService.getAllClients(orderBy, page, perPage);
		ClientListing clientListing = new ClientListing(clients);
		
		// We need to send back headers to help our callers to navigate easily into the clients.
		// This will be done outside this scope, with the help of the JAX-RS 2.0 container
		// response filter. All we need to do is sending back an entity inheriting from Pagination
		// interface.
		int totalCount = clientEnterpriseService.getAllClients().size();
		Paginated<ClientListing> paginatedClientListing = 
				new PaginatedImpl<ClientListing>(
						page,
						perPage,
						totalCount,
						clientListing);
	
		return paginatedClientListing;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("{clientId}")
	@ApiResponses(value = { 
		@ApiResponse(code = 400, message = "Invalid ID supplied"),
		@ApiResponse(code = 404, message = "Client not found") })
	public Response searchClient(@PathParam("clientId") int clientId) {
		if (clientId > 2000) {
			return Response.status(400).entity("Incorrect id, must be an integer less than 2000").build();
		} else {
			// Call the enterprise service.
			Client client = clientEnterpriseService.searchForClient(clientId);
			if (client == null) {
				return Response.status(404).entity("Client not found").build();
			} else {
				return Response.ok().entity(client).build();
			}
		}
	}
	
	@POST
	@Path("/")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Client created") })
	public Response createClient(Client client) {
		Client clientCreated = clientEnterpriseService.createClient(client);
		int clientId = clientCreated.getId();
		
		// We can build the location header to return to the caller,
		// in order for him to have the URI to call operations on the
		// newly created resource.
		URI location = URI.create("client/" + clientId);
		return Response
				.created(location)
				.entity("Client created")
				.build();
	}
	
	@DELETE
	@Path("{clientId}")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Client successfully deleted"),
		@ApiResponse(code = 400, message = "Invalid ID supplied"),
		@ApiResponse(code = 404, message = "Client not found") })
	public Response removeClient(@PathParam("clientId") int clientId) {
		if (clientId > 2000) {
			return Response.status(400).entity("Incorrect id, must be an integer less than 2000").build();
		} else {
			// Call the enterprise service.
			boolean clientRemoved =	clientEnterpriseService.removeClient(clientId);
			if (clientRemoved) {
				return Response.noContent().build();
			} else {
				return Response.status(404).build();
			}
		}
	}
	
	@PUT
	@Path("{clientId}")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "Client successfully updated"),
		@ApiResponse(code = 400, message = "Invalid ID supplied"),
		@ApiResponse(code = 404, message = "Client not found") })
	public Response updateClient(@PathParam("clientId") int clientId, Client client) {
		if (clientId > 2000) {
			return Response.status(400).entity("Incorrect id, must be an integer less than 2000").build();
		} else {
			boolean clientUpdated = clientEnterpriseService.updateClient(clientId, client);
			if (clientUpdated) {
				return Response.noContent().build();
			} else {
				return Response.status(404).build();
			}
		}
	}
}
