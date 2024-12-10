/**
 * Copyright HSLEE. All Rights Reserved.
 */
package com.app.s03.cmn.security;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.security
 * 클래스명: SecurityConstants
 * 설명: Security 관련 상수 Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.04.15     HSLEE      1.0 최초작성
 * </pre>
 */
public class SecurityConstants {
    // 패스워드 5회 입력 시 계정 Lock 처리
    public static final int LOGIN_PW_INCORRECT_MAX_CNT = 5;
    // 패스워드 만료일 90일
    public static final int PW_EXPIRED_DAYS = 90;

    // Token Claims 항목
    public static final String TOKEN_CLIENT_IP = "ip";

    // Access Token 유효시간 3분
    public static final long ACCESS_TOKEN_VALID_TIME = 3 * 60 * 1000L;
    // Refresh Token 유효시간 15시간
    public static final long REFRESH_TOKEN_VALID_TIME = 15 * 60 * 60 * 1000L;

    // 마지막 호출 후 4시간이 지나면 강제 Logout 처리
    public static final long FORCED_LOGOUT_TIME = 4 * 60 * 60 * 1000L;

    // REDIS 사용자 정보 TTL 값 2일
    public static final long REDIS_USER_INFO_TTL_SEC = 2 * 24 * 60 * 60 * 1L;

    // REDIS SSO 정보 TTL 값 3분
    public static final long REDIS_SSO_INFO_TTL_SEC = 3 * 60 * 1L;
}
