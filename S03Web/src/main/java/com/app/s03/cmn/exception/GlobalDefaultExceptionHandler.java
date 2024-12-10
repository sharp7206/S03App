package com.app.s03.cmn.exception;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.utils.ConChar;
import com.app.s03.za.service.Z01Service;


@RestControllerAdvice(annotations = RestController.class)
public class GlobalDefaultExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";
	@Resource
	private Z01Service z01Service;
	
	@Order(1)
	@ExceptionHandler(CommonBusinessException.class)
	public ResponseEntity<Map<String, Object>> handleJsysBusinessException(CommonBusinessException ex,HttpServletRequest request) {
		Map<String, Object> res = new LinkedHashMap<>(); // ("500", "Internal Server Error");
		res.put(ReturnCode.RTN_CODE,ReturnCode.ERROR);
		res.put("message",ex.getLocalizedMessage());
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String headerName = "";
    	String WIN_CD = "";
    	Enumeration<String> headerNames = request.getHeaderNames();
    	String headerValue = "";
		while(headerNames.hasMoreElements()) {
			headerName = headerNames.nextElement();
			if("referer".equals(headerName)) WIN_CD = request.getHeader(headerName);
			headerValue = request.getHeader(headerName);
			//System.out.println(headerName + " : " + headerValue + " <br> ");
		}
		if(!ConChar.isNull(WIN_CD)) {
			WIN_CD = ConChar.upper(WIN_CD.substring(WIN_CD.lastIndexOf("/")+1, WIN_CD.lastIndexOf(".")));
		}
    	Map<String, Object> resMap = new HashMap<>();
    	resMap.put("SYS_CD", ComConstants.SYS_CD);
    	resMap.put("MSG_TXT", ex.toString());
    	resMap.put("USER_ID", userDetails.getUsername());
    	resMap.put("REQ_URI", request.getRequestURI());
    	resMap.put("MENU_CD", WIN_CD);
    	resMap.put("MSG_TXT", ex.toString());

    	//resMap.put("Result", "{Code:-1, Message:\"\\n"+e.toString()+"\"}");
        //return resMap;
    	try {
    		z01Service.createZ01SystemLog(resMap);
    	}catch(Exception sube) {
    		
    	}		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@Order(2)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex,HttpServletRequest request) {
		Map<String, Object> res = new LinkedHashMap<>(); // ("500", "Internal Server Error");
		res.put(ReturnCode.RTN_CODE,ReturnCode.ERROR);
		res.put("message","처리중 오류가 발생하였습니다.");
		StringBuffer buffer = new StringBuffer("<ul>");
		buffer.append("<li>").append(ex.getLocalizedMessage()).append("</li>");
		Arrays.stream(ex.getStackTrace()).forEach(stackTraceElement->{
			buffer.append("<li>").append(stackTraceElement).append("</li>");
		});
		buffer.append("</ul>");
		res.put("exception",buffer);
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		String headerName = "";
    	String WIN_CD = "";
    	Enumeration<String> headerNames = request.getHeaderNames();
    	String headerValue = "";
		while(headerNames.hasMoreElements()) {
			headerName = headerNames.nextElement();
			if("referer".equals(headerName)) WIN_CD = request.getHeader(headerName);
			headerValue = request.getHeader(headerName);
			//System.out.println(headerName + " : " + headerValue + " <br> ");
		}
		if(!ConChar.isNull(WIN_CD)) {
			WIN_CD = ConChar.upper(WIN_CD.substring(WIN_CD.lastIndexOf("/")+1, WIN_CD.lastIndexOf(".")));
		}

    	Map<String, Object> resMap = new HashMap<>();
    	resMap.put("SYS_CD", ComConstants.SYS_CD);
    	resMap.put("MSG_TXT", ex.toString());
    	resMap.put("USER_ID", userDetails.getUsername());
    	resMap.put("REQ_URI", request.getRequestURI());
    	resMap.put("MENU_CD", WIN_CD);
    	resMap.put("MSG_TXT", buffer.toString());

    	//resMap.put("Result", "{Code:-1, Message:\"\\n"+e.toString()+"\"}");
        //return resMap;
    	try {
    		z01Service.createZ01SystemLog(resMap);
    	}catch(Exception sube) {
    		
    	}			
		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
