/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename MfaApplication.java
 */
package com.app.s03.cmn;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn
 * 클래스명: MfaApplication
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.22     hslee      1.0 최초작성
 * </pre>
 */
@Slf4j
public class MfaApplication {

	   
	    public static void main(String[] args) {
	        //SpringApplication.run(MfaApplication.class, args);

	        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
	        GoogleAuthenticatorKey googleAuthenticatorKey = googleAuthenticator.createCredentials();

	        // 실제론 생성한 key를 DB에 저장해놔야 나중에 OTP를 검증할 수 있음
	        String key = googleAuthenticatorKey.getKey();
	        log.info("key : " + key);

	        String QRUrl = GoogleAuthenticatorQRGenerator.getOtpAuthURL("adduci", "userId", googleAuthenticatorKey);
	        log.info("QR URL : " + QRUrl);
	    }

}
