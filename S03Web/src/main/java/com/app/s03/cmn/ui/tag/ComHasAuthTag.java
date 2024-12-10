package com.app.s03.cmn.ui.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspContext;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.service.UserAuthorityService;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 추가권한권체크
-===============================================================================================================
*/
public class ComHasAuthTag extends RequestContextAwareTag {

	/**
	 */
	private static final long serialVersionUID = 6615662303499750120L;

	private String prgmcd;
	private String authcd;
	private UserAuthorityService service;

	public void setPrgmcd(String prgmcd) {
		this.prgmcd = prgmcd;
	}

	public void setAuthcd(String authcd) {
		this.authcd = authcd;
	}

	public void setJspContext(JspContext context) {
	}

	public void doTag() throws IOException {
	}

	@Override
	protected int doStartTagInternal() throws Exception {

		if (service == null) {
			// WebApplicationContext 를 얻는다.
			WebApplicationContext ctx = getRequestContext().getWebApplicationContext();
			service = ctx.getBean(UserAuthorityService.class);
		}
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> param = new HashMap<>();
		param.put("prgmcd", prgmcd);
		List<String> authorities = new ArrayList<String>();
		for (GrantedAuthority auth : userDetails.getAuthorities()) {
			authorities.add(auth.getAuthority());
		}
		param.put("authorities", authorities);

		// getJspBody().invoke( sw );
		//final Map<String, ?> auth = service.getUserProgramAuthority(param);
		String prgmauth = prgmauth = service.getUserProgramAuthority(param);
		prgmauth = prgmauth == null ? "-" : prgmauth;
		if(prgmauth.indexOf(authcd) != -1) {
			 return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
}
