package com.bccns.umsserviceweb.qa.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class QaResultImportResultVO {

    private boolean result;
    private String errMsg;
    private List<QaVO> validSuccessList;
    private List<QaVO> validFailList;
    private List<QaVO> dbFailList;
    
    public QaResultImportResultVO() {
        result = true;
        validSuccessList = new ArrayList<QaVO>();
        validFailList = new ArrayList<QaVO>();
        dbFailList = new ArrayList<QaVO>();
    }
    
    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    
    public List<QaVO> getValidSuccessList() {
        return validSuccessList;
    }

    public void setValidSuccessList(List<QaVO> validSuccessList) {
        this.validSuccessList = validSuccessList;
    }

    public List<QaVO> getValidFailList() {
        return validFailList;
    }

    public void setValidFailList(List<QaVO> validFailList) {
        this.validFailList = validFailList;
    }

    public List<QaVO> getDbFailList() {
        return dbFailList;
    }

    public void setDbFailList(List<QaVO> dbFailList) {
        this.dbFailList = dbFailList;
    }
    
    public void addValidSuccessList(QaVO vo) {
        this.validSuccessList.add(vo);
    }
    
    public void addValidFailList(QaVO vo) {
        this.validFailList.add(vo);
    }
    
    public void addDbFailList(QaVO vo) {
        this.dbFailList.add(vo);
    }

    public String toJsonStr() {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(this);     
        } catch (Exception e) {
            
            StringBuffer buf = new StringBuffer();
            buf.append("{");
            buf.append("\"result\":false,");
            buf.append("\"errMsg\":\"" + e.getMessage() + "\"");
            buf.append("}");
            
            return buf.toString();
        }
    }
    
}
