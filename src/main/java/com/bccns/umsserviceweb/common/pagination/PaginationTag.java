package com.bccns.umsserviceweb.common.pagination;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;



/**
 * The Class PaginationTag.
 */
public class PaginationTag extends TagSupport {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The pagination info. */
	private PaginationInfo paginationInfo;
	
	/** The type. */
	private String type;
	
	/** The js function. */
	private String jsFunction;
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException{
		
		try {
			
			JspWriter out = pageContext.getOut();
			
			PaginationManager paginationManager;
			
            // WebApplicationContext에 id 'paginationManager'로 정의된 해당 Manager를 찾는다.
            WebApplicationContext ctx = RequestContextUtils.getWebApplicationContext(pageContext.getRequest(), pageContext.getServletContext());
            
            paginationInfo.setServletContextRoot(ctx.getServletContext().getContextPath());

            if(ctx.containsBean("paginationManager")){
            	paginationManager = (PaginationManager) ctx.getBean("paginationManager");
            }else{
            	//bean 정의가 없다면 DefaultPaginationManager를 사용. 빈설정이 없으면 기본 적인 페이징 리스트라도 보여주기 위함.
            	paginationManager = new DefaultPaginationManager();
            }
            
            PaginationRenderer paginationRenderer = paginationManager.getRendererType(type);
            
            String contents = paginationRenderer.renderPagination(paginationInfo, jsFunction);
            
            out.println(contents);
            
            return EVAL_PAGE;
            
        } catch (IOException e) {
            throw new JspException();
        }
	}
			
	/**
	 * Sets the js function.
	 * 
	 * @param jsFunction the new js function
	 */
	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}

	/**
	 * Sets the pagination info.
	 * 
	 * @param paginationInfo the new pagination info
	 */
	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}
	
	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(String type){
		this.type = type;
	}
}
