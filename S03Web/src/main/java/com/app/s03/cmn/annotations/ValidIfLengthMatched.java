/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.annotations
 * 클래스명: ValidateIfLegnthMatched
 * 설명: VO 멤버변수에 대한 정합성 규칙(문자열의 길이)를 지시하는 Annotation
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2022.01.06     yonghanlee      1.0 최초작성
 * </pre>
 */
public @interface ValidIfLengthMatched {

    String name() default "";

    int len();

}
