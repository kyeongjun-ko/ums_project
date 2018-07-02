package com.bccns.umsserviceweb.ums.vo;

import java.util.Arrays;
import java.util.List;

import com.bccns.umsserviceweb.common.vo.UsrGrpVO;
import com.bccns.umsserviceweb.mgr.vo.MsgBoxVO;

public class FmsVO {
	private String userId;	
	private String msgSubtype;
	private String scheduleType;
	private String destType; 

	private String subject;
	private String nowDate;
	private String sendDate;
	private String callback;
 	private String ktOfficeCode;
	
	private String cdrId;
	private String destCount;
	private String destInfo;
	
	private String attachFile;
	
	private String reserved1;
	private String reserved2;
	private String reserved3;
	private String reserved4;
	private String reserved5;
	private String reserved6;
	private String reserved7;
	private String reserved8;
	private String reserved9;
	private String sendStatus;
	private String sendCount;
	private String sendResult;
	private String sendProcTime; 
	private String stdId;
	

	private String reserveYear; 
	private String reserveMonth;
	private String reserveDay;
	private String reserveHour;
	private String reserveMin;
	
	
	private String repeatType;
	private String repeatHour;
	private String repeatMin;
	private String repeatDay;
	private String repeatWeek;
	
    private List<MsgBoxVO> msgBoxList;
    private int totalCount;
    private String pagingHtml;

    /*
     * file_name 배열
     */
    public String[] file_name;
    /*
     * file_size 배열
     */
    public String[] file_size;
    
    /*
     * file_type 배열
     */
    public String[] file_type;
    
    /*
     * file_path 배열
     */
    public String[] file_path;
    /*
     * file_servername 배열
     */
    public String[] file_servername;
    /*
     * file_contenttype 배열
     */
    public String[] file_contenttype;
	
    
    private List<UsrGrpVO> usrGrpList;
    
    
    
	public List<UsrGrpVO> getUsrGrpList() {
		return usrGrpList;
	}
	public void setUsrGrpList(List<UsrGrpVO> usrGrpList) {
		this.usrGrpList = usrGrpList;
	}
	public String[] getFile_name() {
		return file_name;
	}
	public void setFile_name(String[] file_name) {
		this.file_name = file_name;
	}
	public String[] getFile_size() {
		return file_size;
	}
	public void setFile_size(String[] file_size) {
		this.file_size = file_size;
	}
	public String[] getFile_type() {
		return file_type;
	}
	public void setFile_type(String[] file_type) {
		this.file_type = file_type;
	}
	public String[] getFile_path() {
		return file_path;
	}
	public void setFile_path(String[] file_path) {
		this.file_path = file_path;
	}
	public String[] getFile_servername() {
		return file_servername;
	}
	public void setFile_servername(String[] file_servername) {
		this.file_servername = file_servername;
	}
	public String[] getFile_contenttype() {
		return file_contenttype;
	}
	public void setFile_contenttype(String[] file_contenttype) {
		this.file_contenttype = file_contenttype;
	}
	
	
	@Override
	public String toString() {
		return "FmsVO [userId=" + userId + ", msgSubtype=" + msgSubtype
				+ ", scheduleType=" + scheduleType + ", destType=" + destType
				+ ", subject=" + subject + ", nowDate=" + nowDate
				+ ", sendDate=" + sendDate + ", callback=" + callback
				+ ", ktOfficeCode=" + ktOfficeCode + ", cdrId=" + cdrId
				+ ", destCount=" + destCount + ", destInfo=" + destInfo
				+ ", attachFile=" + attachFile + ", reserved1=" + reserved1
				+ ", reserved2=" + reserved2 + ", reserved3=" + reserved3
				+ ", reserved4=" + reserved4 + ", reserved5=" + reserved5
				+ ", reserved6=" + reserved6 + ", reserved7=" + reserved7
				+ ", reserved8=" + reserved8 + ", reserved9=" + reserved9
				+ ", sendStatus=" + sendStatus + ", sendCount=" + sendCount
				+ ", sendResult=" + sendResult + ", sendProcTime="
				+ sendProcTime + ", stdId=" + stdId + ", reserveYear="
				+ reserveYear + ", reserveMonth=" + reserveMonth
				+ ", reserveDay=" + reserveDay + ", reserveHour=" + reserveHour
				+ ", reserveMin=" + reserveMin + ", repeatType=" + repeatType
				+ ", repeatHour=" + repeatHour + ", repeatMin=" + repeatMin
				+ ", repeatDay=" + repeatDay + ", repeatWeek=" + repeatWeek
				+ ", msgBoxList=" + msgBoxList + ", totalCount=" + totalCount
				+ ", pagingHtml=" + pagingHtml + ", file_name="
				+ Arrays.toString(file_name) + ", file_size="
				+ Arrays.toString(file_size) + ", file_type="
				+ Arrays.toString(file_type) + ", file_path="
				+ Arrays.toString(file_path) + ", file_servername="
				+ Arrays.toString(file_servername) + ", file_contenttype="
				+ Arrays.toString(file_contenttype) + ", usrGrpList="
				+ usrGrpList + "]";
	}
	public String getReserveYear() {
		return reserveYear;
	}
	public void setReserveYear(String reserveYear) {
		this.reserveYear = reserveYear;
	}
	public String getReserveMonth() {
		return reserveMonth;
	}
	public void setReserveMonth(String reserveMonth) {
		this.reserveMonth = reserveMonth;
	}
	public String getReserveDay() {
		return reserveDay;
	}
	public void setReserveDay(String reserveDay) {
		this.reserveDay = reserveDay;
	}
	public String getReserveHour() {
		return reserveHour;
	}
	public void setReserveHour(String reserveHour) {
		this.reserveHour = reserveHour;
	}
	public String getReserveMin() {
		return reserveMin;
	}
	public void setReserveMin(String reserveMin) {
		this.reserveMin = reserveMin;
	}
	public String getRepeatType() {
		return repeatType;
	}
	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}
	public String getRepeatHour() {
		return repeatHour;
	}
	public void setRepeatHour(String repeatHour) {
		this.repeatHour = repeatHour;
	}
	public String getRepeatMin() {
		return repeatMin;
	}
	public void setRepeatMin(String repeatMin) {
		this.repeatMin = repeatMin;
	}
	public String getRepeatDay() {
		return repeatDay;
	}
	public void setRepeatDay(String repeatDay) {
		this.repeatDay = repeatDay;
	}
	public String getRepeatWeek() {
		return repeatWeek;
	}
	public void setRepeatWeek(String repeatWeek) {
		this.repeatWeek = repeatWeek;
	}
	public List<MsgBoxVO> getMsgBoxList() {
		return msgBoxList;
	}
	public void setMsgBoxList(List<MsgBoxVO> msgBoxList) {
		this.msgBoxList = msgBoxList;
	}
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsgSubtype() {
		return msgSubtype;
	}
	public void setMsgSubtype(String msgSubtype) {
		this.msgSubtype = msgSubtype;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getDestType() {
		return destType;
	}
	public void setDestType(String destType) {
		this.destType = destType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getKtOfficeCode() {
		return ktOfficeCode;
	}
	public void setKtOfficeCode(String ktOfficeCode) {
		this.ktOfficeCode = ktOfficeCode;
	}
	public String getCdrId() {
		return cdrId;
	}
	public void setCdrId(String cdrId) {
		this.cdrId = cdrId;
	}
	public String getDestCount() {
		return destCount;
	}
	public void setDestCount(String destCount) {
		this.destCount = destCount;
	}
	public String getDestInfo() {
		return destInfo;
	}
	public void setDestInfo(String destInfo) {
		this.destInfo = destInfo;
	}
	public String getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(String attachFile) {
		this.attachFile = attachFile;
	}
	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getReserved3() {
		return reserved3;
	}
	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	public String getReserved4() {
		return reserved4;
	}
	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}
	public String getReserved5() {
		return reserved5;
	}
	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}
	public String getReserved6() {
		return reserved6;
	}
	public void setReserved6(String reserved6) {
		this.reserved6 = reserved6;
	}
	public String getReserved7() {
		return reserved7;
	}
	public void setReserved7(String reserved7) {
		this.reserved7 = reserved7;
	}
	public String getReserved8() {
		return reserved8;
	}
	public void setReserved8(String reserved8) {
		this.reserved8 = reserved8;
	}
	public String getReserved9() {
		return reserved9;
	}
	public void setReserved9(String reserved9) {
		this.reserved9 = reserved9;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getSendCount() {
		return sendCount;
	}
	public void setSendCount(String sendCount) {
		this.sendCount = sendCount;
	}
	public String getSendResult() {
		return sendResult;
	}
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	public String getSendProcTime() {
		return sendProcTime;
	}
	public void setSendProcTime(String sendProcTime) {
		this.sendProcTime = sendProcTime;
	}
	public String getStdId() {
		return stdId;
	}
	public void setStdId(String stdId) {
		this.stdId = stdId;
	} 
	
	
	
}
