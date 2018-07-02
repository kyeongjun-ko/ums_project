package com.bccns.umsserviceweb.mgr.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class SearchResvDetVO extends PaginationInfo {
	private String msgId;
	private String userId;
	private String msgType;
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	@Override
	public String toString() {
		return "SearchRsltDetVO [msgId=" + msgId + ", userId=" + userId
				+ ", msgType=" + msgType + "]";
	}
}
