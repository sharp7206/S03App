/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.vo;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.StringUtils;

import com.app.s03.cmn.annotations.ValidIfInLength;
import com.app.s03.cmn.annotations.ValidIfInRange;
import com.app.s03.cmn.annotations.ValidIfLengthMatched;
import com.app.s03.cmn.annotations.ValidIfLongerThan;
import com.app.s03.cmn.annotations.ValidIfNotEmpty;
import com.app.s03.cmn.annotations.ValidIfNotNull;
import com.app.s03.cmn.annotations.ValidIfPatternMatched;
import com.app.s03.cmn.annotations.ValidIfShorterThan;
import com.app.s03.cmn.annotations.ValidateDeeply;
import com.app.s03.cmn.context.ContextHolder;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 패키지명: APP.core.vo
 * 클래스명: ComVO
 * 설명: 공통 VO Class (모든 VO Class 상속 대상)
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.10.21     ADMIN      1.0 최초작성
 * </pre>
 */
@Getter
@Setter
@Slf4j
public class ComVO implements Serializable {
    private static final long serialVersionUID = 8057112466179744540L;

    // 최초등록사용자ID
    protected String frstRegUserId;
    // 최초등록일시
    protected String frstRegDt;
    // 최종변경사용자ID
    protected String lastChgUserId;
    // 최종변경일시
    protected String lastChgDt;
    // 처리기준일자
    protected String prcsCrtrYmd;
    // Validation 체크 결과
    protected ArrayList<String> checkErrors = null;

    /**
     * 공통 속성 적용
     */
    public void updateCmmnProps() {
        this.frstRegUserId = ContextHolder.get().getUserId();
        this.lastChgUserId = ContextHolder.get().getUserId();
        this.prcsCrtrYmd = ContextHolder.get().getPrcsCrtrYmd();
    }

    /**
     * VO입력항목의 정합성을 검증한다.
     * 
     * @param type VO타입
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends ComVO> boolean _validate(Class<T> type) {

        if (log.isDebugEnabled()) {
            log.debug(":::: _validate() is called ");
        }

        boolean result = true;

        Field[] fields = type.getDeclaredFields();
        ArrayList<String> _checkErrors = new ArrayList<>();

        for (Field field : fields) {

            if (log.isDebugEnabled()) {
                log.debug(":::: field name :: {}", field.getName());
            }

            // Annotation체크
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {

                if (log.isDebugEnabled()) {
                    log.debug(":::: annotation type :: {}", annotation.annotationType().getTypeName());
                }

                // Validation 처리
                if (annotation instanceof ValidIfNotNull) {
                    // ValidIfNotNull 처리
                    validateIfNotNull((ValidIfNotNull) annotation, field, _checkErrors);
                }

                if (annotation instanceof ValidIfNotEmpty) {
                    // ValidIfNotEmpty 처리
                    validateIfNotEmpty((ValidIfNotEmpty) annotation, field, _checkErrors);
                }

                if (annotation instanceof ValidIfPatternMatched) {
                    // ValidIfPatternMatched 처리
                    validateIfPatternMatched((ValidIfPatternMatched) annotation, field, _checkErrors);
                }

                if (annotation instanceof ValidIfLongerThan) {
                    // ValidIfLongerThan 처리
                    validateIfLongerThan((ValidIfLongerThan) annotation, field, _checkErrors);
                }

                if (annotation instanceof ValidIfShorterThan) {
                    // ValidIfShorterThan 처리
                    validateIfShorterThan((ValidIfShorterThan) annotation, field, _checkErrors);
                }

                if (annotation instanceof ValidIfInLength) {
                    // ValidIfInLength 처리
                    validateIfInLength((ValidIfInLength) annotation, field, _checkErrors);
                }

                if (annotation instanceof ValidIfInRange) {
                    // ValidIfInRange 처리
                    validateIfInRange((ValidIfInRange) annotation, field, _checkErrors);
                }

                if (annotation instanceof ValidIfLengthMatched) {
                    // ValidIfLengthMatched 처리
                    validateIfLengthMatched((ValidIfLengthMatched) annotation, field, _checkErrors);
                }

                // check deeply
                if (annotation instanceof ValidateDeeply) {
                    Object memberObj = get(field.getName());

                    // APP 클래스인 경우
                    if (memberObj instanceof ComVO) {
                        ((ComVO) memberObj)._validate(memberObj.getClass().asSubclass(ComVO.class));
                        ArrayList<String> subErrors = (ArrayList<String>) ((ComVO) memberObj).get("checkErrors");
                        if (subErrors != null) {
                            _checkErrors.addAll(subErrors);
                        }

                        // List인 경우
                    } else if (memberObj instanceof List) {

                        ((List) memberObj).forEach(item -> {
                            if (item instanceof ComVO) {
                                ((ComVO) item)._validate(item.getClass().asSubclass(ComVO.class));
                                ArrayList<String> subErrors = (ArrayList<String>) ((ComVO) item).get("checkErrors");
                                if (subErrors != null) {
                                    _checkErrors.addAll(subErrors);
                                }
                            }
                        });
                    }
                }
            }

        }

        // Validation 오류 처리
        if (!_checkErrors.isEmpty()) {
            result = false;
            checkErrors = _checkErrors;
        }

        return result;
    }

    /**
     * 
     * Field 가 null인지 체크한다. (모든 데이터 타입에 대해 체크)
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    private void validateIfNotNull(ValidIfNotNull annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);

        if (val == null) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                Method nameAttr = annotation.getClass().getDeclaredMethod("name");
                kName = String.format("%s[%s]", nameAttr.invoke(annotation).toString(), name);
            } catch (RuntimeException re) {
                log.error(re.getMessage(), re);
            } catch (Exception e) {
                log.error("ComVO validateIfNotNull - Exception!", e);
            }

            // ${} 은(는) 필수입력 항목입니다.
            //errors.add(CmmnMssgUtil.getMsgNm("EZZ0012", kName));
            errors.add("Field 가 null인지 체크한다. (모든 데이터 타입에 대해 체크)");
        }
    }

    /**
     * 
     * Field가 지정한 패턴에 부합하는지 체크한다. (String 데이터 타입에 대해 체크) 만약 Field값이 null이거나 Empty이면 skip 한다.
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    private void validateIfPatternMatched(ValidIfPatternMatched annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);
        String pattern = annotation.pattern();

        boolean valid = false;

        if (val == null) {
            valid = true;
        } else if (val instanceof String) {
            if (((String) val).length() == 0) {
                valid = true;
            } else if (!((String) val).matches(pattern)) {
                valid = false;
            } else {
                valid = true;
            }
        } else {
            valid = true;
        }

        if (!valid) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                kName = String.format("%s[%s]", annotation.name(), name);
            } catch (Exception e) {
                log.error("ComVO validateIfPatternMatched - Exception!", e);
            }

            // ${} 입력형식을 확인하세요.
            //errors.add(MssageUtils.getMsgNm("EZZ0068", kName));
            errors.add("${} 입력형식을 확인하세요.");
        }
    }

    /**
     * 
     * Field 가 비어있는지 체크한다. (String, Collection 데이터 타입에 대해 체크)
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    @SuppressWarnings("rawtypes")
    private void validateIfNotEmpty(ValidIfNotEmpty annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);

        // String, Collection객체에 대해서만 체크
        boolean valid = false;
        if (val == null) {
            valid = false;
        } else if (val instanceof String && StringUtils.isEmpty(val)) {
            valid = false;
        } else if (val instanceof Collection && ((Collection) val).isEmpty()) {
            valid = false;
        } else {
            valid = true;
        }

        if (!valid) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                kName = String.format("%s[%s]", annotation.name(), name);
            } catch (Exception e) {
                log.error("ComVO validateIfNotEmpty - Exception!", e);
            }

            // ${} 은(는) 필수입력 항목입니다.
            //errors.add(CmmnMssgUtil.getMsgNm("EZZ0012", kName));
            errors.add("${} 은(는) 필수입력 항목입니다.");
        }
    }

    /**
     * 
     * Field의 길이가 지정된 값보다 큰지 체크한다. (String 데이터 타입에 대해 체크) 만약 Field값이 null이거나 Empty이면 skip한다.
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    private void validateIfLongerThan(ValidIfLongerThan annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);
        int limit = annotation.limit();

        boolean valid = false;

        if (val == null) {
            valid = true;
        } else if (val instanceof String) {

            int lenVal = ((String) val).length();
            if (lenVal == 0) {
                valid = true;
            } else if (lenVal > limit) {
                valid = true;
            } else {
                valid = false;
            }
        } else {
            valid = true;
        }

        if (!valid) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                kName = String.format("%s[%s]", annotation.name(), name);
            } catch (Exception e) {
                log.error("ComVO validateIfLongerThan - Exception!", e);
            }

            // ${} 이(가) ${} 보다 커야 합니다. ${}
            //errors.add(CmmnMssgUtil.getMsgNm("EZZ0024", kName, limit, ""));
            errors.add("${} 이(가) ${} 보다 커야 합니다. ${}");

        }
    }

    /**
     * 
     * Field의 길이가 지정된 값보다 작은지 체크한다. (String 데이터 타입에 대해 체크) Field값이 null이거나 Empty이면 Skip한다.
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    private void validateIfShorterThan(ValidIfShorterThan annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);
        int limit = annotation.limit();

        boolean valid = false;

        if (val == null) {
            valid = true;
        } else if (val instanceof String) {

            int lenVal = ((String) val).length();
            if (lenVal == 0) {
                valid = true;
            } else if (lenVal < limit) {
                valid = true;
            } else {
                valid = false;
            }
        } else {
            valid = true;
        }

        if (!valid) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                kName = String.format("%s[%s]", annotation.name(), name);
            } catch (Exception e) {
                log.error("ComVO validateIfShorterThan - Exception!", e);
            }

            // ${} 이(가) ${} 보다 작아야 합니다. ${}
            //errors.add(CmmnMssgUtil.getMsgNm("EZZ0026", kName, limit, ""));
            errors.add(" ${} 이(가) ${} 보다 작아야 합니다. ${}");

        }
    }

    /**
     * 
     * Field의 길이가 지정된 값과 같은지 체크한다. (String 데이터 타입에 대해 체크) Field값이 null이거나 Empty이면 Skip한다.
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    private void validateIfLengthMatched(ValidIfLengthMatched annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);
        int len = annotation.len();

        boolean valid = false;

        if (val == null) {
            valid = true;
        } else if (val instanceof String) {

            int lenVal = ((String) val).length();
            if (lenVal == 0) {
                valid = true;
            } else if (lenVal == len) {
                valid = true;
            } else {
                valid = false;
            }
        } else {
            valid = true;
        }

        if (!valid) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                kName = String.format("%s[%s]", annotation.name(), name);
            } catch (Exception e) {
                log.error("ComVO validateIfLengthMatched - Exception!", e);
            }

            // ${} 값의 길이는 ${} 이어야 합니다.
            //errors.add(CmmnMssgUtil.getMsgNm("EZZ0078", kName, len));
            errors.add("${} 값의 길이는 ${} 이어야 합니다.");
        }
    }

    /**
     * 
     * Field가 지정한 길이 범위에 부합하는지 체크한다. (String 데이터 타입에 대해 체크) Field값이 null이거나 Empty이면 skip한다.
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    private void validateIfInLength(ValidIfInLength annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);
        int min = annotation.min();
        int max = annotation.max();
        boolean existMaxLimit = max != -1 ? true : false;

        boolean valid = false;

        if (val == null) {
            valid = true;
        } else if (val instanceof String) {

            int lenVal = ((String) val).length();
            if (lenVal == 0) {
                valid = true;
            } else if (existMaxLimit && lenVal >= min && lenVal <= max) {
                valid = true;
            } else if (!existMaxLimit && lenVal >= min) {
                valid = true;
            } else {
                valid = false;
            }
        } else {
            valid = true;
        }

        if (!valid) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                kName = String.format("%s[%s]", annotation.name(), name);
            } catch (Exception e) {
                log.error("ComVO validateIfInLength - Exception!", e);
            }

            if (existMaxLimit) {
                // ${} 의 범위는 ${}~${} 입니다.
                //errors.add(CmmnMssgUtil.getMsgNm("EZZ0077", kName, min, max));
                errors.add("${} 의 범위는 ${}~${} 입니다.");

                if (log.isDebugEnabled()) {
                    log.debug("::: max : {}", max);
                }
            } else {
                // ${} 은(는) ${} 보다 크거나 같아야 합니다.
                //errors.add(CmmnMssgUtil.getMsgNm("EZZ0075", kName, min));
                errors.add("${} 은(는) ${} 보다 크거나 같아야 합니다.");

            }

        }
    }

    /**
     * 
     * Field의 값이 지정된 범위에 존재하는지 체크한다. (Integer, Long, Double, Float 데이터 타입에 대해 체크)
     * 
     * @param annotation Annotation
     * @param field      체크하려는 필드
     * @param errors     Validation 체크 오류 정보를 담을 컨테이너
     */
    private void validateIfInRange(ValidIfInRange annotation, Field field, ArrayList<String> errors) {

        String name = field.getName();
        Object val = this.get(name);
        long min = annotation.min();
        long max = annotation.max();
        boolean existMaxLimit = max != -1 ? true : false;

        boolean valid = false;

        if (val == null) {
            valid = true;
        } else if (val instanceof Integer) {

            int intVal = ((Integer) val).intValue();
            if (existMaxLimit && intVal >= (int) min && intVal <= (int) max) {
                valid = true;
            } else if (!existMaxLimit && intVal >= (int) min) {
                valid = true;
            } else {
                valid = false;
            }

        } else if (val instanceof Long) {

            long longVal = ((Long) val).longValue();
            if (existMaxLimit && longVal >= min && longVal <= max) {
                valid = true;
            } else if (!existMaxLimit && longVal >= min) {
                valid = true;
            } else {
                valid = false;
            }

        } else {
            valid = true;
        }

        if (!valid) {

            // 대상 필드의 한글명
            String kName = "";
            try {
                kName = String.format("%s[%s]", annotation.name(), name);
            } catch (Exception e) {
                log.error("ComVO validateIfInRange - Exception!", e);
            }

            if (existMaxLimit) {
                // ${} 의 범위는 ${}~${} 입니다.
                //errors.add(CmmnMssgUtil.getMsgNm("EZZ0077", kName, min, max));
                errors.add("${} 의 범위는 ${}~${} 입니다.");
            } else {
                // ${} 은(는) ${} 보다 크거나 같아야 합니다.
                //errors.add(CmmnMssgUtil.getMsgNm("EZZ0075", kName, min));
                errors.add("${} 은(는) ${} 보다 크거나 같아야 합니다.");
            }

        }
    }

    /**
     * VO Class의 멤버항목 값을 리턴한다.
     * 
     * @param key
     * @return
     */
    public Object get(String key) {
        Object val = null;
        try {
            Method method = this.getClass().getMethod("get" + StringUtils.capitalize(key));
            val = method.invoke(this);
        } catch (RuntimeException re) {
            log.error(re.getMessage(), re);
        } catch (Exception e) {
            log.error("ComVO get - Exception!", e);
        }
        return val;
    }

}
