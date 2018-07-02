package com.bccns.umsserviceweb.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.support.HandlerMethodInvocationException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.UrlPathHelper;

import com.bccns.umsserviceweb.common.exception.SimpleException;
import com.bccns.umsserviceweb.common.vo.ErrorVO;

/**
 * 스프링 3.2 에서는 @ControllerAdvice 로 구현하면 상속 받지 않아도 된다.
 * 
 * Exception HTTP Status Code ConversionNotSupportedException 500 (Internal
 * Server Error) HttpMediaTypeNotAcceptableException 406 (Not Acceptable)
 * HttpMediaTypeNotSupportedException 415 (Unsupported Media Type)
 * HttpMessageNotReadableException 400 (Bad Request)
 * HttpMessageNotWritableException 500 (Internal Server Error)
 * HttpRequestMethodNotSupportedException 405 (Method Not Allowed)
 * MissingServletRequestParameterException 400 (Bad Request)
 * NoSuchRequestHandlingMethodException 404 (Not Found) TypeMismatchException
 * 400 (Bad Request)
 */
public class DefaultAPIController {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultAPIController.class);

	private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Autowired
	private HttpServletRequest request;

	/**
	 * handleException - controller , 비지니스단에서 나는 에러처리
	 * 
	 * @return ServerErrorCode
	 */
	@ExceptionHandler(SimpleException.class)
	@ResponseBody
	protected ErrorVO handleException(SimpleException serviceException,
			HttpServletResponse response) {
		response.setStatus(serviceException.getErrorVO().getHttpStatus());

		String requestUri = urlPathHelper.getRequestUri(request);
		logger.error("handleException >>>> HTTP request with URI ["
				+ requestUri + " ] "
				+ serviceException.getErrorVO().getMessage());

		return serviceException.getErrorVO();
	}

	/**
	 * HttpMessageNotReadableException (400 (Bad Request)) - controller 타기전
	 * DispatcherServlet 나는에러처리
	 * 
	 * @return ServerErrorCode
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected Map<Object, Object> httpMessageNotReadableException(
			HttpMessageNotReadableException serviceException) {

		String requestUri = urlPathHelper.getRequestUri(request);
		logger.error("DispatcherServlet HttpMessageNotReadableException >>>> HTTP request with URI ["
				+ requestUri + " ] " + serviceException.getMessage());

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("errorCode", HttpStatus.BAD_REQUEST.value());
		map.put("message", HttpStatus.BAD_REQUEST.getReasonPhrase() + " \n "
				+ serviceException.getMessage());
		return map;
	}

	/**
	 * NoSuchRequestHandlingMethodException (404 (Not Found)) - controller 타기전
	 * DispatcherServlet 나는에러처리
	 * 
	 * @return ServerErrorCode
	 */
	@ExceptionHandler(NoSuchRequestHandlingMethodException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected Map<Object, Object> noSuchRequestHandlingMethodException(
			NoSuchRequestHandlingMethodException serviceException) {

		String requestUri = urlPathHelper.getRequestUri(request);
		logger.error("DispatcherServlet NoSuchRequestHandlingMethodException >>>> HTTP request with URI ["
				+ requestUri + " ] " + serviceException.getMessage());

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("errorCode", HttpStatus.NOT_FOUND.value());
		map.put("message", HttpStatus.NOT_FOUND.getReasonPhrase() + " \n "
				+ serviceException.getMessage());
		return map;
	}

	/**
	 * HttpRequestMethodNotSupportedException (405 (Method Not Allowed , Request
	 * method = RequestMethod. not supported) ) - controller 타기전
	 * DispatcherServlet 나는에러처리
	 * 
	 * @return ServerErrorCode
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	protected Map<Object, Object> httpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException serviceException) {

		String requestUri = urlPathHelper.getRequestUri(request);
		logger.error("DispatcherServlet HttpRequestMethodNotSupportedException >>>> HTTP request with URI ["
				+ requestUri + " ] " + serviceException.getMessage());

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("errorCode", HttpStatus.METHOD_NOT_ALLOWED.value());
		map.put("message", HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()
				+ " \n " + serviceException.getMessage());
		return map;
	}

	/**
	 * HttpRequestMethodNotSupportedException (415 (Unsupported Media Type) ) -
	 * controller 타기전 DispatcherServlet 나는에러처리
	 * 
	 * @return ServerErrorCode
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ResponseBody
	protected Map<Object, Object> httpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException serviceException) {

		String requestUri = urlPathHelper.getRequestUri(request);
		logger.error("DispatcherServlet HttpMediaTypeNotSupportedException >>>> HTTP request with URI ["
				+ requestUri + " ] " + serviceException.getMessage());

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("errorCode", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
		map.put("message", HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()
				+ " \n " + serviceException.getMessage());
		return map;
	}

	/**
	 * HandlerMethodInvocationException (500 (Missing header) ) - controller 타기전
	 * DispatcherServlet 나는에러처리
	 * 
	 * @return ServerErrorCode
	 */
	@ExceptionHandler(HandlerMethodInvocationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	protected Map<Object, Object> handlerMethodInvocationException(
			HandlerMethodInvocationException serviceException) {

		String requestUri = urlPathHelper.getRequestUri(request);
		logger.error("DispatcherServlet HandlerMethodInvocationException >>>> HTTP request with URI ["
				+ requestUri + " ] " + serviceException.getMessage());

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
		map.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
				+ " \n " + serviceException.getMessage());
		return map;
	}
}
