/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.context;

import org.apache.commons.beanutils.BeanUtils;

import com.app.s03.cmn.constant.ComConstants.HeaderReqResType;
import com.app.s03.cmn.context.vo.ComContentsVO;
import com.app.s03.cmn.vo.HeaderVO;


/**
 * <pre>
 * 패키지명: APP.core.context
 * 클래스명: ContextHolder
 * 설명: Context Area Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.04.04     HSLEE      1.0 최초작성
 * </pre>
 */
public class ContextHolder {

    private static final ThreadLocal<ComContentsVO> CONTEXT = new ThreadLocal<>();

    public static void set(ComContentsVO value) {
        CONTEXT.set(value);
    }

    public static ComContentsVO get() {
        ComContentsVO ctx = CONTEXT.get();
        if (ctx == null) {
            ctx = new ComContentsVO();
            CONTEXT.set(ctx);
        }
        return ctx;
    }

    public static void clear() {
        CONTEXT.remove();
    }

    /**
     * 표준헤더 기준으로 Context Area 항목 설정
     * 
     * @param stdHeader
     * @return ComContentsVO
     * @throws Exception
     */
    public static ComContentsVO createFromStdHeader(HeaderVO stdHeader) throws Exception {
        ComContentsVO ctx = new ComContentsVO();

        // Request Header 기준으로 Context Area 항목에 복사
        BeanUtils.copyProperties(ctx, stdHeader);

        // Header 항목ID 와 Context Area 항목ID 다른 경우 복사 처리후 추가작성 필요
        ctx.setCallSstmIpAddr(stdHeader.getUserIp());

        CONTEXT.set(ctx);

        return ctx;
    }

    /**
     * Context Area 기준으로 표준헤더 항목 설정 후 반환
     * 
     * @param reqResType
     * @return HeaderVO
     * @throws Exception
     */
    public static HeaderVO convertStdHeader(HeaderReqResType reqResType) throws Exception {
        HeaderVO stdHeader = new HeaderVO();

        // Context Area 기준으로 Header 항목에 복사
        BeanUtils.copyProperties(stdHeader, get());
        // Header 항목ID 와 Context Area 항목ID 다른 경우 복사 처리후 추가작성 필요
        stdHeader.setUserIp(get().getCallSstmIpAddr());
        stdHeader.setDmndRspnsClssVal(reqResType.getCode()); // 요청응답구분값

        return stdHeader;
    }

    /**
     * Context Area 정보 비어있는 확인
     * 
     * @return boolean
     */
    public static boolean isEmpty() {
        return CONTEXT.get() == null;
    }

}
