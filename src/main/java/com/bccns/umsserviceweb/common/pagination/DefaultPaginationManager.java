package com.bccns.umsserviceweb.common.pagination;


import java.util.Map;

/**
 * The Class DefaultPaginationManager.
 */

public class DefaultPaginationManager implements PaginationManager{
	
	/** The renderer type. */
	private Map<String, PaginationRenderer> rendererType;
	
	/**
	 * Sets the renderer type.
	 * 
	 * @param rendererType the new renderer type
	 */
	public void setRendererType(Map<String, PaginationRenderer> rendererType) {
		this.rendererType = rendererType;
	}
	
	/* (non-Javadoc)
	 * @see com.sbsch.cms.framework.web.ui.pagination.PaginationManager#getRendererType(java.lang.String)
	 */
	public PaginationRenderer getRendererType(String type) {
		
		return (rendererType!=null && rendererType.containsKey(type)) ? (PaginationRenderer) rendererType.get(type):new DefaultPaginationRenderer();		
	}	
	
}
