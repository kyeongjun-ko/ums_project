package com.bccns.umsserviceweb.qa.vo;

import java.io.Serializable;

public class QaItemVO  implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String qaId;
    private String qaHisSeq;
    private String reqRstType;
    private String itemSeq;
    private String item;
    private String result;
    
    
    public String getQaHisSeq() {
        return qaHisSeq;
    }
    public void setQaHisSeq(String qaHisSeq) {
        this.qaHisSeq = qaHisSeq;
    }
    public String getQaId() {
        return qaId;
    }
    public void setQaId(String qaId) {
        this.qaId = qaId;
    }
    public String getReqRstType() {
        return reqRstType;
    }
    public void setReqRstType(String reqRstType) {
        this.reqRstType = reqRstType;
    }
    public String getItemSeq() {
        return itemSeq;
    }
    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    

}
