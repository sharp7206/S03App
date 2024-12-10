/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.exception;

import com.app.s03.cmn.type.SystemMessage;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.exception
 * 클래스명: BizException
 * 설명: 비지니스 서비스 단에서 발생하는 Exception ([origin] egovframework.rte.fdl.cmmn.exception.EgovBizException)
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.04.14     HSLEE      1.0 최초작성
 * </pre>
 */
public class BizException extends BaseException {
    private static final long serialVersionUID = 3671707110863748086L;

    private String mssgCd;

    public BizException() {
        this(SystemMessage.SYS0000.name(), SystemMessage.SYS0000.getMessage());
    }

    public BizException(String mssgCd, String message) {
        this(mssgCd, message, null);
    }

    public BizException(String mssgCd, String message, Exception wrappedException) {
        this.mssgCd = mssgCd;
        this.message = message;
        this.wrappedException = wrappedException;
    }

    /**
     * @return the mssgCd
     */
    public String getMssgCd() {
        return mssgCd;
    }

}
