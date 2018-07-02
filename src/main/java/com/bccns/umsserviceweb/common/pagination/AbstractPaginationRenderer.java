package com.bccns.umsserviceweb.common.pagination;

import java.text.MessageFormat;

/**
 * The Class AbstractPaginationRenderer.
 */
public abstract class AbstractPaginationRenderer implements PaginationRenderer{
 
	/** The first page label. */
	public String firstPageLabel;
	
	/** The previous page label. */
	public String previousPageLabel;
	
	/** The current page label. */
	public String currentPageLabel;
	
	/** The other page label. */
	public String otherPageLabel;
	
	/** The next page label. */
	public String nextPageLabel;
	
	/** The last page label. */
	public String lastPageLabel;

	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.web.ui.pagination.PaginationRenderer#renderPagination(com.sbsch.cms.framework.web.ui.pagination.PaginationInfo, java.lang.String)
	 */
	public String renderPagination(PaginationInfo paginationInfo,String jsFunction){
		
		StringBuffer strBuff = new StringBuffer();
        
		String servletContextRoot = paginationInfo.getServletContextRoot();
        int firstPageNo = paginationInfo.getFirstPageNo();
        int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
        int totalPageCount = paginationInfo.getTotalPageCount();
		int pageSize = paginationInfo.getPageSize();
		int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
		int currentPageNo = paginationInfo.getCurrentPageNo();
		int lastPageNo = paginationInfo.getLastPageNo();
		
		if(totalPageCount > pageSize){
			if(firstPageNoOnPageList > pageSize){
				strBuff.append(MessageFormat.format(firstPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNo),Integer.toString(firstPageNo)}));				
				strBuff.append(MessageFormat.format(previousPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNoOnPageList-1),servletContextRoot}));
			}else{
				//strBuff.append(MessageFormat.format(previousPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNo)}));
	        	//strBuff.append(MessageFormat.format(firstPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNo),Integer.toString(firstPageNo)}));
	        }
		}
		
		for(int i=firstPageNoOnPageList;i<=lastPageNoOnPageList;i++){
			if(i==currentPageNo){
        		strBuff.append(MessageFormat.format(currentPageLabel,new Object[]{Integer.toString(i)}));
        	}else{
        		strBuff.append(MessageFormat.format(otherPageLabel,new Object[]{jsFunction,Integer.toString(i),Integer.toString(i)}));
        	}
        }
        
		if(totalPageCount > pageSize){
			if(lastPageNoOnPageList < totalPageCount){
				strBuff.append(MessageFormat.format(nextPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNoOnPageList+pageSize),servletContextRoot}));
	        	strBuff.append(MessageFormat.format(lastPageLabel,new Object[]{jsFunction,Integer.toString(lastPageNo),Integer.toString(lastPageNo)}));		
	        }else{
	        	//strBuff.append(MessageFormat.format(lastPageLabel,new Object[]{jsFunction,Integer.toString(lastPageNo),Integer.toString(lastPageNo)}));
	        	//strBuff.append(MessageFormat.format(nextPageLabel,new Object[]{jsFunction,Integer.toString(lastPageNo)}));
	        }
		}
		return strBuff.toString();
	}
}
