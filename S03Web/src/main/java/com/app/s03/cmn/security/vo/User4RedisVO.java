/**
 * Copyright HSLEE. All Rights Reserved.
 */
package com.app.s03.cmn.security.vo;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.security.vo
 * 클래스명: User4RedisVO
 * 설명: Redis에 사용자 정보를 저장하기 위한 VO Class
 *
 * ==================================================================
 * 변경일자       변경자          변경내용
 * ------------------------------------------------------------------
 * 2024.04.15     HSLEE      1.0 최초작성
 * </pre>
 */
@Getter
public class User4RedisVO implements Serializable {
    private static final long serialVersionUID = 3469883297939046368L;

    @Setter
    private String lastAccessTime;
    private User4LoginVO userVo;
    private List<UserRole> authorities;
    @Setter
    private String indivLgnYn;

    @ConstructorProperties({ "lastAccessTime", "userVo", "authorities", "indivLgnYn" })
    public User4RedisVO(String lastAccessTime, User4LoginVO userVo, List<UserRole> authorities, String indivLgnYn) {
        this.lastAccessTime = lastAccessTime;
        this.userVo = userVo;
        this.authorities = authorities;
        this.indivLgnYn = indivLgnYn;
    }

}
