package com.bccns.umsserviceweb.ums.vo;

import java.util.Arrays;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;

public class AddrExcelInsertVO extends PaginationInfo  {
	private int rowIndex;
    private String addrMetaSeq;
    
    private String rowNumber;
	private String userId;
	private String grpNo;
	private String grpNm;
	private String addrNo;
	private String name;
	private String sname;
	private String smsNo;
	private String vmsNo;
	private String fmsNo;
	private String note;
	private String instDt;
	private String instId;
	private String updtDt;
	private String updtId;
	private String searchType;
	private String searchName;
	private String regFg;
	
	
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getGrpNm() {
		return grpNm;
	}
	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
	}
	public String getRegFg() {
		return regFg;
	}
	public void setRegFg(String regFg) {
		this.regFg = regFg;
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
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGrpNo() {
		return grpNo;
	}
	public void setGrpNo(String grpNo) {
		this.grpNo = grpNo;
	}
	public String getAddrNo() {
		return addrNo;
	}
	public void setAddrNo(String addrNo) {
		this.addrNo = addrNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmsNo() {
		return smsNo;
	}
	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}
	public String getVmsNo() {
		return vmsNo;
	}
	public void setVmsNo(String vmsNo) {
		this.vmsNo = vmsNo;
	}
	public String getFmsNo() {
		return fmsNo;
	}
	public void setFmsNo(String fmsNo) {
		this.fmsNo = fmsNo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getAddrMetaSeq() {
		return addrMetaSeq;
	}
	public void setAddrMetaSeq(String addrMetaSeq) {
		this.addrMetaSeq = addrMetaSeq;
	}
	@Override
	public String toString() {
		return "AddrExcelInsertVO [rowIndex=" + rowIndex + ", addrMetaSeq="
				+ addrMetaSeq + ", rowNumber=" + rowNumber + ", userId="
				+ userId + ", grpNo=" + grpNo + ", grpNm=" + grpNm
				+ ", addrNo=" + addrNo + ", name=" + name + ", sname=" + sname
				+ ", smsNo=" + smsNo + ", vmsNo=" + vmsNo + ", fmsNo=" + fmsNo
				+ ", note=" + note + ", instDt=" + instDt + ", instId="
				+ instId + ", updtDt=" + updtDt + ", updtId=" + updtId
				+ ", searchType=" + searchType + ", searchName=" + searchName
				+ ", regFg=" + regFg + "]";
	}

}
