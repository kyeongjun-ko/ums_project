package com.bccns.umsserviceweb.common.vo;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonVO {

    private boolean result = true;
    private String sucMsg;
    private String errMsg;
    private String returnId;
    
    public String getSucMsg() {
		return sucMsg;
	}
	public void setSucMsg(String sucMsg) {
		this.sucMsg = sucMsg;
	}
	public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public String getReturnId() {
        return returnId;
    }
    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }
    public String toJsonStr() {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(this);     
        } catch (Exception e) {
            
            StringBuffer buf = new StringBuffer();
            buf.append("{");
            buf.append("\"result\":false,");
            buf.append("\"errMsg\":\"" + e.getMessage() + "\"");
            buf.append("}");
            
            return buf.toString();
        }
    }
    
}
