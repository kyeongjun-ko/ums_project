package com.bccns.umsserviceweb.ums.exception;

public class UmsSendProcException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2406497504286328980L;

    private String msg;
    
    public UmsSendProcException() {
        super();
    }
    
    public UmsSendProcException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String getMessage() {
        return msg;
    }
    
    
}
