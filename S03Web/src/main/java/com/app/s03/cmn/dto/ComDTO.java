/**
 * Copyright KOBC. All Rights Reserved.
 */
package com.app.s03.cmn.dto;

import org.apache.commons.lang3.StringUtils;

import com.app.s03.cmn.context.ContextHolder;
import com.app.s03.cmn.type.SystemMessage;
import com.app.s03.cmn.utils.MessageUtils;
import com.app.s03.cmn.vo.ComVO;
import com.app.s03.cmn.vo.HeaderVO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: kobc.ct.dto
 * 클래스명: KobcDTO
 * 설명: 공통 Response DTO Class
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2021.10.18     ADMIN      1.0 최초작성
 * </pre>
 */
@JsonPropertyOrder(alphabetic = false)
@Getter
@Setter
public class ComDTO<DATA extends ComVO> {

    private HeaderVO header;

    private DATA data;

    public ComDTO() {
        this.header = new HeaderVO();
    }

    public ComDTO<DATA> success() {
        if (StringUtils.isBlank(ContextHolder.get().getRspnsMssgCd())) {
            ContextHolder.get().setRspnsMssgCd(SystemMessage.SYS1000.name());
            ContextHolder.get().setRspnsMssgCn(SystemMessage.SYS1000.getMessage());
        }
        return this;
    }

    public ComDTO<DATA> success(String mssgCd, Object... mssgArgs) {
        ContextHolder.get().setRspnsMssgCd(mssgCd);
        ContextHolder.get().setRspnsMssgCn(MessageUtils.getMessage(mssgCd, mssgArgs));
        return this;
    }

}
