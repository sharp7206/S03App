/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename CustomAccessDecisionManager.java
 */
package com.app.s03.cmn.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.AntPathMatcher;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.utils.ConChar;
import com.app.s03.cmn.utils.SysUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.security
 * 클래스명: CustomAccessDecisionManager
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.22     hslee      1.0 최초작성
 * </pre>
 */
@Slf4j
public class CustomAccessDecisionManager implements AccessDecisionManager {
    private List<AccessDecisionVoter<? extends Object>> decisionVoters;

    @Autowired
    public CustomAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
    	super();
        this.decisionVoters = decisionVoters;
    }
    
    public List<String> getNotCheckPattern() {
        return notCheckPattern;
    }
    
	/**
	 * db mapper
	 */
	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * 접근제어 예외처리을 위해서리..
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 접근제어 예외처리 목록
     */
    private List<String> notCheckPattern;

	/**
	 * 접근제어 예외처리 목록 세팅
	 * @param notCheckPattern
	 */
	public void setNotCheckPattern(List<String> notCheckPattern) {
		this.notCheckPattern = notCheckPattern;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
	 */
	@Override
	public void decide(Authentication authentication, Object filter, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// TODO Auto-generated method stub
		if ((filter == null) || !this.supports(filter.getClass())) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}

		String url = ((FilterInvocation) filter).getRequestUrl();
		String contexto = ((FilterInvocation) filter).getRequest().getContextPath();
		String prgmcd = SysUtils.getPrograCdFromUrl(url);//url 로부터 프로그램코드(2자리 혹은 6자리) 추출.
		//log.debug(">>>>>>>>>>>>>>>>>>>>Url:{},Contexto:{},Programcd:{},Ip:{},Userid:{}",url,contexto,prgmcd);
		if(!ConChar.isNull(prgmcd)) {
			if(!url.endsWith("/null") && !"anonymousUser".equals(authentication.getPrincipal()) && !url.startsWith("/api/cmn/")) {
				WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
				Map<String, Object> accessMap = new HashMap<>();
				accessMap.put("accessurl",url);
				accessMap.put("syscd",ComConstants.SYS_CD);
				accessMap.put("userid",authentication.getName());
				accessMap.put("userip",web.getRemoteAddress());// IP
				accessMap.put("prgmcd",prgmcd);// 프로그램코드
				commonMapper.insert("cmn.Login.insertAccesslog", accessMap);// 로그인 성공시 시각 저장
				if (log.isDebugEnabled()) {
					//log.debug("Url:{},Contexto:{},Programcd:{},Ip:{},Userid:{}",url,contexto,prgmcd,web.getRemoteAddress(),authentication.getName());
				}
			}

			boolean isNeedCheck = true;
			for (String pattern : notCheckPattern) {
				if(pathMatcher.match(pattern, url)) {
					isNeedCheck = false;
					break;
				}
			}

			if(isNeedCheck) {
				//프로그램권한체크(프로그램코드있을경우)
				Map<String, Object> tempMap = new HashMap();
				tempMap.put("syscd", ComConstants.SYS_CD);
				tempMap.put("prgmcd", prgmcd);			
				String authgrps = StringUtils.isEmpty(prgmcd) ? commonMapper.selectOne("cmn.Login.selectAllAuthority", ComConstants.SYS_CD) : commonMapper.selectOne("cmn.Login.selectProgramAuthority", tempMap);
				String retVal = StringUtils.isEmpty(authgrps) ? "-" : authgrps;// 프로그램코드에 대한 권한있는 권한구룹목록. (권한그룹코드,권한그룹코드)
				if (log.isDebugEnabled()) {
					//log.debug("Url:{},Programcd:{},Auth:{}",url,prgmcd,retVal);
				}
				
				int deny = 0;
				Collection<ConfigAttribute> roles = SecurityConfig.createListFromCommaDelimitedString(retVal);// (권한그룹코드,권한그룹코드)
				for (AccessDecisionVoter<? extends Object> voter : decisionVoters) {
					int result = ((AccessDecisionVoter<Object>) voter).vote(authentication, filter, roles);
					//int result = voter.vote(authentication, (FilterInvocation) filter, roles);
					if (log.isDebugEnabled()) {
						log.debug("Voter:{},Returned:{}",voter,result);
					}
					switch (result) {
					case AccessDecisionVoter.ACCESS_GRANTED:
						return;
					case AccessDecisionVoter.ACCESS_DENIED:
						deny++;
						break;
					default:
						break;
					}
				}
				if (log.isDebugEnabled()) {
					//log.debug("Url:{},Contexto:{},Programcd:{},Deny:{}",url,contexto,prgmcd,deny);
				}
				if (deny > 0) {
					throw new AccessDeniedException("AbstractAccessDecisionManager.accessDenied 접근 권한이 없습니다.["+url+"]");
				}
				
				// To get this far, every AccessDecisionVoter abstained
				//checkAllowIfAllAbstainDecisions();
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.ConfigAttribute)
	 */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        // Check if the implementation supports the provided ConfigAttribute
        // Return true if supported, false otherwise
        return true; // Replace with your actual implementation
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // Check if the implementation supports the provided class
        // Return true if supported, false otherwise
        return true; // Replace with your actual implementation
    }

}
