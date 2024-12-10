package com.app.s03.cmn.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.mapper.CommonMapper;

import lombok.extern.slf4j.Slf4j;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 인증성공시 후처리 핸들러
-===============================================================================================================
*/
@Slf4j
@Component
public class ComAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private RequestCache requestCache;

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;
	 private String defaultUrl;

	    public void setDefaultUrl(String defaultUrl) {
	        this.defaultUrl = defaultUrl;
	    }

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		String redirectUrl = "";
        if (savedRequest != null) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            redirectUrl = savedRequest.getRedirectUrl();
        }
        
        if (!StringUtils.hasLength(redirectUrl)) {
        	
        	redirectUrl = "intro.do";
        }
        
        log.debug("redirectUrl = {}", redirectUrl);
        
        // setting session timeout
        request.getSession(false).setMaxInactiveInterval(60 * 60);
        
		ComUserDetails userDetails = (ComUserDetails) authentication.getPrincipal();
		commonMapper.update("cmn.Login.updatePwdFailureClear", authentication.getName());// 로그인 성공시 실패 카운트 초기화
		Map<String, Object> loginMap = new HashMap();
		loginMap.put("syscd", ComConstants.SYS_CD);
		loginMap.put("userid", userDetails.getUsername());
		loginMap.put("userip", ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());// IP
		commonMapper.insert("cmn.Login.insertLoginhist", loginMap);// 로그인 성공시 시각 저장.

		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));//Request 가 ajax 인지 체크.
		if (ajax) {
			
			Map<String, Object> result = new HashMap<>();
			result.put("error", false);
			result.put("url", redirectUrl);
			
			MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            MediaType jsonMimeType = MediaType.APPLICATION_JSON;

            if (jsonConverter.canWrite(result.getClass(), jsonMimeType)) {
                jsonConverter.write(result, jsonMimeType, new ServletServerHttpResponse(response));
            }
            
		} else {
			super.onAuthenticationSuccess(request, response, authentication);       
		}
	}
}