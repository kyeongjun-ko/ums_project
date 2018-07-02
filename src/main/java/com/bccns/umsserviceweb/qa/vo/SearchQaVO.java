package com.bccns.umsserviceweb.qa.vo;

import java.util.List;

import com.bccns.umsserviceweb.common.pagination.PaginationInfo;


public class SearchQaVO extends PaginationInfo {
    
    private String srchQaId;
    
    private String qaId;
    
    private String srchTerritory;
    
    private String srchContTitle;
    
    private String srchCpNm;
    
    private String srchVendorNm;
    
    private String[] arrCpNm;
    
    private String[] arrVendorNm;
    
    private String srchStatus;
    
    private String srchContType;
    
    private String srchEstStartDate;
    
    private String srchEstEndDate;
    
    private String srchVodStartDate;
    
    private String srchVodEndDate;
    
    private String srchReqStartDate;
    
    private String srchReqEndDate;
    
    private String srchComplStartDate;
    
    private String srchComplEndDate;
    
    private String srchMasterId;
    
    private String srchCid;
    
    //private String[] arrQaResultOther;
    
    private List<String> arrQaResultOther;
    
    private String[] arrQaResult;
    
    private String[] arrProdState;
    
    private String flagDel;
    
    private String[] arrQaId;
    
    private String[] arrProductId;
    
    private String[] arrTerritory;
    
    private String[] arrRegion;
    
    private String qaHisFlag;
    
    private String qaReqComplFlag;
    
    private String uiSearchFlag;
    
    
    /*
    public String[] getArrQaResultOther() {
        return arrQaResultOther;
    }

    public void setArrQaResultOther(String[] arrQaResultOther) {
        this.arrQaResultOther = arrQaResultOther;
    }
    */
    
    public String getUiSearchFlag() {
        return uiSearchFlag;
    }

    public List<String> getArrQaResultOther() {
        return arrQaResultOther;
    }

    public void setArrQaResultOther(List<String> arrQaResultOther) {
        this.arrQaResultOther = arrQaResultOther;
    }

    public void setUiSearchFlag(String uiSearchFlag) {
        this.uiSearchFlag = uiSearchFlag;
    }

    public String[] getArrProdState() {
        return arrProdState;
    }

    public void setArrProdState(String[] arrProdState) {
        this.arrProdState = arrProdState;
    }

    public String[] getArrRegion() {
        return arrRegion;
    }

    public void setArrRegion(String[] arrRegion) {
        this.arrRegion = arrRegion;
    }

    public String[] getArrQaResult() {
        return arrQaResult;
    }

    public void setArrQaResult(String[] arrQaResult) {
        this.arrQaResult = arrQaResult;
    }

    public String getSrchComplStartDate() {
        return srchComplStartDate;
    }

    public void setSrchComplStartDate(String srchComplStartDate) {
        this.srchComplStartDate = srchComplStartDate;
    }

    public String getSrchComplEndDate() {
        return srchComplEndDate;
    }

    public void setSrchComplEndDate(String srchComplEndDate) {
        this.srchComplEndDate = srchComplEndDate;
    }

    public String[] getArrCpNm() {
        return arrCpNm;
    }

    public void setArrCpNm(String[] arrCpNm) {
        this.arrCpNm = arrCpNm;
    }

    public String[] getArrVendorNm() {
        return arrVendorNm;
    }

    public void setArrVendorNm(String[] arrVendorNm) {
        this.arrVendorNm = arrVendorNm;
    }

    public String getSrchCpNm() {
        return srchCpNm;
    }

    public void setSrchCpNm(String srchCpNm) {
        this.srchCpNm = srchCpNm;
    }

    public String getSrchVendorNm() {
        return srchVendorNm;
    }

    public void setSrchVendorNm(String srchVendorNm) {
        this.srchVendorNm = srchVendorNm;
    }

    public String getSrchStatus() {
        return srchStatus;
    }

    public void setSrchStatus(String srchStatus) {
        this.srchStatus = srchStatus;
    }

    public String getSrchContType() {
        return srchContType;
    }

    public void setSrchContType(String srchContType) {
        this.srchContType = srchContType;
    }

    public String getSrchEstStartDate() {
        return srchEstStartDate;
    }

    public void setSrchEstStartDate(String srchEstStartDate) {
        this.srchEstStartDate = srchEstStartDate;
    }

    public String getSrchEstEndDate() {
        return srchEstEndDate;
    }

    public void setSrchEstEndDate(String srchEstEndDate) {
        this.srchEstEndDate = srchEstEndDate;
    }

    public String getSrchVodStartDate() {
        return srchVodStartDate;
    }

    public void setSrchVodStartDate(String srchVodStartDate) {
        this.srchVodStartDate = srchVodStartDate;
    }

    public String getSrchVodEndDate() {
        return srchVodEndDate;
    }

    public void setSrchVodEndDate(String srchVodEndDate) {
        this.srchVodEndDate = srchVodEndDate;
    }

    public String getSrchReqStartDate() {
        return srchReqStartDate;
    }

    public void setSrchReqStartDate(String srchReqStartDate) {
        this.srchReqStartDate = srchReqStartDate;
    }

    public String getSrchReqEndDate() {
        return srchReqEndDate;
    }

    public void setSrchReqEndDate(String srchReqEndDate) {
        this.srchReqEndDate = srchReqEndDate;
    }

    public String getSrchMasterId() {
        return srchMasterId;
    }

    public void setSrchMasterId(String srchMasterId) {
        this.srchMasterId = srchMasterId;
    }

    public String getSrchCid() {
        return srchCid;
    }

    public void setSrchCid(String srchCid) {
        this.srchCid = srchCid;
    }

    public String[] getArrTerritory() {
        return arrTerritory;
    }

    public void setArrTerritory(String[] arrTerritory) {
        this.arrTerritory = arrTerritory;
    }

    public String getQaReqComplFlag() {
        return qaReqComplFlag;
    }

    public void setQaReqComplFlag(String qaReqComplFlag) {
        this.qaReqComplFlag = qaReqComplFlag;
    }

    public String getQaId() {
        return qaId;
    }

    public void setQaId(String qaId) {
        this.qaId = qaId;
    }

    public String getQaHisFlag() {
        return qaHisFlag;
    }

    public void setQaHisFlag(String qaHisFlag) {
        this.qaHisFlag = qaHisFlag;
    }

    public String[] getArrProductId() {
        return arrProductId;
    }

    public void setArrProductId(String[] arrProductId) {
        this.arrProductId = arrProductId;
    }

    public String getSrchContTitle() {
        return srchContTitle;
    }

    public void setSrchContTitle(String srchContTitle) {
        this.srchContTitle = srchContTitle;
    }

    public String getSrchTerritory() {
        return srchTerritory;
    }

    public void setSrchTerritory(String srchTerritory) {
        this.srchTerritory = srchTerritory;
    }

    public String getSrchQaId() {
        return srchQaId;
    }

    public void setSrchQaId(String srchQaId) {
        this.srchQaId = srchQaId;
    }

    public String getFlagDel() {
        return flagDel;
    }

    public void setFlagDel(String flagDel) {
        this.flagDel = flagDel;
    }

    public String[] getArrQaId() {
        return arrQaId;
    }

    public void setArrQaId(String[] arrQaId) {
        this.arrQaId = arrQaId;
    }
    
    
        
    

    

}
