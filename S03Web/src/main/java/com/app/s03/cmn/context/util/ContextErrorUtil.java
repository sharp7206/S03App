/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.context.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.app.s03.cmn.context.ContextHolder;
import com.app.s03.cmn.context.vo.StackTrace4CntsVO;
import com.app.s03.cmn.exception.CommonBusinessException;
import com.app.s03.cmn.utils.MessageUtils;

/**
 * <pre>
 * 패키지명: APP.core.context.util
 * 클래스명: ContextErrorUtil
 * 설명: Exception 관련 Utility Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.11.05     ADMIN      1.0 최초작성
 * </pre>
 */
public class ContextErrorUtil {

    /**
     * Context Area 에 Exception Error 정보 설정
     * 
     * @param exception
     * @param packageName
     * @param messageUtil
     */
    public static void setContextAreaErrInfo(Throwable exception, String packageName, MessageUtils messageUtil) {
        String configVal = messageUtil.getMessage("ca.stacktrace.max.cnt");
        int maxCnt = StringUtils.isNumeric(configVal) ? Integer.parseInt(configVal) : 1;

        // Context Area 값이 설정되지 않은 경우
        if (ContextHolder.get().getOcrnErrTrceNocs() < 1) {
            List<StackTrace4CntsVO> errorList = getStackTraceList(exception, packageName, maxCnt);
            ContextHolder.get().setOcrnErrTrceNocs(errorList.size());
            ContextHolder.get().setErrorList(errorList);
        }
    }

    /**
     * Stack Trace List 반환 (Context Area: Error List)
     * 
     * @param exception
     * @param packageName
     * @param maxCnt
     * @return List<StackTrace4CntsVO>
     */
    public static List<StackTrace4CntsVO> getStackTraceList(Throwable exception, String packageName, int maxCnt) {
        List<StackTrace4CntsVO> errorList = new ArrayList<StackTrace4CntsVO>();
        errorList.add(getErrorVo(exception, packageName));

        if (exception instanceof CommonBusinessException) {
            StackTrace4CntsVO stVo;
            Throwable throwable = exception;
            for (int i = 1; i < maxCnt; i++) {
                throwable = throwable instanceof CommonBusinessException ? ((CommonBusinessException) throwable).getCause()
                        : throwable.getCause();
                stVo = getErrorVo(throwable, null);

                if (stVo != null)
                    errorList.add(stVo);
                else
                    break;
            }
        }

        return errorList;
    }

    /**
     * Stack Trace VO 에 Exception 설정 후 반환
     * 
     * @param throwable
     * @param StackTrace4CntsVO
     * @return
     */
    private static StackTrace4CntsVO getErrorVo(Throwable throwable, String packageName) {
        if (throwable == null)
            return null;

        StackTrace4CntsVO errorVo = null;
        StackTraceElement ste = null;
        if (StringUtils.isNotEmpty(packageName)) {
            for (StackTraceElement element : throwable.getStackTrace()) {
                if (element.toString().startsWith(packageName)) {
                    ste = element;
                    break;
                }
            }
        } else {
            ste = throwable.getStackTrace()[0];
        }

        if (ste != null) {
            errorVo = StackTrace4CntsVO.builder().errNm(throwable.getClass().getName())
                    .errMssgCn(throwable.getMessage()).errOccClasNm(ste.getClassName())
                    .errOccMethNm(ste.getMethodName()).errOccLnNo(ste.getLineNumber()).build();
        }

        return errorVo;
    }

    /**
     * 예상치 못한 Exception 처리 (Error Max Count 고려하지 않음)
     * 
     * @param exception
     */
    public static void addContextAreaErrInfo(Throwable exception) {
        List<StackTrace4CntsVO> errorList;
        // Context Area 값이 설정되지 않은 경우
        if (ContextHolder.get().getOcrnErrTrceNocs() < 1) {
            errorList = new ArrayList<StackTrace4CntsVO>();
            errorList.add(getErrorVo(exception, null));
        } else {
            errorList = ContextHolder.get().getErrorList();
            errorList.add(0, getErrorVo(exception, null));
        }
        ContextHolder.get().setOcrnErrTrceNocs(errorList.size());
        ContextHolder.get().setErrorList(errorList);
    }

}
