package com.bccns.umsserviceweb.common.pagination;

/**
 * The Class PaginationInfo.
 */
public class PaginationInfo {
	
	/** The servlet context root. */
	private String servletContextRoot;
	
	/** The current page no. */
	
	private int currentPageNo;
	
	/** The record count per page. */
	private int recordCountPerPage;
	
	/** The page size. */
	private int pageSize;
	
	/** The total record count. */
	private int totalRecordCount;
	
	/**
	 * Gets the 현재 Servlet ContextRoot.
	 * 
	 * @return the 현재 Servlet ContextRoot
	 */
	public String getServletContextRoot() {
		return servletContextRoot;
	}

	/**
	 * Sets the 현재 Servlet ContextRoot.
	 * 
	 * @param servletContextRoot the new 현재 Servlet ContextRoot
	 */
	public void setServletContextRoot(String servletContextRoot) {
		this.servletContextRoot = servletContextRoot;
	}

	/**
	 * Gets the record count per page.
	 * 
	 * @return the record count per page
	 */
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	
	/**
	 * Sets the record count per page.
	 * 
	 * @param recordCountPerPage the new record count per page
	 */
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	
	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * Sets the page size.
	 * 
	 * @param pageSize the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * Gets the required Fields - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.
	 * 
	 * @return the required Fields - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다
	 */
	public int getCurrentPageNo() {
		if(currentPageNo <= 0){
			return 1;
		}else{
			return currentPageNo;
		}
	}
	
	/**
	 * Sets the required Fields - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.
	 * 
	 * @param currentPageNo the new required Fields - 이 필드들은 페이징 계산을 위해 반드시
	 *            입력되어야 하는 필드 값들이다
	 */
	public void setCurrentPageNo(int currentPageNo) {
		if(currentPageNo <= 0){
			this.currentPageNo = 1;
		}else{
			this.currentPageNo = currentPageNo;
		}
	}
	
	/**
	 * Sets the total record count.
	 * 
	 * @param totalRecordCount the new total record count
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	
	/**
	 * Gets the total record count.
	 * 
	 * @return the total record count
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	
	/** The total page count. */
	
	private int totalPageCount;
	
	/** The first page no on page list. */
	private int firstPageNoOnPageList;
	
	/** The last page no on page list. */
	private int lastPageNoOnPageList;
	
	/** The first record index. */
	private int firstRecordIndex;
	
	/** The last record index. */
	private int lastRecordIndex;	
	
	/**
	 * Gets the not Required Fields - 이 필드들은 Required Fields 값을 바탕으로 계산해서 정해지는
	 * 필드 값이다.
	 * 
	 * @return the not Required Fields - 이 필드들은 Required Fields 값을 바탕으로 계산해서
	 *         정해지는 필드 값이다
	 */
	public int getTotalPageCount() {
		totalPageCount = ((getTotalRecordCount()-1)/getRecordCountPerPage()) + 1;
		return totalPageCount;
	}
	
	/**
	 * Gets the first page no.
	 * 
	 * @return the first page no
	 */
	public int getFirstPageNo(){
		return 1;
	}
	
	/**
	 * Gets the last page no.
	 * 
	 * @return the last page no
	 */
	public int getLastPageNo(){
		return getTotalPageCount();		
	}
	
	/**
	 * Gets the first page no on page list.
	 * 
	 * @return the first page no on page list
	 */
	public int getFirstPageNoOnPageList() {
		firstPageNoOnPageList = ((getCurrentPageNo()-1)/getPageSize())*getPageSize() + 1;
		return firstPageNoOnPageList;
	}
	
	/**
	 * Gets the last page no on page list.
	 * 
	 * @return the last page no on page list
	 */
	public int getLastPageNoOnPageList() {		
		lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;		
		if(lastPageNoOnPageList > getTotalPageCount()){
			lastPageNoOnPageList = getTotalPageCount();
		}
		return lastPageNoOnPageList;
	}

	/**
	 * Gets the first record index.
	 * 
	 * @return the first record index
	 */
	public int getFirstRecordIndex() {
		firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
		return firstRecordIndex;
	}

	/**
	 * Gets the last record index.
	 * 
	 * @return the last record index
	 */
	public int getLastRecordIndex() {
		lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
		return lastRecordIndex;
	}
	
	
	
	/** List page  Data Sort 에 사용 **/

	private String pageSortColumn;
	
	private String pageSortOption;
	
	public String getPageSortColumn() {
		return pageSortColumn;
	}

	public void setPageSortColumn(String pageSortColumn) {
		this.pageSortColumn = pageSortColumn;
	}

	public String getPageSortOption() {
		return pageSortOption;
	}

	public void setPageSortOption(String pageSortOption) {
		this.pageSortOption = pageSortOption;
	}
	
}
