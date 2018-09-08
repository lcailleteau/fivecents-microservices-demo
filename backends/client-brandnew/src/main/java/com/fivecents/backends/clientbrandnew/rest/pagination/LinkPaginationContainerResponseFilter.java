package com.fivecents.backends.clientbrandnew.rest.pagination;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
public class LinkPaginationContainerResponseFilter implements ContainerResponseFilter {
   

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {

        if (!(responseContext.getEntity() instanceof Paginated)) {
            return;
        }

        UriInfo uriInfo = requestContext.getUriInfo();
        Paginated entity = (Paginated) responseContext.getEntity();

        responseContext.setEntity(entity.currentPage());
        responseContext.getHeaders()
                     //  .addAll(LINK,
        				.addAll("Links",
                               new LinkPagination(
                                   entity.currentPageIndex(),
                                   entity.pageCount()
                               ).toLinks(uriInfo).toArray(Link[]::new)
                       );
        responseContext.getHeaders().add(PaginationConstants.X_TOTAL_COUNT, entity.totalCount());
        responseContext.getHeaders().add(PaginationConstants.X_PAGE_COUNT, entity.pageCount());
    }
}
