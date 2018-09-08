package com.fivecents.backends.clientbrandnew.rest.pagination;

/**
 * Interface to be implemented by the entity returned in web service
 * listing requests.
 * 
 * @author Laurent CAILLETEAU
 *
 * @param <T>
 */
public interface Paginated<T> {
    T currentPage();
    int currentPageIndex();
    int pageCount();
    int perPageCount();
    int totalCount();
}
