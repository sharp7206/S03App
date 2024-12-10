package com.app.s03.cmn.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.properties.ReturnCode;
import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.security.vo.UserDetailVO;
import com.app.s03.cmn.service.UserAuthorityService;
import com.app.s03.cmn.utils.CommonUtils;
import com.app.s03.cmn.utils.ConChar;
import com.app.s03.cmn.utils.GoogleOTPUtil;
import com.app.s03.cmn.utils.Utility;
import lombok.extern.slf4j.Slf4j;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 로그인 관련
-===============================================================================================================
*/
@Slf4j
@Controller

@PropertySource("classpath:application.properties")
public class LoginController {
	@Value("${google.otpUseYn}") private String otpUseYn;
	
	@Autowired
	private UserAuthorityService uaservice;
	
	@RequestMapping(value = "/login.do")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		log.info("otpUseYn>>>>>>>>"+otpUseYn);
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		try {
			request.getSession().setAttribute("prevPage", savedRequest.getRedirectUrl());
		} catch(NullPointerException e) {
			request.getSession().setAttribute("prevPage", "/");
		}
		if("Y".equals(otpUseYn)) {
			return "cmn/login/loginOTP";
		}else {
			return "cmn/login/login";
		}
	}
	
	@RequestMapping(value = "/ssoLogin.do")
	public String ssoLogin(@RequestParam String userid, HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		try {
			request.getSession().setAttribute("prevPage", savedRequest.getRedirectUrl());
		} catch(NullPointerException e) {
			request.getSession().setAttribute("prevPage", "/");
		}
		return "cmn/login/login";
	}
	
	@RequestMapping(value = {"", "/index.do"})
	public String login(@AuthenticationPrincipal ComUserDetails userDetails) {
		if (userDetails == null) {
			if("Y".equals(otpUseYn)) {
				return "redirect: loginOTP.do";
			}else {
				return "redirect: login.do";
			}
		} else {
			return "redirect: intro.do";
		}
	}
	
	/***OTP QR Regi*/
	@RequestMapping(value = "/otpQRLogin.do")
	public String otpQRLogin(HttpServletRequest request, ModelMap model) throws Exception {
        // 응답 설정
        Map<String, Object> jsonData = CommonUtils.readJsonData(java.net.URLDecoder.decode(request.getParameter("_jsonparam")));
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");

		Map<String, Object> otpQRMap = GoogleOTPUtil.generateOTPKeyQr((String)param.get("userid"), "APPOtpuser");
		String QRUrl = (String)otpQRMap.get("QRUrl");
        model.addAttribute(ReturnCode.RTN_CODE,ReturnCode.OK);
        model.addAttribute("jsonData", jsonData);
        model.addAttribute("param", param);
	    model.addAttribute("QRUrl", QRUrl);
	 
	    return "cmn/login/otpQRRegi";
	}

	/***OTP*/
	@RequestMapping(value = "/otpLogin.do")
	public String otpLogin(HttpServletRequest request, ModelMap model) throws Exception {
        // 응답 설정
        Map<String, Object> jsonData = CommonUtils.readJsonData(java.net.URLDecoder.decode(request.getParameter("_jsonparam")));
		Map<String, Object> param = (Map<String, Object>) jsonData.get("param");
		Map<String, Object> otpMap = uaservice.searchGoogleOptInfoByuserid((String)param.get("userid"));
		String QRUrl = "";
		String otpSecrKey = (String)otpMap.get("otpSecrKey");
		if(ConChar.isNull(otpSecrKey)) {
			Map<String, Object> otpQRMap = GoogleOTPUtil.generateOTPKeyQr((String)param.get("userid"), "APPOtpuser");
			QRUrl = (String)otpQRMap.get("QRUrl");
			otpSecrKey = (String)otpQRMap.get("otpSecrKey");
			otpMap.putAll(otpQRMap);
		}
		model.addAttribute("QRUrl", QRUrl);
		model.addAttribute("otpSecrKey", otpSecrKey);
		model.addAttribute("userid", (String)param.get("userid"));
        model.addAttribute("param", param);
	 
	    return "cmn/login/otpLogin";
	}


}
