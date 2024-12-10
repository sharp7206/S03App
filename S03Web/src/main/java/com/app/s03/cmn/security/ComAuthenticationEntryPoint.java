/**
 * 
 */
package com.app.s03.cmn.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hslee
 *
 */
@Slf4j
@Component
public class ComAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		log.error("Request Url = {}", ((HttpServletRequest)request).getRequestURI().toString());
    	log.error("Request Querystring = {}", ((HttpServletRequest)request).getQueryString());
    	log.error("Exception = {}", authException);
    	
    	response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        
        // exception 별로 메시지를 결정한다
        String catchedExceptionMessage = null;
        
    	if (InsufficientAuthenticationException.class == authException.getClass()) {
    		catchedExceptionMessage = authException.getMessage();
    	}
    	
    	// Ajax 호출인지 아닌지 체크
    	boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    	if (ajax) {
        	
        	if (StringUtils.hasLength(catchedExceptionMessage)) {
        		writer.println(HttpServletResponse.SC_UNAUTHORIZED + " : " + authException.getMessage());
        	} else {
        		writer.println(HttpServletResponse.SC_UNAUTHORIZED + " : " + catchedExceptionMessage);
        	}
        } else {
        	
        	if (StringUtils.hasLength(catchedExceptionMessage)) {
        		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        	} else {
        		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, catchedExceptionMessage);
        	}
        }
    	
	}

}
