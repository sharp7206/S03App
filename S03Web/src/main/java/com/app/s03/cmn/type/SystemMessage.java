/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.type;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.context.ContextHolder;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.type
 * 클래스명: SystemMessage
 * 설명: System Message 정의
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.03     ADMIN      1.0 최초작성
 * </pre>
 */
public enum SystemMessage {
    // 에러
    SYS0000 ("시스템 오류", "System Error"),
    // 인증관련 에러
    SYS0001("인증되지 않았습니다.", "UnAuthorized"), SYS0002("권한이 없습니다.", "Forbidden"),
    SYS0003("사용자 인증에 실패했습니다.", "사용자 인증에 실패했습니다."), SYS0004("인증토큰 정보가 없습니다.", "인증토큰 정보가 없습니다."),
    SYS0005("토큰정보가 유효하지 않습니다.", "토큰정보가 유효하지 않습니다."), SYS0006("토큰 유효시간이 초과되었습니다.", "토큰 유효시간이 초과되었습니다."),
    SYS0007("사용자 인증정보가 없습니다.", "사용자 인증정보가 없습니다."), SYS0008("시간초과로 강제 로그아웃 처리되었습니다.", "시간초과로 강제 로그아웃 처리되었습니다."),
    // 표준전문관련 에러
    SYS0020("표준전문이 아닙니다.", "표준전문이 아닙니다."), SYS0021("응답 표준헤더 변환에 실패했습니다.", "응답 표준헤더 변환에 실패했습니다."),
    SYS0022("표준헤더 필수항목을 확인하십시오.", "표준헤더 필수항목을 확인하십시오."),
    SYS0023("표준헤더의 시스템환경구분값이 일치하지 않습니다.", "표준헤더의 시스템환경구분값이 일치하지 않습니다."),
    // 연계관련 에러
    SYS0030("인터페이스 아이디가 빈값이 될 수 없습니다.", "인터페이스 아이디가 빈값이 될 수 없습니다."),
    SYS0031("인터페이스 포트 번호가 빈값이 될 수 없습니다.", "인터페이스 포트 번호가 빈값이 될 수 없습니다."),
    SYS0032 ("인터페이스 요청 시 오류가 발생했습니다.", "인터페이스 요청 시 오류가 발생했습니다."),
    // JobPass관련 에러
    SYS0040("[JobPass] Job 조회에 실패했습니다.", "[JobPass] Job 조회에 실패했습니다."),
    SYS0041("[JobPass] Task 조회에 실패했습니다.", "[JobPass] Task 조회에 실패했습니다."),
    SYS0042("[JobPass] Job 매개변수 설정에 실패했습니다.", "[JobPass] Job 매개변수 설정에 실패했습니다."),
    SYS0043("[JobPass] Job 실행에 실패했습니다.", "[JobPass] Job 실행에 실패했습니다."),
    SYS0044("[JobPass] Job 상태 조회에 실패했습니다.", "[JobPass] Job 상태 조회에 실패했습니다."),
    // Redis
    SYS0050 ("[Redis]값 설정에 실패했습니다.", "[Redis] Fail to set value"),

    // 그 외 에러
    SYS0100("트랜잭션 시간이 초과되었습니다.", "Transaction timed out."), SYS0101("JSON 데이터 변환에 실패했습니다.", "JSON 데이터 변환에 실패했습니다."),
    SYS0102("설정값 확인이 필요합니다.", "설정값 확인이 필요합니다."),
    // 성공
    SYS1000("정상 처리되었습니다.", "정상 처리되었습니다."),
    // 인증관련
    SYS1001("토큰이 재발급 되었습니다.", "토큰이 재발급 되었습니다."), SYS1002("SSO 인증 요청이 유효하지 않습니다.", "Invalid SSO request"),
    SYS1003("SSO로그인하세요.", "Please, login through SSO"), SYS1004("로그인하세요.", "You've been logged out. Please login"),
    SYS1005("로그인하세요.(비상로그인)", "Please login"),
    // 암복호화관련
    SYS2001("Petra 세션 획득 실패", "Fail to acquire a petra session"), SYS2002("파일 저장 실패", "Fail to store a file securely"),
    SYS2003("파일 복원 실패", "Fail to restore a file"),;

    private String kor;
    private String eng;

    private SystemMessage(String kor, String eng) {
        this.kor = kor;
        this.eng = eng;
    }

    public String getMessage() {
        String langCd = ContextHolder.get().getUserLnggClssVal();
        if (StringUtils.isBlank(langCd))
            langCd = ComConstants.DEFAULT_LANG_CD;
        return getMessage(new Locale(langCd));
    }

    public String getMessage(Locale locale) {
        return locale == Locale.ENGLISH ? eng : kor;
    }

}
