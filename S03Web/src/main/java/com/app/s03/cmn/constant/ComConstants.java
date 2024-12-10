/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.constant;

/**
 * <pre>
 * 패키지명: APP.core.constant
 * 클래스명: APPConstants
 * 설명: APP 공통 상수 Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.10     ADMIN      1.0 최초작성
 * </pre>
 */
public class ComConstants {
	// Empty String
    public static final String SYS_CD = "s03";
    // Empty String
    public static final String EMPTY_STRING = "";
    // Comma String
    public static final String COMMA_STRING = ",";
    // Hyphen String
    public static final String HYPHEN_STRING = "-";
    // Underscore String
    public static final String UNDERSCORE_STRING = "_";
    // Double Quotation Mark String
    public static final String DOUBLE_QUOTATION_STRING = "\"";
    // Single Quotation Mark String
    public static final String SINGLE_QUOTATION_STRING = "'";
    // Forward Slash String
    public static final String FORWARD_SLASH_STRING = "/";
    // Backslash String
    public static final String BACKSLASH_STRING = "\\";
    // 기본언어
    public static final String DEFAULT_LANG_CD = "KO";
    // 기본문자인코딩
    public static final String DEFAULT_CHARACTER_SET = "UTF-8";
    // 시스템환경구분코드
    public static final String SYS_ENV_LOCAL = "L";
    public static final String SYS_ENV_DEV = "D";
    public static final String SYS_ENV_PRDT = "P";
    public static final String SYS_ENV_KEY_IN = "K";
    // 'Y', 'N' 구분값
    public static final String Y = "Y";
    public static final String N = "N";
    // URI
    public static final String URI_PREFIX = "/api";
    public static final String CW_URI_ROOT = URI_PREFIX + "/cw";
    public static final String SF_URI_ROOT = URI_PREFIX + "/sf";
    public static final String AW_URI_ROOT = URI_PREFIX + "/aw";
    // 기본페이지크기
    public static final int DEFAULT_PAGE_SIZE = 15;

    // AOP 호출순서
    public static final int AOP_ORDER_CW = 1;
    public static final int AOP_ORDER_AW = 2;
    public static final int AOP_ORDER_SF = 3;

    // 표준헤더에서 요청응답구분코드의 응답코드로 사용
    public static enum HeaderReqResType {
        RESPONSE ("R"),
        REQUEST ("Q");

        private String code;

        private HeaderReqResType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    // 대량데이터 조회 구분
    public static enum MassDataType {
        SCROLLING ("1"),
        PAGING ("2");

        private String code;

        private MassDataType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}
