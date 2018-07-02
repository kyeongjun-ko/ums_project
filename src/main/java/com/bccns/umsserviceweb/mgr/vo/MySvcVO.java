package com.bccns.umsserviceweb.mgr.vo;

import java.util.Arrays;
import java.util.List;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;
import com.bccns.umsserviceweb.common.vo.UmsUserVO;

public class MySvcVO  {
	
	 
	private String userId;
	private String smsSendCnt;
	private String smsSuccCnt;
	private String smsfailCnt;
	
	private String lmsSendCnt;
	private String lmsSuccCnt;
	private String lmsfailCnt;
	
	private String mmsSendCnt;
	private String mmsSuccCnt;
	private String mmsfailCnt;
	
	private String fmsSendCnt;
	private String fmsSuccCnt;
	private String fmsfailCnt;
	
	private String vmsSendCnt;
	private String vmsSuccCnt;
	private String vmsfailCnt;
	
	private String vmsQrSendCnt;
	private String vmsQrSuccCnt;
	private String vmsQrfailCnt;
	
	private String smartDmSendCnt;
	private String smartDmSuccCnt;
	private String smartDmfailCnt;
	
	private String smartFaxSendCnt;
	private String smartFaxSuccCnt;
	private String smartFaxfailCnt;
	
	
	private String totSendCnt;
	private String totSuccCnt;
	private String totfailCnt;
	private String yyyymm;
	
	private UmsUserVO umsUserVO;
	
    public UmsUserVO getUmsUserVO() {
		return umsUserVO;
	}

	public void setUmsUserVO(UmsUserVO umsUserVO) {
		this.umsUserVO = umsUserVO;
	}

	private int totalCount;
    private String pagingHtml;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(String pagingHtml) {
		this.pagingHtml = pagingHtml;
	}

	public String getTotSendCnt() {
		return totSendCnt;
	}

	public void setTotSendCnt(String totSendCnt) {
		this.totSendCnt = totSendCnt;
	}

	public String getTotSuccCnt() {
		return totSuccCnt;
	}

	public void setTotSuccCnt(String totSuccCnt) {
		this.totSuccCnt = totSuccCnt;
	}

	public String getTotfailCnt() {
		return totfailCnt;
	}

	public void setTotfailCnt(String totfailCnt) {
		this.totfailCnt = totfailCnt;
	}

	public String getFmsSendCnt() {
		return fmsSendCnt;
	}

	public void setFmsSendCnt(String fmsSendCnt) {
		this.fmsSendCnt = fmsSendCnt;
	}

	public String getFmsSuccCnt() {
		return fmsSuccCnt;
	}

	public void setFmsSuccCnt(String fmsSuccCnt) {
		this.fmsSuccCnt = fmsSuccCnt;
	}

	public String getFmsfailCnt() {
		return fmsfailCnt;
	}

	public void setFmsfailCnt(String fmsfailCnt) {
		this.fmsfailCnt = fmsfailCnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getYyyymm() {
		return yyyymm;
	}

	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}

	private List<TransUseListVO> transUseList;



	public String getSmsSendCnt() {
		return smsSendCnt;
	}

	public void setSmsSendCnt(String smsSendCnt) {
		this.smsSendCnt = smsSendCnt;
	}

	public String getSmsSuccCnt() {
		return smsSuccCnt;
	}

	public void setSmsSuccCnt(String smsSuccCnt) {
		this.smsSuccCnt = smsSuccCnt;
	}

	public String getSmsfailCnt() {
		return smsfailCnt;
	}

	public void setSmsfailCnt(String smsfailCnt) {
		this.smsfailCnt = smsfailCnt;
	}

	public String getLmsSendCnt() {
		return lmsSendCnt;
	}

	public void setLmsSendCnt(String lmsSendCnt) {
		this.lmsSendCnt = lmsSendCnt;
	}

	public String getLmsSuccCnt() {
		return lmsSuccCnt;
	}

	public void setLmsSuccCnt(String lmsSuccCnt) {
		this.lmsSuccCnt = lmsSuccCnt;
	}

	public String getLmsfailCnt() {
		return lmsfailCnt;
	}

	public void setLmsfailCnt(String lmsfailCnt) {
		this.lmsfailCnt = lmsfailCnt;
	}

	public String getMmsSendCnt() {
		return mmsSendCnt;
	}

	public void setMmsSendCnt(String mmsSendCnt) {
		this.mmsSendCnt = mmsSendCnt;
	}

	public String getMmsSuccCnt() {
		return mmsSuccCnt;
	}

	public void setMmsSuccCnt(String mmsSuccCnt) {
		this.mmsSuccCnt = mmsSuccCnt;
	}

	public String getMmsfailCnt() {
		return mmsfailCnt;
	}

	public void setMmsfailCnt(String mmsfailCnt) {
		this.mmsfailCnt = mmsfailCnt;
	}

	public String getVmsSendCnt() {
		return vmsSendCnt;
	}

	public void setVmsSendCnt(String vmsSendCnt) {
		this.vmsSendCnt = vmsSendCnt;
	}

	public String getVmsSuccCnt() {
		return vmsSuccCnt;
	}

	public void setVmsSuccCnt(String vmsSuccCnt) {
		this.vmsSuccCnt = vmsSuccCnt;
	}

	public String getVmsfailCnt() {
		return vmsfailCnt;
	}

	public void setVmsfailCnt(String vmsfailCnt) {
		this.vmsfailCnt = vmsfailCnt;
	}

	public String getVmsQrSendCnt() {
		return vmsQrSendCnt;
	}

	public void setVmsQrSendCnt(String vmsQrSendCnt) {
		this.vmsQrSendCnt = vmsQrSendCnt;
	}

	public String getVmsQrSuccCnt() {
		return vmsQrSuccCnt;
	}

	public void setVmsQrSuccCnt(String vmsQrSuccCnt) {
		this.vmsQrSuccCnt = vmsQrSuccCnt;
	}

	public String getVmsQrfailCnt() {
		return vmsQrfailCnt;
	}

	public void setVmsQrfailCnt(String vmsQrfailCnt) {
		this.vmsQrfailCnt = vmsQrfailCnt;
	}

	public String getSmartDmSendCnt() {
		return smartDmSendCnt;
	}

	public void setSmartDmSendCnt(String smartDmSendCnt) {
		this.smartDmSendCnt = smartDmSendCnt;
	}

	public String getSmartDmSuccCnt() {
		return smartDmSuccCnt;
	}

	public void setSmartDmSuccCnt(String smartDmSuccCnt) {
		this.smartDmSuccCnt = smartDmSuccCnt;
	}

	public String getSmartDmfailCnt() {
		return smartDmfailCnt;
	}

	public void setSmartDmfailCnt(String smartDmfailCnt) {
		this.smartDmfailCnt = smartDmfailCnt;
	}

	public String getSmartFaxSendCnt() {
		return smartFaxSendCnt;
	}

	public void setSmartFaxSendCnt(String smartFaxSendCnt) {
		this.smartFaxSendCnt = smartFaxSendCnt;
	}

	public String getSmartFaxSuccCnt() {
		return smartFaxSuccCnt;
	}

	public void setSmartFaxSuccCnt(String smartFaxSuccCnt) {
		this.smartFaxSuccCnt = smartFaxSuccCnt;
	}

	public String getSmartFaxfailCnt() {
		return smartFaxfailCnt;
	}

	public void setSmartFaxfailCnt(String smartFaxfailCnt) {
		this.smartFaxfailCnt = smartFaxfailCnt;
	}

	public List<TransUseListVO> getTransUseList() {
		return transUseList;
	}

	public void setTransUseList(List<TransUseListVO> transUseList) {
		this.transUseList = transUseList;
	}

	@Override
	public String toString() {
		return "MySvcVO [userId=" + userId + ", smsSendCnt=" + smsSendCnt
				+ ", smsSuccCnt=" + smsSuccCnt + ", smsfailCnt=" + smsfailCnt
				+ ", lmsSendCnt=" + lmsSendCnt + ", lmsSuccCnt=" + lmsSuccCnt
				+ ", lmsfailCnt=" + lmsfailCnt + ", mmsSendCnt=" + mmsSendCnt
				+ ", mmsSuccCnt=" + mmsSuccCnt + ", mmsfailCnt=" + mmsfailCnt
				+ ", fmsSendCnt=" + fmsSendCnt + ", fmsSuccCnt=" + fmsSuccCnt
				+ ", fmsfailCnt=" + fmsfailCnt + ", vmsSendCnt=" + vmsSendCnt
				+ ", vmsSuccCnt=" + vmsSuccCnt + ", vmsfailCnt=" + vmsfailCnt
				+ ", vmsQrSendCnt=" + vmsQrSendCnt + ", vmsQrSuccCnt="
				+ vmsQrSuccCnt + ", vmsQrfailCnt=" + vmsQrfailCnt
				+ ", smartDmSendCnt=" + smartDmSendCnt + ", smartDmSuccCnt="
				+ smartDmSuccCnt + ", smartDmfailCnt=" + smartDmfailCnt
				+ ", smartFaxSendCnt=" + smartFaxSendCnt + ", smartFaxSuccCnt="
				+ smartFaxSuccCnt + ", smartFaxfailCnt=" + smartFaxfailCnt
				+ ", totSendCnt=" + totSendCnt + ", totSuccCnt=" + totSuccCnt
				+ ", totfailCnt=" + totfailCnt + ", yyyymm=" + yyyymm
				+ ", umsUserVO=" + umsUserVO + ", totalCount=" + totalCount
				+ ", pagingHtml=" + pagingHtml + ", transUseList="
				+ transUseList + "]";
	}
	
}
