package com.bccns.umsserviceweb.push.vo;

public class RexHeaderVO {

	private String svcId;		
	private String reqSeq;
	private String requestId;
	public String getSvcId() {
		return svcId;
	}
	public void setSvcId(String svcId) {
		this.svcId = svcId;
	}
	public String getReqSeq() {
		return reqSeq;
	}
	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	@Override
	public String toString() {
		return "RexHeaderVO [svcId=" + svcId + ", reqSeq=" + reqSeq
				+ ", requestId=" + requestId + "]";
	}	
	

}
