/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: APP.core.vo
 * 클래스명: HeaderVO
 * 설명: Header 정보 Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.10.18     ADMIN      1.0 최초작성
 * </pre>
 */
@Getter
@Setter
public class HeaderVO implements Serializable {
    private static final long serialVersionUID = 4961352308585743557L;

    private String dlngId; // 거래ID
    private String srvcUriAddr; // 서비스URI주소
    private int prgrsSn; // 진행일련번호
    private int prcsSn; // 처리일련번호
    private String prcsCrtrYmd; // 처리기준일자
    private String mngrSrvcYn; // 관리자서비스여부
    private String rpppOtptYn; // 보고서출력여부
    private String dmndRspnsClssVal; // 요청응답구분값
    private String fltxChstNm; // 전문문자셋명
    private String fltxChstVal; // 전문문자셋값
    private String callSstmId; // 호출시스템ID
    private String prcsSstmId; // 처리시스템ID
    private String scrnId; // 화면ID
    private String sstmEnvrClssVal; // 시스템환경구분값
    private String userId; // 사용자ID
    private String userIp; // 사용자IP
    private String certApprToknVal; // 인증접근토큰값
    private String certExpryToknVal; // 인증만료토큰값
    private String userLnggClssVal; // 사용자언어구분값
    private String evlmInqPrcsMethClssVal; // 대량조회처리방법구분값
    private int pageSz; // 페이지크기
    private int pageNo; // 페이지번호
    private String nxtRsltExstYn; // 차기결과존재여부
    private int prsInqNocs; // 현재조회건수
    private int allInqNocs; // 전체조회건수
    private String linkPrcsYn; // 연계처리여부
    private String linkId; // 연계아이디
    private String linkProcsMethClssVal; // 연계처리방법구분값
    private String dlngPrcsRsltClssVal; // 거래처리결과구분값
    private String prcsBgngDt; // 처리시작일시
    private String prcsEndDt; // 처리종료일시
    private String rspnsMssgCd; // 응답메시지코드
    private String rspnsMssgCn; // 응답메시지내용
    private String spplMssgCn; // 추가메시지내용

}
