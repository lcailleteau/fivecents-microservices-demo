package com.fivecents.backends.clientbrandnew.rest.pagination;

/**
 * Constants for the github-like pagination.
 *   
 * @author Laurent CAILLETEAU
 */
public class PaginationConstants {
	// Keys returned as HTTP headers in responses.
	public static final String PREV_REL = "prev";
    public static final String NEXT_REL = "next";
    public static final String FIRST_REL = "first";
    public static final String LAST_REL = "last";
    public static final String X_TOTAL_COUNT = "X-Total-Count";
    public static final String X_PAGE_COUNT = "X-Page-Count";
    
    // Query params to trigger pagination.
    public static final String PAGE_QUERY_PARAM = "page";
    public static final String PER_PAGE_QUERY_PARAM = "per_page";
    
    // Other constants.
    public static final int FIRST_PAGE = 1;
}
