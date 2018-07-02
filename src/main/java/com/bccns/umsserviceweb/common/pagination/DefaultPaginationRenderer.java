package com.bccns.umsserviceweb.common.pagination;

/**
 * The Class DefaultPaginationRenderer.
 */
public class DefaultPaginationRenderer extends AbstractPaginationRenderer {
	
	/**
	 * Instantiates a new default pagination renderer.
	 */
	public DefaultPaginationRenderer() {
		firstPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}... </a></li>&#160;"; 
        previousPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\"><<</a></li>&#160;";
        currentPageLabel = "<li  class=\"active\"><a href=\"#\"{0}</a></li>&#160;";
        otherPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>&#160;";
        nextPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">>></a></li>&#160;";
        lastPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\"> ...{2}</a></li>&#160;";
	}

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.web.ui.pagination.AbstractPaginationRenderer#renderPagination(com.sbsch.cms.framework.web.ui.pagination.PaginationInfo, java.lang.String)
	 */
	@Override
	public String renderPagination(PaginationInfo paginationInfo,
			String jsFunction) {
		
		return super.renderPagination(paginationInfo, jsFunction);
	}

}