package com.app.s03.cmn.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import com.app.s03.cmn.mapper.CommonMapper;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 인증실패시 처리 핸들러
-===============================================================================================================
*/
@Component
public class ComAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;
	
	@Autowired
	private MessageSource messageSource;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		/*
		 BadCredentialException : 비밀번호가 일치하지 않을 때 던지는 예외
		 InternalAuthenticationServiceException : 존재하지 않는 아이디일 때 던지는 예외
		 AuthenticationCredentialNotFoundException:인증 요구가 거부됐을 때 던지는 예외
		 LockedException:인증 거부 - 잠긴 계정
		 DisabledException:인증 거부 - 계정 비활성화
		 AccountExpiredException:인증 거부 - 계정 유효기간 만료
		 CredentialExpiredException:인증 거부 - 비밀번호 유효기간 만료
		*/
		String errormsg ="";
		if(exception instanceof BadCredentialsException) {
			commonMapper.update("cmn.Login.updatePwdFailureCnt", request.getParameter("userid"));
            errormsg = messageSource.getMessage("user.BadCredentials", null, LocaleContextHolder.getLocale());
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errormsg = messageSource.getMessage("user.AccessDeniedException", null, LocaleContextHolder.getLocale());
        } else if(exception instanceof DisabledException) {
            errormsg = messageSource.getMessage("user.Disaled", null, LocaleContextHolder.getLocale());
        } else if(exception instanceof CredentialsExpiredException) {
            errormsg = messageSource.getMessage("user.CredentialsExpired", null, LocaleContextHolder.getLocale());
        } else if (exception instanceof LockedException) {
        	errormsg = messageSource.getMessage("user.Locked", null, LocaleContextHolder.getLocale());
        } else if (exception instanceof SessionAuthenticationException) {
        	errormsg = messageSource.getMessage("user.SessionAuthentication", null, LocaleContextHolder.getLocale());
		} else {
			errormsg = messageSource.getMessage("user.LoginError", null, LocaleContextHolder.getLocale());
		}
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));//Request 가 ajax 인지 체크.
		if(ajax) {
			response.setHeader("Content-Type", "application/json");
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			String data = StringUtils.join(new String[] {"{\"error\" : \"true\", \"message\" : \"", errormsg,"\"}"});
			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/login.do");
			request.setAttribute("message", errormsg);
			rd.forward(request, response);
		}
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
	}

}
