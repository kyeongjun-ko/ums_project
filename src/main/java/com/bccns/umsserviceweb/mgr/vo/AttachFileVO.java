package com.bccns.umsserviceweb.mgr.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class AttachFileVO	{
	private String userId    ;
	private String grpCd     ;
	private String grpNo     ;
	private String fileNo    ;
	private String msgNo     ;
	private String fileType  ;
	private String fileSize  ;
	private String fileDir   ;
	private String fileNm    ;
	private String instDt;
	private String instId;
	private String updtDt;
	private String updtId;
	private String searchType;
	private String searchName;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getGrpNo() {
		return grpNo;
	}
	public void setGrpNo(String grpNo) {
		this.grpNo = grpNo;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
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
	@Override
	public String toString() {
		return "AttachFileVO [userId=" + userId + ", grpCd=" + grpCd
				+ ", grpNo=" + grpNo + ", fileNo=" + fileNo + ", msgNo="
				+ msgNo + ", fileType=" + fileType + ", fileSize=" + fileSize
				+ ", fileDir=" + fileDir + ", fileNm=" + fileNm + ", instDt="
				+ instDt + ", instId=" + instId + ", updtDt=" + updtDt
				+ ", updtId=" + updtId + ", searchType=" + searchType
				+ ", searchName=" + searchName + "]";
	}
	
	
}
