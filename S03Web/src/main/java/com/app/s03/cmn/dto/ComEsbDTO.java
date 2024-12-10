/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.app.s03.cmn.vo.ComEsbVO;
import com.app.s03.cmn.vo.HeaderVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.dto
 * 클래스명: KobcEsbDTO
 * 설명: ESB 연계 DTO Class
 *
 * ==================================================================
 * 변경일자       변경자          변경내용
 * ------------------------------------------------------------------
 * 2021.12.15     HyojinKwon      1.0 최초작성
 * 2022.04.27     JinkukPark      데이터부 Class 변경 (KobcVO > KobcEsbVO)
 * </pre>
 */
@JsonPropertyOrder(alphabetic = false)
@Builder
@Getter
@Setter
public class ComEsbDTO {

    private HeaderVO header;
    private ComEsbVO data;

}
