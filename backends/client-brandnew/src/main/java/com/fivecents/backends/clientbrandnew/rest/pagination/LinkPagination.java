package com.fivecents.backends.clientbrandnew.rest.pagination;

import java.util.stream.Stream;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

/**
 * This helper class builds the Link headers found in HATEOAS
 * REST calls, to make pagination headers the same wey as GitHub.
 * 
 * A nice blog about this advanced REST pagination here :
 *   https://blog.arkey.fr/2017/10/02/pagination_hateoas_link/
 *   
 * @author Laurent CAILLETEAU
 */
public class LinkPagination {
	

    public final int pageCount;
    public final int currentPageIndex;
    
    public LinkPagination(int currentPageIndex, int pageCount) {
    	this.currentPageIndex = currentPageIndex;
    	this.pageCount = pageCount;
    }

    public Stream<Link> toLinks(UriInfo uriInfo) {
        if (currentPageIndex == 1 && pageCount == 1) {
            return Stream.empty();
        }

        Stream.Builder<Link> linkStreamBuilder = Stream.builder();

        if (currentPageIndex > 1) {
            linkStreamBuilder.accept(
                Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                           .replaceQueryParam(PaginationConstants.PAGE_QUERY_PARAM,
                                                              currentPageIndex - 1))
                    .rel(PaginationConstants.PREV_REL)
                    .build());
        }

        if (currentPageIndex < pageCount) {
            linkStreamBuilder.accept(
                Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                           .replaceQueryParam(PaginationConstants.PAGE_QUERY_PARAM,
                                                              currentPageIndex + 1))
                    .rel(PaginationConstants.NEXT_REL)
                    .build());
        }

        linkStreamBuilder.accept(
            Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                       .replaceQueryParam(PaginationConstants.PAGE_QUERY_PARAM,
                                    		   PaginationConstants.FIRST_PAGE))
                .rel(PaginationConstants.FIRST_REL)
                .build());

        linkStreamBuilder.accept(
            Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                       .replaceQueryParam(PaginationConstants.PAGE_QUERY_PARAM,
                                                          pageCount))
                .rel(PaginationConstants.LAST_REL)
                .build());

        return linkStreamBuilder.build();
    }
}
