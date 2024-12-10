/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.annotations;

import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;

@Retention(CLASS)
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.annotations
 * 클래스명: SuppressFBWarnings
 * 설명: FindBugs 경고 억제 Annotation
 *
 * ==================================================================
 * 변경일자       변경자          변경내용
 * ------------------------------------------------------------------
 * 2022.03.21     hslee      1.0 최초작성
 * </pre>
 */

public @interface SuppressFBWarnings {
    /**
     * The set of FindBugs warnings that are to be suppressed in annotated element. The value can be a bug category,
     * kind or pattern.
     */
    String[] value() default {};

    /** Optional documentation of the reason why the warning is suppressed. */
    String justification() default "";

}
