/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.context.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.app.s03.cmn.constant.ComConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: APP.core.context.vo
 * 클래스명: APPContentsVO
 * 설명: Context Area 에서 사용하는 VO Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.10.19     ADMIN      1.0 최초작성
 * </pre>
 */
@Getter
@Setter
public class ComContentsVO implements Serializable {
    private static final long serialVersionUID = -5697604252366595940L;

    // 사용자정보
    private String userId; // 사용자ID
    private String empNo; // 사원번호
    private String userNm; // 사용자명
    private String userJbgdNm; // 사용자직급명
    private String userDeptCd; // 사용자부서코드
    private String userDeptNm; // 사용자부서명
    private String userLnggClssVal; // 사용자언어구분코드
    private String indivLgnYn; // 개별로그인여부 (비상로그인시 "Y", 이외 "N")

    // 고객정보
    private String clcmNo; // 고객사번호
    private String clcmNm; // 고객사명
    private String clcmClssCd; // 고객사구분코드

    // 인증토큰
    private String certApprToknVal; // 인증접근토큰값
    private String certExpryToknVal; // 인증만료토큰값

    // 처리시각정보
    private String prcsBgngDt; // 처리시작일시
    private String prcsEndDt; // 처리종료일시
    private long prcsHr; // 처리시간

    // 영업일정보
    private String sstmYmd; // 시스템일자
    private String prcsCrtrYmd; // 처리기준일자
    private String taskYmd; // 업무일자
    private String hldyClssCd; // 휴일구분코드

    // 처리시스템정보
    private String prcsSstmId; // 처리시스템ID
    private String prcsSstmIpAddr; // 처리시스템IP주소
    private String srvrNm; // 서버명
    private String prcsInsnId; // 처리인스턴스ID

    // 호출시스템정보
    private String callSstmId; // 호출시스템ID
    private String callSstmIpAddr; // 호출시스템IP주소
    private String scrnId; // 화면ID
    private String scrnNm; // 화면명

    // 서비스정보
    private String srvcUriAddr; // 서비스URI주소
    private String mngrSrvcYn; // 관리자서비스여부
    private String rpppOtptYn; // 보고서출력여부
    private String srvcTypeClssVal; // 서비스유형구분값
    private String srvcNm; // 서비스명

    // 거래식별정보
    private String dlngId; // 거래ID
    private int prgrsSn; // 진행일련번호

    // 거래제어정보
    private String srvcLglvClssVal; // 서비스로그레벨구분값
    private long srvcExpryHr; // 서비스만료시간

    // 연계처리정보
    private int linkPrcsNocs; // 연계처리건수
    private List<Link4CntsVO> linkList; // 연계정보리스트

    // 전문처리정보
    private String fltxChstNm; // 전문문자셋명
    private String sstmEnvrClssVal; // 시스템환경구분값
    private String dlngDmndParaCn; // 거래요청파라미터내용

    // 처리결과정보
    private String dlngPrcsRsltClssVal; // 거래처리결과구분값
    private String rspnsMssgCd; // 응답메시지코드
    private String rspnsMssgCn; // 응답메시지내용
    private String spplMssgCn; // 추가메시지내용

    // 오류상세정보
    private int ocrnErrTrceNocs; // 발생오류추적건수
    private List<StackTrace4CntsVO> errorList; // 오류리스트

    // 개인정보취급정보
    private int apprPrvcNocs; // 접근개인정보건수
    private List<Privacy4CntsVO> prvcList; // 개인정보리스트

    // 대량데이터처리정보
    private String evlmInqPrcsMethClssVal; // 대량조회처리방법구분값
    private int pageSz; // 페이지크기
    private int pageNo; // 페이지번호
    private String nxtRsltExstYn; // 차기결과존재여부
    private int prsInqNocs; // 현재조회건수
    private int allInqNocs; // 전체조회건수

    @Override
    public String toString() {
        String rtrn;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            rtrn = ComConstants.SYS_ENV_LOCAL.equals(sstmEnvrClssVal)
                    ? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
                    : mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            rtrn = ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
        return rtrn;
    }

}
