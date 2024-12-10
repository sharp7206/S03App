package com.app.s03.cmn.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 접근거부처리핸들러
-===============================================================================================================
*/
@Slf4j
public class ComAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;

	public ComAccessDeniedHandler() {
	}

	public ComAccessDeniedHandler(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

		log.debug("accessDeniedException = {}", accessDeniedException);
		
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));//Request 가 ajax 인지 체크.

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		String message = accessDeniedException.getMessage();
		
		if (ajax) {//ajax Request 인 경우.
			
			Map<String, Object> result = new HashMap<>();
			result.put("error", true);
			result.put("message", message);
			
			MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            MediaType jsonMimeType = MediaType.APPLICATION_JSON;

            if (jsonConverter.canWrite(result.getClass(), jsonMimeType)) {
                jsonConverter.write(result, jsonMimeType, new ServletServerHttpResponse(response));
            }
            
		} else {// 일반 Request 인경우.
			RequestDispatcher rd = request.getRequestDispatcher(errorPage);
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}

}
