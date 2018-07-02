package com.bccns.umsserviceweb.qa.vo;

import java.util.List;
import java.util.Map;

import com.bccns.umsserviceweb.common.vo.CommonAbstractVO;

public class QaVO extends CommonAbstractVO {
    
    
    private String qaId;
    private String qaHisSeq;
    private String orderId = "";
    private String qaResult;
    private String encodeVendor;
    private String state        ;
    private String description;
    private String reqUserId;
    private String reqUserIdEmail;
    private String reqDate   ;
    private String reqDateAmPm ;
    private String updUserId;  
    private String updDate    ;
    private String productId   ;
    private String verifyUserId;
    private String verifyUserIdEmail;
    private String verifyDate; 
    private String verifyDateAmPm;
    private String massYn     ;
    private String cid          ;
    private String cancelYn     ;
    private String cpId          ;
    private String territory     ;
    private String territoryNm   ;
    private String contType      ;
    private String contTypeNm    ;
    private String urgentFlag    ;
    private String reqCont       ;
    private String reqFlag       ;
    private String contTitle     ; 
    private String encodeVendorNm;
    private String cpNm          ;
    private String backTrackType ;
    private String serviceId;
    
    private String shopId;
    
    private String curLibType;
        
    private List<QaItemVO> itemList;
    
    private String[] arrProductId;
    
    private String[] arrQaId;
    
    private String[] arrItem;
    
    private String[] arrRItem;
    
    private String[] arrCItem;
    
    private String[] arrRMeta;
    
    private String qaCnt    ;
    
    private List<Map> arrMyGroup;
    
    private String reqPopUpFlag;
    
    private Map cmbBackTrackType; 
    
    private String qaFailVideo;
    
    private String qaFailMeta;
    
    private String prodCurState;
    
    private String publishDate;
    
    private Integer rowIndex;
    
    private String returnMsg;
    
    private String videoErrorCode;
    private String metaErrorCode;
    private String errorDescription;
    
    private String est_start_date;
    private String vod_start_date;
    
    public String getReqUserIdEmail() {
        return reqUserIdEmail;
    }

    public void setReqUserIdEmail(String reqUserIdEmail) {
        this.reqUserIdEmail = reqUserIdEmail;
    }

    public String getVerifyUserIdEmail() {
        return verifyUserIdEmail;
    }

    public void setVerifyUserIdEmail(String verifyUserIdEmail) {
        this.verifyUserIdEmail = verifyUserIdEmail;
    }

    public String getVerifyDateAmPm() {
        return verifyDateAmPm;
    }

    public void setVerifyDateAmPm(String verifyDateAmPm) {
        this.verifyDateAmPm = verifyDateAmPm;
    }

    public String getReqDateAmPm() {
        return reqDateAmPm;
    }

    public void setReqDateAmPm(String reqDateAmPm) {
        this.reqDateAmPm = reqDateAmPm;
    }

    public String getEst_start_date() {
        return est_start_date;
    }

    public void setEst_start_date(String est_start_date) {
        this.est_start_date = est_start_date;
    }

    public String getVod_start_date() {
        return vod_start_date;
    }

    public void setVod_start_date(String vod_start_date) {
        this.vod_start_date = vod_start_date;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getProdCurState() {
        return prodCurState;
    }

    public void setProdCurState(String prodCurState) {
        this.prodCurState = prodCurState;
    }

    public String getQaFailVideo() {
        return qaFailVideo;
    }

    public void setQaFailVideo(String qaFailVideo) {
        this.qaFailVideo = qaFailVideo;
    }

    public String getQaFailMeta() {
        return qaFailMeta;
    }

    public void setQaFailMeta(String qaFailMeta) {
        this.qaFailMeta = qaFailMeta;
    }

    public String getCurLibType() {
        return curLibType;
    }

    public void setCurLibType(String curLibType) {
        this.curLibType = curLibType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String[] getArrQaId() {
        return arrQaId;
    }

    public void setArrQaId(String[] arrQaId) {
        this.arrQaId = arrQaId;
    }

    public String[] getArrCItem() {
        return arrCItem;
    }

    public void setArrCItem(String[] arrCItem) {
        this.arrCItem = arrCItem;
    }

    public String getQaCnt() {
        return qaCnt;
    }

    public void setQaCnt(String qaCnt) {
        this.qaCnt = qaCnt;
    }

    public String getBackTrackType() {
        return backTrackType;
    }

    public void setBackTrackType(String backTrackType) {
        this.backTrackType = backTrackType;
    }

    public Map getCmbBackTrackType() {
        return cmbBackTrackType;
    }

    public void setCmbBackTrackType(Map cmbBackTrackType) {
        this.cmbBackTrackType = cmbBackTrackType;
    }

    public String[] getArrRMeta() {
        return arrRMeta;
    }

    public void setArrRMeta(String[] arrRMeta) {
        this.arrRMeta = arrRMeta;
    }

    public String[] getArrRItem() {
        return arrRItem;
    }

    public void setArrRItem(String[] arrRItem) {
        this.arrRItem = arrRItem;
    }

    public String getEncodeVendor() {
        return encodeVendor;
    }

    public void setEncodeVendor(String encodeVendor) {
        this.encodeVendor = encodeVendor;
    }

    public String getQaHisSeq() {
        return qaHisSeq;
    }

    public void setQaHisSeq(String qaHisSeq) {
        this.qaHisSeq = qaHisSeq;
    }

    public String getReqPopUpFlag() {
        return reqPopUpFlag;
    }

    public void setReqPopUpFlag(String reqPopUpFlag) {
        this.reqPopUpFlag = reqPopUpFlag;
    }

    public String getTerritoryNm() {
        return territoryNm;
    }

    public void setTerritoryNm(String territoryNm) {
        this.territoryNm = territoryNm;
    }

    public String getContTypeNm() {
        return contTypeNm;
    }

    public void setContTypeNm(String contTypeNm) {
        this.contTypeNm = contTypeNm;
    }

    public List<QaItemVO> getItemList() {
        return itemList;
    }

    public void setItemList(List<QaItemVO> itemList) {
        this.itemList = itemList;
    }

    public List<Map> getArrMyGroup() {
        return arrMyGroup;
    }

    public void setArrMyGroup(List<Map> arrMyGroup) {
        this.arrMyGroup = arrMyGroup;
    }

    public String[] getArrItem() {
        return arrItem;
    }

    public void setArrItem(String[] arrItem) {
        this.arrItem = arrItem;
    }

    public String[] getArrProductId() {
        return arrProductId;
    }

    public void setArrProductId(String[] arrProductId) {
        this.arrProductId = arrProductId;
    }
    
    public String getQaId() {
        return qaId;
    }
    public void setQaId(String qaId) {
        this.qaId = qaId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getQaResult() {
        return qaResult;
    }
    public void setQaResult(String qaResult) {
        this.qaResult = qaResult;
    }
    
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getReqUserId() {
        return reqUserId;
    }
    public void setReqUserId(String reqUserId) {
        this.reqUserId = reqUserId;
    }
    public String getReqDate() {
        return reqDate;
    }
    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }
    public String getUpdUserId() {
        return updUserId;
    }
    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }
    public String getUpdDate() {
        return updDate;
    }
    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getVerifyUserId() {
        return verifyUserId;
    }
    public void setVerifyUserId(String verifyUserId) {
        this.verifyUserId = verifyUserId;
    }
    public String getVerifyDate() {
        return verifyDate;
    }
    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }
    public String getMassYn() {
        return massYn;
    }
    public void setMassYn(String massYn) {
        this.massYn = massYn;
    }
    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCancelYn() {
        return cancelYn;
    }
    public void setCancelYn(String cancelYn) {
        this.cancelYn = cancelYn;
    }
    public String getCpId() {
        return cpId;
    }
    public void setCpId(String cpId) {
        this.cpId = cpId;
    }
    public String getTerritory() {
        return territory;
    }
    public void setTerritory(String territory) {
        this.territory = territory;
    }
    public String getContType() {
        return contType;
    }
    public void setContType(String contType) {
        this.contType = contType;
    }
    public String getUrgentFlag() {
        return urgentFlag;
    }
    public void setUrgentFlag(String urgentFlag) {
        this.urgentFlag = urgentFlag;
    }
    public String getReqCont() {
        return reqCont;
    }
    public void setReqCont(String reqCont) {
        this.reqCont = reqCont;
    }
    public String getReqFlag() {
        return reqFlag;
    }
    public void setReqFlag(String reqFlag) {
        this.reqFlag = reqFlag;
    }
    public String getContTitle() {
        return contTitle;
    }
    public void setContTitle(String contTitle) {
        this.contTitle = contTitle;
    }
    public String getEncodeVendorNm() {
        return encodeVendorNm;
    }
    public void setEncodeVendorNm(String encodeVendorNm) {
        this.encodeVendorNm = encodeVendorNm;
    }
    public String getCpNm() {
        return cpNm;
    }
    public void setCpNm(String cpNm) {
        this.cpNm = cpNm;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getVideoErrorCode() {
        return videoErrorCode;
    }

    public void setVideoErrorCode(String videoErrorCode) {
        this.videoErrorCode = videoErrorCode;
    }

    public String getMetaErrorCode() {
        return metaErrorCode;
    }

    public void setMetaErrorCode(String metaErrorCode) {
        this.metaErrorCode = metaErrorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
