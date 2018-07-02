package com.bccns.umsserviceweb.common.pagination;

/**
 * The Interface PaginationRenderer.
 */
public interface PaginationRenderer {
	
	/**
	 * Render pagination.
	 * 
	 * @param paginationInfo the pagination info
	 * @param jsFunction the js function
	 * @return the string
	 */
	public String renderPagination(PaginationInfo paginationInfo,String jsFunction);
	
}
