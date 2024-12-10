/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.dto;

import com.app.s03.cmn.vo.HeaderVO;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.dto
 * 클래스명: StdMssgDTO
 * 설명: 표준전문 DTO Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.05     ADMIN      1.0 최초작성
 * </pre>
 */
@Getter
public class StdMssgDTO {

    private HeaderVO header;
    @Setter
    private String body;

    public StdMssgDTO(HeaderVO header) {
        this.header = header;
    }

}
