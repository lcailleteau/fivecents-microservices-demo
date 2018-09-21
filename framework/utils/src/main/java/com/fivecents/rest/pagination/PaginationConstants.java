package com.fivecents.rest.pagination;

/**
 * Constants for the pagination links.
 *   
 * @author Laurent CAILLETEAU
 */
public class PaginationConstants {
	// Keys returned as HTTP headers in responses.
	public static final String RELATION_PREV = "prev";
    public static final String RELATION_NEXT = "next";
    public static final String RELATION_FIRST = "first";
    public static final String RELATION_LAST = "last";
    public static final String X_TOTAL_COUNT = "X-Total-Count";
    public static final String X_PAGE_COUNT = "X-Page-Count";
    
    // Query params to trigger pagination.
    public static final String QUERY_PARAM_PAGE = "page";
    public static final String QUERY_PARAM_PER_PAGE = "per_page";
    
    // Other constants.
    public static final int FIRST_PAGE = 1;
}
