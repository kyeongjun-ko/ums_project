package com.bccns.umsserviceweb.common.pagination;

/**
 * The Class ImagePaginationRenderer.
 */
public class ImagePaginationRenderer extends AbstractPaginationRenderer {
 
	/**
	 * Instantiates a new image pagination renderer.
	 */
	public ImagePaginationRenderer() {
		
	/*	firstPageLabel = " <input type=\"image\" onclick=\"{0}({1}); return false;\" src=\"http://cci.sbs.co.kr/resources/images/common/btn/paging_first.gif\" class=\"first\" title=\"처음\" /> ";
                previousPageLabel = "<a href=\"#\" class=\"dir\" onclick=\"{0}({1}); return false;\"><img src=\"http://img.sbs.co.kr/sw11/sp12/common/images/btn_prev.gif\" alt=\"이전\"></a>";
                currentPageLabel = "<strong>{0}</strong>&#160;";
                otherPageLabel = " <a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a> ";
                nextPageLabel = "<a href=\"#\" class=\"dir\" onclick=\"{0}({1}); return false;\"><img src=\"http://img.sbs.co.kr/sw11/sp12/common/images/btn_next.gif\" alt=\"다음\"></a>";
                lastPageLabel = " <input type=\"image\" onclick=\"{0}({1}); return false;\" src=\"http://cci.sbs.co.kr/resources/images/common/btn/paging_end.gif\" class=\"end\" title=\"끝\" /> ";
                
                
        firstPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}... </a></li>&#160;"; 
        previousPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\"><<</a></li>&#160;";
        currentPageLabel = "<li  class=\"active\">{0}</li>&#160;";
        otherPageLabel = "<li><li><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>&#160;";
        nextPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">>></a></li>&#160;";
        lastPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\"> ...{2}</a></li>&#160;";
        
            */    
                firstPageLabel = "<li><a href=\"javascript:;\" onclick=\"{0}({1}); return false;\">{2}... </a></li>";
                previousPageLabel = "<li><a href=\"javascript:;\" onclick=\"{0}({1}); return false;\"><<</a></li>";
                currentPageLabel = " <li  class=\"active\"><a href=\"#\">{0}</a></li>";
                otherPageLabel = " <li><a href=\"javascript:;\" onclick=\"{0}({1}); return false;\">{2}</a></li>";
                nextPageLabel = " <li><a href=\"javascript:;\" onclick=\"{0}({1}); return false;\">>></a></li>";
                lastPageLabel = " <li><a href=\"javascript:;\" onclick=\"{0}({1}); return false;\"> ...{2}</a></li>";
                  
                
	}
	
}