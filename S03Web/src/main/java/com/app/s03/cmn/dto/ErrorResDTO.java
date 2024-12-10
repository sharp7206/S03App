/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.app.s03.cmn.vo.HeaderVO;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.response
 * 클래스명: ErrorResDTO
 * 설명: 오류난 경우 Response DTO Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.02     ADMIN      1.0 최초작성
 * </pre>
 */
@JsonPropertyOrder(alphabetic = false)
@Getter
@Setter
public class ErrorResDTO {

    private HeaderVO header;
    private Object data;

    public static ErrorResDTO create() {
        return new ErrorResDTO();
    }

    public ErrorResDTO header(HeaderVO header) {
        this.header = header;
        return this;
    }

}
