package com.app.s03.cmn.utils;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.AntPathMatcher;

import com.app.s03.cmn.exception.CommonBusinessException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * 간략 : .
 * 상세 : .
 * com.app.s03.cmn.utils
 *   |_ SysUtils.java
 * </pre>
 * 
 * @Company : HSL Corp,
 * @Author  : hslee
 * @Date    : 2023. 7. 19. 오후 2:36:00
 * @Version : 1.0
 */

public class SysUtils {
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 문자열 JSON DATA --> Map<String, Object> 으로 파싱.
	 * @param jsonData
	 * @return
	 */
	public static Map<String, Object> readJsonData(String jsonData) {
		try {
			return mapper.readValue(jsonData, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
			throw new CommonBusinessException(e);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new CommonBusinessException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommonBusinessException(e);
		}
	}

	/**
	 * url 으로 부터 프로그램코드 뽑기.
	 * @param url
	 * @return
	 */
	public static String getPrograCdFromUrl_BAK(String url) {
		if (url == null) {
			return null;
		}
		AntPathMatcher pathMatcher = new AntPathMatcher();
		if (pathMatcher.match("/page/cmn/**/*", url)) {
			return null;
		}
		if (pathMatcher.match("/page/*/*", url)) {
			String[] paths = url.split("/");
			if (paths[3].length() >= 2 && StringUtils.isAlphanumeric(paths[3].substring(0, 2))) {
				return paths[3].substring(0, 2);
			}
			return null;
		}
		if (pathMatcher.match("/page/*/*/*", url)) {
			String[] paths = url.split("/");
			if (paths[4].length() >= 6 && StringUtils.isAlphanumeric(paths[4].substring(0, 6))) {
				return paths[4].substring(0, 6);
			}
		}

		if (pathMatcher.match("/api/cmn/**/*", url)) {
			return null;
		}
		if (pathMatcher.match("/api/*/common/*", url)) {
			return null;
		}
		if (pathMatcher.match("/api/*/*/*/*", url)) {
			String[] paths = url.split("/");
			if (paths[4].length() == 6 && StringUtils.isAlphanumeric(paths[4])) {
				return paths[4];
			}
			return null;
		}
		return null;
	}
	
	/**
	 * url 으로 부터 프로그램코드 뽑기.
	 * @param url
	 * @return
	 */
	public static String getPrograCdFromUrl(String url) {
		//String requestURI = "";
		if (url == null) {
			return null;
		}else {
			
		}
		AntPathMatcher pathMatcher = new AntPathMatcher();
		if (pathMatcher.match("/page/cmn/**/*", url)) {
			return null;
		}
		if (pathMatcher.match("/api/cmn/**/*", url)) {
			return null;
		}
		if (pathMatcher.match("/api/*/common/*", url)) {
			return null;
		}
		if(url.indexOf("page/")>=0){
			url = url.substring(url.lastIndexOf("/"));
			url = url.replaceAll(".do", "").replaceAll("/", "");
			return url;
		}
		
		if(url.indexOf("api/")>=0 && ConChar.stringToArrayList(url, "/").size() >=2){
			url = (ConChar.stringToArrayList(url, "/")).get(ConChar.stringToArrayList(url, "/").size()-2);
			//url = url.substring(url.lastIndexOf("/")-1);
			//url = url.replaceAll(".do", "").replaceAll("/", "");
			return url;
		}
		
		return null;
	}	

	/**
	 * underscore ('_') 가 포함되어 있는 문자열을 Camel Case ( 낙타등
	 * 표기법 - 단어의 변경시에 대문자로 시작하는 형태. 시작은 소문자) 로 변환해주는
	 * utility 메서드 ('_' 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리
	 * 함.)
	 * @param underScore
	 *        - '_' 가 포함된 변수명
	 * @return Camel 표기법 변수명
	 */
	public static String convert2CamelCase(String underScore) {

		// '_' 가 나타나지 않으면 이미 camel case 로 가정함.
		// 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
		// 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
		if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
			return underScore;
		}
		return JdbcUtils.convertUnderscoreNameToPropertyName(underScore);
	}


}

