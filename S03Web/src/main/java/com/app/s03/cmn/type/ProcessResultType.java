/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.type;

/**
 * <pre>
 * 패키지명: kobc.core.type
 * 클래스명: ProcessResultType
 * 설명: 거래처리결과구분코드 정의
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.03     ADMIN      1.0 최초작성
 * </pre>
 */
public enum ProcessResultType {
    SUCCESS ("0"),
    SYS_ERROR ("1"),
    BIZ_ERROR ("2"),;

    private String code;

    private ProcessResultType(String code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

}
