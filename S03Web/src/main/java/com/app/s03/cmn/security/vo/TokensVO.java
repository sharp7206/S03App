package com.app.s03.cmn.security.vo;

import com.app.s03.cmn.vo.ComVO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.security.vo
 * 클래스명: TokensVO
 * 설명: Token VO Class
 *
 * ==================================================================
 * 변경일자       변경자          변경내용
 * ------------------------------------------------------------------
 * 2024.04.04     HSLEE      1.0 최초작성
 * </pre>
 */
@Builder
@Getter
@Setter
public class TokensVO extends ComVO {
    private static final long serialVersionUID = 642238552592464441L;
    private String userId;
    private String accessToken;
    private String refreshToken;
}
