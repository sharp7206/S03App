package com.app.s03.cmn.security.vo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserDetailVO implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6272205296627110367L;
	private String userId; //USERID
	private String loginId; //LOGINID
	private String userPasswd; //USER_PASSWD
	private String registNo; //REGISTNO
	private String userName; //USERNAME
	private String userGroupId; //USERGROUPID
	private String shopId; //SHOPID
	private String shopNm; //SHOPNM
	private String shopTypeCd; //SHOPTYPECD
	private String upshopId; //UPShopID
	private String deptCode; //DEPTCODE
	private String tel1; //TEL1
	private String tel2; //TEL2
	private String tel3; //TEL3
	private String mobile1; //MOBILE1
	private String mobile2; //MOBILE2
	private String mobile3; //MOBILE3
	private String email; //EMAIL
	private String userStatusCd; //USERSTATUSCD
	private String ssoYn; //SSO_YN
	private String pkiYn; //PKI_YN
	private String pwStartDate; //PW_STARTDATE
	private String oldPw1; //OLDPW1
	private String oldPw2; //OLDPW2
	private String oldPw3; //OLDPW3
	private String userYn; //USEYN
	private String roleCd; //ROLE_CD
	private List<Map<String, Object>> menuList;
	private List<UserRole> authList;

	private String j_username;
	private String j_password;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authList;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
