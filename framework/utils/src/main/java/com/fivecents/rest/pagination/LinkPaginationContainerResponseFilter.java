package com.fivecents.rest.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * This filter class implements JAX-RS 2.0 ContainerResponseFilter. All resources responses
 * will get through it, and we can build the Links elements outside the scope of the resource,
 * making a clean separation of concerns.
 * 
 * @author Laurent CAILLETEAU
 */
@Provider
public class LinkPaginationContainerResponseFilter implements ContainerResponseFilter {
	/**
	 * The main filter method requested on every resource response.
	 */
	@Override
	public void filter(ContainerRequestContext requestContext,
				ContainerResponseContext responseContext) {

		// Well, if no pagination, let's ignore.
		if (responseContext.getEntity() instanceof Paginated) {
			Paginated<?> entity = (Paginated<?>) responseContext.getEntity();
			
			// We can build the list of Links, with the information on pages we have.
			List<Link> allLinks = buildLinks(
					entity.requestedPageNumber(), 
					entity.totalNumberOfPages(), 
					requestContext.getUriInfo());

			// Let's feed the responseContext with these data.
			responseContext.setEntity(entity.requestedPageEntity());
			responseContext.getHeaders().addAll("Links", allLinks);
			responseContext.getHeaders().add(PaginationConstants.X_TOTAL_COUNT, entity.totalNumberOfElements());
			responseContext.getHeaders().add(PaginationConstants.X_PAGE_COUNT, entity.totalNumberOfPages());
		}
    }

	/** 
	 * Method to help building the Links for a given response. 
	 */
	private List<Link> buildLinks(int requestedPageNumber, int totalNumberOfPages, UriInfo uriInfo) {
		List<Link> allLinks = new ArrayList<>();
		if (requestedPageNumber == 1 && totalNumberOfPages == 1) {
            return allLinks;
        }

		// First and last links.
		Link linkFirst = Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
				.replaceQueryParam(PaginationConstants.QUERY_PARAM_PAGE, PaginationConstants.FIRST_PAGE))
				.rel(PaginationConstants.RELATION_FIRST)
				.build();
		allLinks.add(linkFirst);
		Link linkLast = Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
				.replaceQueryParam(PaginationConstants.QUERY_PARAM_PAGE, totalNumberOfPages))
				.rel(PaginationConstants.RELATION_LAST)
				.build();
		allLinks.add(linkLast);

		// Previous and next links, if required.
		if (requestedPageNumber > 1) {
			Link link = Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
					.replaceQueryParam(PaginationConstants.QUERY_PARAM_PAGE, requestedPageNumber - 1))
					.rel(PaginationConstants.RELATION_PREV)
					.build();
			allLinks.add(link);
		}
		if (requestedPageNumber < totalNumberOfPages) {
			Link link = Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
					.replaceQueryParam(PaginationConstants.QUERY_PARAM_PAGE, requestedPageNumber + 1))
					.rel(PaginationConstants.RELATION_NEXT)
					.build();
			allLinks.add(link);
		}

		return allLinks;
	}
}
