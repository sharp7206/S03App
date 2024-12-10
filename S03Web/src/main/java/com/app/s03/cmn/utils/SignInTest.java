/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename SignInTest.java
 */
package com.app.s03.cmn.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.utils
 * 클래스명: SignInTest
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.24     hslee      1.0 최초작성
 * </pre>
 */
@Slf4j
public class SignInTest {

	/**
	 * 메소드에 대한 설명 작성해주세요.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	       // Replace the URL with the actual endpoint you want to send the POST request to
		Map<String, Object> tokenMap = DongBackUtil.getSingInJsonStr();
		System.out.println("TOKEN==="+tokenMap );
		System.out.println("Authorization==="+tokenMap.get("authToken") );
		String signIn = "auth/signIn.do";
		String url = "https://dong100apidev.busanbank.co.kr/api/auth/signIn.do";
		/*
        String url = "https://dong100apidev.busanbank.co.kr/api/auth/signIn.do";
        String originText  = "9F9av7578g97jL80";
        String jsonKey = "{\"accessKey\" : \"9F9av7578g97jL80\"}";
        // Replace this JSON string with the actual payload you want to send
        // Create a list to hold the form parameters
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("trCode", "10"));
        formParams.add(new BasicNameValuePair("encrypt", "A"));
        String encText  = encryptAES(originText, jsonKey, false);
        System.out.println("encText (encodeBase64) : " + encText);

        
        formParams.add(new BasicNameValuePair("data", encText));
        formParams.add(new BasicNameValuePair("partnerNo", "0000000361"));

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Create an instance of HttpPost with the URL
        HttpPost httpPost = new HttpPost(url);

        try {
            // Set the request entity (payload) as a StringEntity
        	UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams);
            httpPost.setEntity(formEntity);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            log.info("formEntity:"+formEntity.toString());
            // Execute the POST request
            HttpResponse response = httpClient.execute(httpPost);

         // Get the response entity
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // Parse the JSON response
                String responseBody = EntityUtils.toString(responseEntity);
                JSONObject jsonObject = new JSONObject(responseBody);

                // Extract and print specific values from the JSON response
                String dataVal = jsonObject.getString("data");
                String decryptVal = decryptAES(originText, dataVal);
                System.out.println("decryptVal: " + decryptVal);

            }
            // Print the response status code
            log.info("Response Header: {}", response.getAllHeaders());
            log.info("Response Code: {}", response.getStatusLine().getStatusCode());

            // Print the response content
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
	}
	
    public static String encryptAES(String keyString, String plainText, boolean bUrlSafe) {
        String cipherText = "";
        if ((keyString == null) || keyString.length() == 0 || (plainText == null) || plainText.length() == 0) {
            return cipherText;
        }
       

        // 키의 길이는 16, 24, 32 만 지원
        if ((keyString.length() != 16) && (keyString.length() != 24) && (keyString.length() != 32)) {
            return cipherText;
        }
       
        try {
            byte[] keyBytes = keyString.getBytes("UTF-8");
            byte[] plainTextBytes = plainText.getBytes("UTF-8");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int bsize = cipher.getBlockSize();
            IvParameterSpec ivspec = new IvParameterSpec(Arrays.copyOfRange(keyBytes, 0, bsize));
           
            SecretKeySpec secureKey = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, ivspec);
            byte[] encrypted = cipher.doFinal(plainTextBytes);

            if (bUrlSafe) {
                cipherText = Base64.encodeBase64URLSafeString(encrypted);
            } else {
                cipherText = new String(Base64.encodeBase64(encrypted), "UTF-8");
            }
           
        } catch (Exception e) {
            cipherText = "";
            e.printStackTrace();
        }

        return cipherText;
    }
   
   
    public static String decryptAES(String keyString, String cipherText) {
        String plainText = "";
        if ((keyString == null) || keyString.length() == 0 || (cipherText == null) || cipherText.length() == 0) {
            return plainText;
        }
       
        if ((keyString.length() != 16) && (keyString.length() != 24) && (keyString.length() != 32)) {
            return plainText;
        }

        try {
            byte[] keyBytes = keyString.getBytes("UTF-8");
            byte[] cipherTextBytes = Base64.decodeBase64(cipherText.getBytes("UTF-8"));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int bsize = cipher.getBlockSize();
            IvParameterSpec ivspec = new IvParameterSpec(Arrays.copyOfRange(keyBytes, 0, bsize));
           
            SecretKeySpec secureKey = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secureKey, ivspec);
            byte[] decrypted = cipher.doFinal(cipherTextBytes);

            plainText = new String(decrypted, "UTF-8");
           
        } catch (Exception e) {
            plainText = "";
            e.printStackTrace();
        }

        return plainText;
    }

}
