/**
 * Copyright HSLEE. All Rights Reserved.
 */
package com.app.s03.cmn.security.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.security.vo
 * 클래스명: User4LoginVO
 * 설명: Login 처리를 위한 사용자 VO Class
 *
 * ==================================================================
 * 변경일자       변경자          변경내용
 * ------------------------------------------------------------------
 * 2024.04.15     HSLEE      1.0 최초작성
 * </pre>
 */
@Getter
@Setter
public class User4LoginVO implements Serializable {
    private static final long serialVersionUID = -3459401854615617501L;

    private String userId;
    private String empNo;
    private String userNm;
    private String deptCd;
    private String deptNm;
    private String jbgdNm;
    private String jbpsNm;
    private String trunIpAddr;
    private String pwCrvl;
    private String pwLastMdfcnDt;
    private int pwInptErrTcnt;
    private String acctLockYn;
    private String userStClssCd;

}
