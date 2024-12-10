/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.annotations
 * 클래스명: ValidIfNotNull
 * 설명: VO 멤버변수에 대한 정합성 규칙(Not Null)을 지시하는 Annotation
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.12.07     yonghan.lee      1.0 최초작성
 * </pre>
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIfNotNull {
    String name() default "";
}
