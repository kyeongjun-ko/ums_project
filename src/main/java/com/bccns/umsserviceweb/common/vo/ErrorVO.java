package com.bccns.umsserviceweb.common.vo;


public class ErrorVO {
	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The http status. */
    private int               httpStatus;

    /** The code. */
    private String            errorCode;

    /** The message. */
    private String            message;

    /** The resource. */
    private String            resource;

    /** The dev message. */
    private String            devMessage;

    public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getDevMessage() {
		return devMessage;
	}

	public void setDevMessage(String devMessage) {
		this.devMessage = devMessage;
	}

	/*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( !( obj instanceof ErrorVO ) )
            return false;
        ErrorVO target = (ErrorVO) obj;
        if ( target.getErrorCode() == this.getErrorCode()
                && target.getHttpStatus() == this.getHttpStatus()
                && target.getMessage() == this.getMessage()
                && target.getResource() == this.getResource() 
                && target.getDevMessage() == this.devMessage)
            return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString(java.lang.Object)
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n >> httpStatus=[").append(this.httpStatus).append("]");
        sb.append("\n >> error_code=[").append(this.errorCode).append("]");
        sb.append("\n >> resource=[").append(this.resource).append("]");
        sb.append("\n >> message=[").append(this.message).append("]");
        sb.append("\n >> devMessage=[").append(this.devMessage).append("]");
        return sb.toString();
    }
}
