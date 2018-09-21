package com.fivecents.rest.pagination;

/**
 * Default implementation for a paginated JAX-RS entity.
 * The only remaining method to implement is the one that is
 * effectively returning the list of entities.
 * 
 * @author Laurent CAILLETEAU
 *
 * @param <T> Type of the entity.
 */
public class PaginatedImpl<T> implements Paginated<T> {
	protected int requestedPageNumber = 1;
	protected int totalNumberOfPages = 1;
	protected int numberOfElementsPerPage = 1;
	protected int totalNumberOfElements = 10;
	protected T jaxrsResponseEntity;
	
	/**
	 * Constructors.
	 */
	public PaginatedImpl(
		int requestedPageNumber,
		int totalNumberOfPages,
		int numberOfElementsPerPage,
		int totalNumberOfElements,
		T jaxrsResponseEntity) {
		this.requestedPageNumber = requestedPageNumber;
		this.totalNumberOfPages = totalNumberOfPages;
		this.numberOfElementsPerPage = numberOfElementsPerPage;
		this.totalNumberOfElements = totalNumberOfElements;
		this.jaxrsResponseEntity = jaxrsResponseEntity;
	}
	public PaginatedImpl(
		int requestedPageNumber,
		int numberOfElementsPerPage,
		int totalNumberOfElements,
		T jaxrsResponseEntity) {
		this.requestedPageNumber = requestedPageNumber;
		this.numberOfElementsPerPage = numberOfElementsPerPage;
		this.totalNumberOfElements = totalNumberOfElements;
		this.jaxrsResponseEntity = jaxrsResponseEntity;
		
		// Let's figure out the page count.
		this.totalNumberOfPages = (int) Math.ceil((double) totalNumberOfElements / numberOfElementsPerPage);
	}
	
	/**
	 * Current page index.
	 */
	public int requestedPageNumber() {
		return requestedPageNumber; 
	}
	
	/**
	 * Page count.
	 */
	public int totalNumberOfPages() {
		return totalNumberOfPages; 
	}
	
	/**
	 * Per-page count.
	 */
	public int numberOfElementsPerPage() {
		return numberOfElementsPerPage; 
	}

	/**
	 * Total count.
	 */
	public int totalNumberOfElements() {
		return totalNumberOfElements; 
	}
	
	/**
	 * Returns the entity.
	 */
	public T requestedPageEntity() {
		return jaxrsResponseEntity;
	}
}
