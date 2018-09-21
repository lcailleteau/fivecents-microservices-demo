package com.fivecents.rest.pagination;

/**
 * Interface to be implemented by the entity returned in web service
 * listing requests.
 * 
 * @author Laurent CAILLETEAU
 *
 * @param <T>
 */
public interface Paginated<T> {
    T requestedPageEntity();
    int requestedPageNumber();
    int totalNumberOfPages();
    int numberOfElementsPerPage();
    int totalNumberOfElements();
}
