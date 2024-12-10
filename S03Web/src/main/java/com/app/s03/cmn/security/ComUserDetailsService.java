package com.app.s03.cmn.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.mapper.CommonMapper;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
-
-===============================================================================================================
*/
@Service
public class ComUserDetailsService implements UserDetailsService {

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		ComUserDetails user = (ComUserDetails)commonMapper.selectOne("cmn.Login.selectUser", userid);
		if (user != null) {
			user.setSyscd(ComConstants.SYS_CD);
			List<Map<String, Object>> authorityList = commonMapper.selectList("cmn.Login.selectUserAuthorityList", user);
			if (authorityList.size() == 0) {
				throw new AccessDeniedException("시스템 권한이 없습니다.");
			}
			List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			for (Map<String, Object> authority : authorityList) {
				user.setRoleCd((String)authority.get("authority"));
				list.add(new SimpleGrantedAuthority((String) authority.get("authority")));
			}
			user.setAuthorities(list);
		}
		
		return user;
	}
}