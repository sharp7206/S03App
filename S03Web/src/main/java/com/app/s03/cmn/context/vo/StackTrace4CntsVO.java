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
 * 클래스명: StackTrace4CntsVO
 * 설명: Context Area 에서 사용하는 Stack Trace VO Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.01     ADMIN      1.0 최초작성
 * </pre>
 */
@Builder
@Getter
@Setter
public class StackTrace4CntsVO implements Serializable {
    private static final long serialVersionUID = -3882375392725876059L;

    private String errNm; // 오류이름
    private String errMssgCn; // 오류메시지내용
    private String errOccClasNm; // 오류발생클래스명
    private String errOccMethNm; // 오류발생메소드명
    private int errOccLnNo; // 오류발생라인수

}
