/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename GoogleOTP.java
 */
package com.app.s03.cmn.utils;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;

import com.warrenstrange.googleauth.GoogleAuthenticator;
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.utils
 * 클래스명: GoogleOTP 인증 Util
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.22     hslee      1.0 최초작성
 * </pre>
 */
public class GoogleOTPUtil {
	public static Map<String, Object> generateOTPKeyQr(String account, String host) { 
		HashMap<String, String> map = new HashMap<String, String>(); 
		byte[] buffer = new byte[5 + 5 * 5]; //버퍼에 할당 
		new Random().nextBytes(buffer); //버퍼를 난수로 셋팅 
		Base32 codec = new Base32(); 
		byte[] otpSecrKey = Arrays.copyOf(buffer, 10); 
		byte[] bEncodedKey = codec.encode(otpSecrKey); 
		String encodedKey = new String(bEncodedKey); //생성된 Key (해당키로 OTP확인)
		Map<String, Object> retMap = new HashMap(); 
		String url = getQRBarcodeURL(account, host, encodedKey);
		retMap.put("otpSecrKey", encodedKey);
		retMap.put("QRUrl", url);
		return retMap; 
	}
	
	public static boolean checkCode(String userCode, String otpkey) {
		/*
		long otpnum = Integer.parseInt(userCode); // Google OTP 앱에 표시되는 6자리 숫자
		long wave = new Date().getTime() / 30000; // Google OTP의 주기는 30초
		boolean result = false;
		try {
			Base32 codec = new Base32();
			byte[] decodedKey = codec.decode(otpkey);
			int window = 3;
			for (int i = -window; i <= window; ++i) {
				long hash = verify_code(decodedKey, wave + i);
				if (hash == otpnum) result = true;
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		*/
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		return googleAuthenticator.authorize(userCode, Integer.parseInt(otpkey));
	}
	
	
	private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}

		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);

		int offset = hash[20 - 1] & 0xF;

		// We're using a long because Java hasn't got unsigned int.
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			// We are dealing with signed bytes:
			// we just keep the first byte.
			truncatedHash |= (hash[offset + i] & 0xFF);
		}

		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;

		return (int) truncatedHash;
	}
	
	// 계정명(유저ID), 발급자(회사명), 개인키  받아서 구글OTP 인증용 링크를 생성하는 메소드
	public static String getQRBarcodeURL(String account, String host, String secretKey) {
		// QR코드 주소 생성
		String format2 = "http://chart.apis.google.com/chart?cht=qr&chs=200x200&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s&chld=H|0";
		return String.format(format2, account, host, secretKey);
	}
}
