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
 * 클래스명: Privacy4CntsVO
 * 설명: Context Area 에서 사용하는 개인정보 접근 VO Class
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
public class Privacy4CntsVO implements Serializable {
    private static final long serialVersionUID = 2777361578806679160L;

    private String prvcClssCd; // 개인정보구분코드
    private String prvcCn; // 개인정보내용
    private String prvcApprRsnCd; // 개인정보접근사유코드
    private String prvcApprRsnCn; // 개인정보접근사유내용

}
