/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename DongBackUtil.java
 */
package com.app.s03.cmn.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.utils
 * 클래스명: DongBackUtil
 * 설명: 동백Application의 SingIn을 통한 AccessToken 반환
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.24     hslee      1.0 최초작성
 * </pre>
 */
public class DongBackUtil {
	
	public static Map<String, Object> getSingInJsonStr(){
		String url = ComProperties.getProperty("dongback.url")+"auth/signIn.do";
		String accessKey = ComProperties.getProperty("dongback.accessKey");
		String partnerNo = ComProperties.getProperty("dongback.partnerNo");
        String jsonKey = "{\"accessKey\" : \""+accessKey+"\"}";
        String decryptVal = "";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // Replace this JSON string with the actual payload you want to send
        // Create a list to hold the form parameters
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("trCode", "10"));
        formParams.add(new BasicNameValuePair("encrypt", "A"));
        String encText  = encryptAES(accessKey, jsonKey, false);

        
        formParams.add(new BasicNameValuePair("data", encText));
        formParams.add(new BasicNameValuePair("partnerNo", partnerNo));

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Create an instance of HttpPost with the URL
        HttpPost httpPost = new HttpPost(url);

        try {
            // Set the request entity (payload) as a StringEntity
        	UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams);
            httpPost.setEntity(formEntity);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            HttpResponse response = httpClient.execute(httpPost);

         // Get the response entity
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // Parse the JSON response
                String responseBody = EntityUtils.toString(responseEntity);
                JSONObject jsonObject = new JSONObject(responseBody);

                // Extract and print specific values from the JSON response
                String dataVal = jsonObject.getString("data");
                decryptVal = decryptAES(accessKey, dataVal);
             // Create an instance of ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
             // Convert the JSON string to a Map
                resultMap = objectMapper.readValue(decryptVal, Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
	}
	
	/**동백API 호출
	 * @param String uri :evt/evtBnList.do
	 * @param reqMap :"{\"status\" : \"R\", \"userId\" : \"A000246268\"}"
	 * @param trCode : 54
	 * @param encryptType : A(encrypt), P(plan)
	 * @return Map
	 * ex
		String uri = "mbr/mbrMgt.do";
		Map<String, String> reqMap = new HashMap();
		reqMap.put("status", "R");
		reqMap.put("userId", "A000246268");
		String trCode = "54";
		String encryptType = "A";
		String retStr = DongBackUtil.getApiRequest(uri, reqMap, trCode, encryptType);
	 * */
	public static Map<String, ?> getApiRequest(String uri, Map<String, String> reqMap, String trCode, String encryptType) {
		String retStr = "";
		String authToken = "";
		/* 해당 authToken이 request Header에 넎지 않아도 무관하여 주석처리
		Map<String, Object> tokenMap = DongBackUtil.getSingInJsonStr();
		authToken = (String)tokenMap.get("authToken");
		*/
		String url = ComProperties.getProperty("dongback.url")+uri;
		String securityKey = ComProperties.getProperty("dongback.securityKey");
		String partnerNo = ComProperties.getProperty("dongback.partnerNo");
		
        //String hashCode = encryptAES(securityKey, "540000000361R", true);
        
        // Replace this JSON string with the actual payload you want to send
        String jsonKey = "";
        try {
			jsonKey = Utility.toJSONdefault(reqMap);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			jsonKey="";
		}
        // Create a list to hold the form parameters
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("trCode", trCode));
        formParams.add(new BasicNameValuePair("encrypt", encryptType));
        formParams.add(new BasicNameValuePair("partnerNo", partnerNo));
        if("A".equals(encryptType)) {
            formParams.add(new BasicNameValuePair("data", encryptAES(securityKey, jsonKey, false)));
        }else {
            formParams.add(new BasicNameValuePair("data", jsonKey));
        }

        

        // Create an instance of HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Create an instance of HttpPost with the URL
        HttpPost httpPost = new HttpPost(url);

        try {
            // Set the request entity (payload) as a StringEntity
        	UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams);
            httpPost.setEntity(formEntity);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setHeader("Authorization", authToken);
            // Execute the POST request
            HttpResponse response = httpClient.execute(httpPost);

         // Get the response entity
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // Parse the JSON response
                String responseBody = EntityUtils.toString(responseEntity);
                JSONObject jsonObject = new JSONObject(responseBody);
                //System.out.println("jsonObject:>>>>>> " + jsonObject);
                // Extract and print specific values from the JSON response
                retStr = jsonObject.getString("data");
                retStr = decryptAES(securityKey, retStr);
                System.out.println("retStr: " + retStr);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }		
		return Utility.readJsonData(retStr);
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
