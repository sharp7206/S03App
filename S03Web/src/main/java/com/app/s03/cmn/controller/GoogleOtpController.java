/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename GoogleOtpController.java
 */
package com.app.s03.cmn.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrenstrange.googleauth.GoogleAuthenticator;

import com.app.s03.cmn.properties.ReturnCode;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.controller
 * 클래스명: GoogleOtpController
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.19     hslee      1.0 최초작성
 * </pre>
 */
@RestController
@RequestMapping("/api/otp/")
public class GoogleOtpController {
	/**
	 * 시스템코드목록
	 * @param request
	 * @return
	 */
	@PostMapping("getOtpCode.do")
	public Map<String, ?> geSyscdList() {
		Map<String, String> param = new HashMap<>();
		param.put("activeyn", "Y");
		Map<String, Object> res = new LinkedHashMap<>();
		String otpSecrKey = "FCJ2I4M76ZOGE5NG52LK236HKRRCK6P4";

	    // GoogleAuthenticator 인스턴스 생성

	    GoogleAuthenticator gAuth = new GoogleAuthenticator();
	    // 현재 시간에 해당하는 OTP 생성
	    int totpPassword = gAuth.getTotpPassword(otpSecrKey);
	    res.put("totpPassword", totpPassword);
		res.put(ReturnCode.RTN_CODE,ReturnCode.OK);
		return res;
	}

}
