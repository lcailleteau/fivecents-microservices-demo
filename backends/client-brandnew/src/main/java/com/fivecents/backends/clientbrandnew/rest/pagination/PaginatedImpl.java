package com.fivecents.backends.clientbrandnew.rest.pagination;

import com.fivecents.backends.clientbrandnew.rest.beans.ClientListing;

/**
 * Default implementation for a paginated JAX-RS entity.
 * The only remaining method to implement is the one that is
 * effectively returning the list of elements.
 * 
 * @author Laurent CAILLETEAU
 *
 * @param <T> Type of the entity.
 */
public class PaginatedImpl<T> implements Paginated<T> {
	protected int currentPageIndex = 1;
	protected int pageCount = 1;
	protected int perPageCount = 1;
	protected int totalCount = 10;
	protected T jaxrsResponseEntity;
	
	/**
	 * Constructors.
	 */
	public PaginatedImpl(
		int currentPageIndex,
		int pageCount,
		int perPageCount,
		int totalCount,
		T jaxrsResponseEntity) {
		this.currentPageIndex = currentPageIndex;
		this.pageCount = pageCount;
		this.perPageCount = perPageCount;
		this.totalCount = totalCount;
		this.jaxrsResponseEntity = jaxrsResponseEntity;
	}
	public PaginatedImpl(
		int currentPageIndex,
		int perPageCount,
		int totalCount,
		T jaxrsResponseEntity) {
		this.currentPageIndex = currentPageIndex;
		this.perPageCount = perPageCount;
		this.totalCount = totalCount;
		this.jaxrsResponseEntity = jaxrsResponseEntity;
		
		// Let's figure out the page count.
		this.pageCount = (int) Math.ceil((double) totalCount / perPageCount);
	}
	
	/**
	 * Current page index.
	 */
	public int currentPageIndex() {
		return currentPageIndex; 
	}
	
	/**
	 * Page count.
	 */
	public int pageCount() {
		return pageCount; 
	}
	
	/**
	 * Per-page count.
	 */
	public int perPageCount() {
		return perPageCount; 
	}

	/**
	 * Total count.
	 */
	public int totalCount() {
		return totalCount; 
	}
	
	/**
	 * Returns the entity.
	 */
	public T currentPage() {
		return jaxrsResponseEntity;
	}
}
