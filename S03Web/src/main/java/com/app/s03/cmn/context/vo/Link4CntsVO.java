/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.context.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: APP.core.context.vo
 * 클래스명: LinkProcess4CntsVO
 * 설명: Context Area 에서 사용하는 연계정보 VO Class
 *
 * ==================================================================
 * 변경일자       변경자          변경내용
 * ------------------------------------------------------------------
 * 2021.12.10     HyojinKwon      1.0 최초작성
 * </pre>
 */
@Builder
@Getter
@Setter
public class Link4CntsVO implements Serializable {
    private static final long serialVersionUID = 8065490592441208911L;

    private int linkPrcsSn; // 연계처리일련번호
    private String linkSstmId; // 연계시스템ID
    private String linkPrcsMthdClssCd; // 연계처리방법구분코드
    private String linkDmndTm; // 연계요청시각
    private String linkRspnsTm; // 연계응답시각
    private String linkPrcsRsltClssCd; // 연계처리결과구분코드
    private String linkPrcsRsltMssgCn; // 연계처리결과메시지내용

}
