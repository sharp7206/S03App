/**
 * Copyright APP. All Rights Reserved.
 */
package com.app.s03.cmn.vo;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.constant.ComConstants.MassDataType;
import com.app.s03.cmn.context.ContextHolder;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: APP.core.vo
 * 클래스명: APPPagingVO
 * 설명: 공통 Paging VO Class (대량조회 처리하는 모든 VO Class 상속 대상)
 *
 * ==================================================================
 * 변경일자       변경자          변경내용
 * ------------------------------------------------------------------
 * 2021.12.24     HyojinKwon      1.0 최초작성
 * </pre>
 */
@Getter
@Setter
public class ComPagingVO extends ComVO {
    private static final long serialVersionUID = 1943540220294959941L;

    // 대량조회처리방법구분값 (N: N/A, 1: 연속거래, 2: 페이징)
    protected String evlmInqPrcsMethClssVal;
    // 페이지크기
    protected int pageSz;
    // 페이지번호
    protected int pageNo;
    // 차기결과존재여부
    protected String nxtRsltExstYn;
    // 현재조회건수
    protected int prsInqNocs;
    // 전체조회건수
    protected int allInqNocs;

    /**
     * 대량조회관련 속성 적용
     * 
     * @param prsInqNocs
     */
    public void updatePagingProps(int prsInqNocs) {
        ContextHolder.get().setPrsInqNocs(prsInqNocs);
        // 연속거래
        if (MassDataType.SCROLLING.getCode().equals(this.evlmInqPrcsMethClssVal)) {
            ContextHolder.get().setAllInqNocs(this.allInqNocs + prsInqNocs);
            ContextHolder.get().setNxtRsltExstYn(this.pageSz == prsInqNocs ? ComConstants.Y : ComConstants.N);
        }
    }

    /**
     * 대량조회관련 속성 적용
     * 
     * @param prsInqNocs
     * @param allInqNocs
     */
    public void updatePagingProps(int prsInqNocs, int allInqNocs) {
        updatePagingProps(prsInqNocs);
        // 페이징
        if (MassDataType.PAGING.getCode().equals(this.evlmInqPrcsMethClssVal)) {
            ContextHolder.get().setAllInqNocs(allInqNocs);
            if (this.pageNo == 0)
                ContextHolder.get().setPageSz(1);
        }
    }

}
