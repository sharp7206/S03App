/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename ComProperties.java
 */
package com.app.s03.cmn.utils;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;

import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.utils
 * 클래스명: ComProperties
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.24     hslee      1.0 최초작성
 * </pre>
 */
@Slf4j
public class ComProperties {
    private final static String PROPERTIES_FILE = "application.properties";
    private final static String DEFAULT_DELIMITER = ",";
    /**
     * 설정 파일 읽어서 Properties로 반환
     * 
     * @return Properties
     */
    private static Properties readProperties() {
        Properties props = new Properties();
        try {
            Reader reader = Resources.getResourceAsReader(PROPERTIES_FILE);
            if (reader != null)
                props.load(reader);
        } catch (IOException e) {
            log.error("설정 property 읽기 실패!", e);
        }
        return props;
    }

    /**
     * 설정된 값 반환
     * 
     * @param key
     * @return String
     */
    public static String getProperty(String key) {
        return readProperties().getProperty(key);
    }

    /**
     * 설정된 값이 복수인 경우 기본 구분자(,) 기준으로 List로 반환
     * 
     * @param key
     * @return List<String>
     */
    public static List<String> getPropertyValues(String key) {
        return getPropertyValues(key, DEFAULT_DELIMITER);
    }

    /**
     * 설정된 값이 복수인 경우 구분자 기준으로 List로 반환
     * 
     * @param key
     * @param delimiter
     * @return List<String>
     */
    public static List<String> getPropertyValues(String key, String delimiter) {
        String value = readProperties().getProperty(key);
        return StringUtils.isBlank(value) ? null : Arrays.asList(value.split(delimiter));
    }

}

