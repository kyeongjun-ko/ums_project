package com.bccns.umsserviceweb.common.exception;

import com.bccns.umsserviceweb.common.vo.ErrorVO;


public class SimpleException extends Exception {
	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4021436054174208556L;

    /** The error vo. */
    private ErrorVO           errorVO;

    /** The exception. */
    private Exception         exception;

    /**
     * Instantiates a new SimpleException exception.
     */
    private SimpleException() {
        super();
    }

    /**
     * Instantiates a new SimpleException exception.
     *
     * @param httpStatus the http status
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param resource the resource
     */
    public SimpleException(int httpStatus, String errorCode, String errorMessage, String resource) {
        this();
        this.errorVO = new ErrorVO();
        this.errorVO.setHttpStatus(httpStatus);
        this.errorVO.setErrorCode(errorCode);
        this.errorVO.setMessage(errorMessage);
        this.errorVO.setResource(resource);
    }

    /**
     * Instantiates a new SimpleException exception.
     *
     * @param httpStatus the http status
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param resource the resource
     * @param e the e
     */
    public SimpleException(int httpStatus, String errorCode, String errorMessage, String resource,
            RuntimeException e) {
        this();
        this.errorVO = new ErrorVO();
        this.errorVO.setHttpStatus(httpStatus);
        this.errorVO.setErrorCode(errorCode);
        this.errorVO.setMessage(errorMessage);
        this.errorVO.setResource(resource);
        this.exception = e;

    }

    /**
     * Instantiates a new SimpleException exception.
     *
     * @param httpStatus the http status
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param devMessage the dev message
     * @param resource the resource
     * @param e the e
     */
    public SimpleException(int httpStatus, String errorCode, String errorMessage,
            String devMessage, String resource, Exception e) {
        this();
        this.errorVO = new ErrorVO();
        this.errorVO.setHttpStatus(httpStatus);
        this.errorVO.setErrorCode(errorCode);
        this.errorVO.setMessage(errorMessage);
        this.errorVO.setDevMessage(devMessage);
        this.errorVO.setResource(resource);
        this.exception = e;
    }

    /**
     * Instantiates a new SimpleException exception.
     * 
     * @param vo
     *            the vo
     */
    public SimpleException(ErrorVO vo) {

        this.errorVO = vo;

    }

    /**
     * Instantiates a new SimpleException exception.
     * 
     * @param vo
     *            the vo
     * @param e
     *            the e
     */
    public SimpleException(ErrorVO vo, Exception e) {
        this.errorVO = vo;
        this.exception = e;
    }

    /**
     * Gets the error vo.
     * 
     * @return the error vo
     */
    public ErrorVO getErrorVO() {
        return errorVO;
    }

    /**
     * Sets the error vo.
     * 
     * @param errorVO
     *            the new error vo
     */
    public void setErrorVO(ErrorVO errorVO) {
        this.errorVO = errorVO;
    }

    /**
     * Gets the exception.
     * 
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Sets the exception.
     * 
     * @param exception
     *            the new exception
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    /**
     * Instantiates a new SimpleException exception.
     *
     * @param httpStatus the http status
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param devMessage the dev message
     * @param resource the resource
     * @param e the e
     */
    public SimpleException(int httpStatus, String errorCode) {
        this();
        this.errorVO = new ErrorVO();
        this.errorVO.setHttpStatus(httpStatus);
        this.errorVO.setErrorCode(errorCode);
    }
}
