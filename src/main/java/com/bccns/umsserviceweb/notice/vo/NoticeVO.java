package com.bccns.umsserviceweb.notice.vo;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class NoticeVO extends PaginationInfo  {
	
	private String noticeCd;
	private String noticeNo;
	private String noticeSubNo;
	private String noticeTitle;
	private String noticeCont;
	private String hit;
	private String ip;
	private String instDt;
	private String instId;
	private String updtDt;
	private String updtId;
	private String searchType;
	private String searchName;
	
	@Override
	public String toString() {
		return "NoticeVO [noticeCd=" + noticeCd + ", noticeNo=" + noticeNo
				+ ", noticeSubNo=" + noticeSubNo + ", noticeTitle="
				+ noticeTitle + ", noticeCont=" + noticeCont + ", hit=" + hit
				+ ", ip=" + ip + ", instDt=" + instDt + ", instId=" + instId
				+ ", updtDt=" + updtDt + ", updtId=" + updtId + ", searchType="
				+ searchType + ", searchName=" + searchName + "]";
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getNoticeCd() {
		return noticeCd;
	}
	public void setNoticeCd(String noticeCd) {
		this.noticeCd = noticeCd;
	}
	public String getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getNoticeSubNo() {
		return noticeSubNo;
	}
	public void setNoticeSubNo(String noticeSubNo) {
		this.noticeSubNo = noticeSubNo;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeCont() {
		return noticeCont;
	}
	public void setNoticeCont(String noticeCont) {
		this.noticeCont = noticeCont;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getInstDt() {
		return instDt;
	}
	public void setInstDt(String instDt) {
		this.instDt = instDt;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getUpdtDt() {
		return updtDt;
	}
	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}
	public String getUpdtId() {
		return updtId;
	}
	public void setUpdtId(String updtId) {
		this.updtId = updtId;
	}
	
	
}
