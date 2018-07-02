package com.bccns.umsserviceweb.mgr.vo;
import java.util.Arrays;
import java.util.List;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;
import com.bccns.umsserviceweb.common.vo.UsrGrpVO;

public class SearchGrpVO extends PaginationInfo {
 
	private String[] grpArNo;
	private String[] grpArCd;
	
	private String rowNumber;
	private String userId;
	private String grpNo;
	
	private String grpCd;
	private String grpNm;
		
	private String note;
	private String instDt;
	private String instId;
	private String updtDt;
	private String updtId;
	private String searchType;
	private String searchName;
	
	private String grpCdNm;
	private String grpCnt;
	
	private List<UsrGrpVO> usrGrpList;
	
	private String regFg;

	private String name;

	private String selCnt;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSelCnt() {
		return selCnt;
	}
	public void setSelCnt(String selCnt) {
		this.selCnt = selCnt;
	}
	public String getRegFg() {
		return regFg;
	}
	public void setRegFg(String regFg) {
		this.regFg = regFg;
	}
	public String[] getGrpArCd() {
		return grpArCd;
	}
	public void setGrpArCd(String[] grpArCd) {
		this.grpArCd = grpArCd;
	}
	public List<UsrGrpVO> getUsrGrpList() {
		return usrGrpList;
	}
	public void setUsrGrpList(List<UsrGrpVO> usrGrpList) {
		this.usrGrpList = usrGrpList;
	}
	public String getGrpCdNm() {
		return grpCdNm;
	}
	public void setGrpCdNm(String grpCdNm) {
		this.grpCdNm = grpCdNm;
	}
	public String getGrpCnt() {
		return grpCnt;
	}
	public void setGrpCnt(String grpCnt) {
		this.grpCnt = grpCnt;
	}
	public String[] getGrpArNo() {
		return grpArNo;
	}
	public void setGrpArNo(String[] grpArNo) {
		this.grpArNo = grpArNo;
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
	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getGrpNm() {
		return grpNm;
	}
	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
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
		return "SearchGrpVO [grpArNo=" + Arrays.toString(grpArNo)
				+ ", grpArCd=" + Arrays.toString(grpArCd) + ", rowNumber="
				+ rowNumber + ", userId=" + userId + ", grpNo=" + grpNo
				+ ", grpCd=" + grpCd + ", grpNm=" + grpNm + ", note=" + note
				+ ", instDt=" + instDt + ", instId=" + instId + ", updtDt="
				+ updtDt + ", updtId=" + updtId + ", searchType=" + searchType
				+ ", searchName=" + searchName + ", grpCdNm=" + grpCdNm
				+ ", grpCnt=" + grpCnt + ", usrGrpList=" + usrGrpList
				+ ", regFg=" + regFg + ", name=" + name + ", selCnt=" + selCnt
				+ "]";
	}
	
	
}
