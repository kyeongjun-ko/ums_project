package com.bccns.umsserviceweb.ums.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class UmsImportResultVO {
	private boolean result;
    private String errMsg;
    private List<AddrExcelVO> addrExcelList;
    private List<AddrExcelVO2> addrExcelList2;
   
    
    public List<AddrExcelVO2> getAddrExcelList2() {
		return addrExcelList2;
	}

	public void setAddrExcelList2(List<AddrExcelVO2> addrExcelList2) {
		this.addrExcelList2 = addrExcelList2;
	}
	private String html;
    
    public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public UmsImportResultVO() {
        result = true;
        addrExcelList = new ArrayList<AddrExcelVO>();
        addrExcelList2 = new ArrayList<AddrExcelVO2>();
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

	public List<AddrExcelVO> getAddrExcelList() {
		return addrExcelList;
	}

	public void setAddrExcelList(List<AddrExcelVO> addrExcelList) {
		this.addrExcelList = addrExcelList;
	}
	public void addAddrExcelList(AddrExcelVO vo) {
        this.addrExcelList.add(vo);
    }
	public void addAddrExcelList2(AddrExcelVO2 vo){
		this.addrExcelList2.add(vo);
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
	@Override
	public String toString() {
		return "UmsImportResultVO [result=" + result + ", errMsg=" + errMsg
				+ ", addrExcelList=" + addrExcelList + "]";
	}
    
}
