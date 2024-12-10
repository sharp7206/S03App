package com.app.s03.cmn.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.AntPathMatcher;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.utils.SysUtils;
/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 보호해야할 url 접근제어 판단하는 클래스
-===============================================================================================================
*/
public class ComAccessDecisionManager extends AbstractAccessDecisionManager {

	/**
	 * 로그
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

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

	/**
	 * db mapper
	 */
	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	/**
	 * @param decisionVoters
	 */
	protected ComAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		super(decisionVoters);
	}

	/*
	 * 접근권한 판단.
	 * (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void decide(Authentication authentication, Object filter, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

		if ((filter == null) || !this.supports(filter.getClass())) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}

		String url = ((FilterInvocation) filter).getRequestUrl();
		String contexto = ((FilterInvocation) filter).getRequest().getContextPath();
		String prgmcd = SysUtils.getPrograCdFromUrl(url);//url 로부터 프로그램코드(2자리 혹은 6자리) 추출.
		if(!url.endsWith("/null") && !"anonymousUser".equals(authentication.getPrincipal()) && !url.startsWith("/api/cmn/")) {
			WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
			Map<String, Object> accessMap = new HashMap<>();
			accessMap.put("accessurl",url);
			accessMap.put("syscd",ComConstants.SYS_CD);
			accessMap.put("userid",authentication.getName());
			accessMap.put("userip",web.getRemoteAddress());// IP
			accessMap.put("prgmcd",prgmcd);// 프로그램코드
			commonMapper.insert("cmn.Login.insertAccesslog", accessMap);// 로그인 성공시 시각 저장
			if (logger.isDebugEnabled()) {
				logger.debug("Url:{},Contexto:{},Programcd:{},Ip:{},Userid:{}",url,contexto,prgmcd,web.getRemoteAddress(),authentication.getName());
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
			if (logger.isDebugEnabled()) {
				logger.debug("Url:{},Programcd:{},Auth:{}",url,prgmcd,retVal);
			}

			int deny = 0;
			Collection<ConfigAttribute> roles = SecurityConfig.createListFromCommaDelimitedString(retVal);// (권한그룹코드,권한그룹코드)
			for (AccessDecisionVoter voter : getDecisionVoters()) {
				int result = voter.vote(authentication, filter, roles);//해당자원에대한 권한 확인..
				if (logger.isDebugEnabled()) {
					logger.debug("Voter:{},Returned:{}",voter,result);
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
			if (logger.isDebugEnabled()) {
				logger.debug("Url:{},Contexto:{},Programcd:{},Deny:{}",url,contexto,prgmcd,deny);
			}
			if (deny > 0) {
				throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "접근 권한이 없습니다.["+url+"]"));
			}
			// To get this far, every AccessDecisionVoter abstained
			checkAllowIfAllAbstainDecisions();
		}
	}
}