/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: APP.core.cmmn.vo
 * 클래스명: CommonMessageVO
 * 설명: 공통메시지 VO Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.10.22     ADMIN      1.0 최초작성
 * </pre>
 */
@Getter
@Setter
public class CommonMessageVO implements Serializable {
    private static final long serialVersionUID = 5378089867517600749L;

    // 메시지ID
    private String mssgId;

    // 국가코드
    private String ntnCd;

    // 메시지내용
    private String mssgCn;

    // 메시지구분코드
    private String mssgClssCd;

}
