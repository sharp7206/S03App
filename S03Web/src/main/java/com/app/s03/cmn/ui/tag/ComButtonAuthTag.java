package com.app.s03.cmn.ui.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.service.UserAuthorityService;

/**
-===============================================================================================================
- 아래 프로그램에 대한 저작권을 포함한 지적재산권은 APP에 있으며,
- APP가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
- APP의 지적재산권 침해에 해당됩니다.
- (Copyright ⓒ HSL Cof., Ltd., Ltd. All Rights Reserved| Confidential)
-===============================================================================================================
- 버턴권한처리
-===============================================================================================================
*/
public class ComButtonAuthTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 3871073894951079034L;

	private final String BTN_ADD = "C";//추가버턴
	private final String BTN_DEL = "D";//삭제버턴
	private final String BTN_SAVE = "U";//저장버턴
	private final String BTN_EXCEL = "E";//엑셀버턴
	private final String BTN_PRINT = "P";//출력버턴
	private final String BTN_EXV = "V";//V권한
	private final String BTN_EXW = "W";//W권한
	private final String BTN_EXX = "X";//X권한
	private final String BTN_EXY = "Y";//Y권한
	private final String BTN_EXZ = "Z";// Z권한
	private final String ICON_DEFAULT = "<i class=\"fas fa-caret-right\"> ";

	private String prgmcd;//화면 코드
	private String prefix;// 버턴접두어
	private String viewbtns;// 노출대상 버턴목록.
	private UserAuthorityService service;// 권한체크 서비스.

	private static final Logger logger = LoggerFactory.getLogger(ComButtonAuthTag.class);

	public void setPrgmcd(String prgmcd) {
		this.prgmcd = prgmcd;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setViewbtns(String viewbtns) {
		this.viewbtns = viewbtns;
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
		param.put("syscd", ComConstants.SYS_CD);
		param.put("prgmcd", prgmcd);
		List<String> authorities = new ArrayList<String>();
		for (GrantedAuthority auth : userDetails.getAuthorities()) {
			authorities.add(auth.getAuthority());
		}
		param.put("authorities", authorities);
		final StringWriter sw = new StringWriter();
		// getJspBody().invoke( sw );
		viewbtns = viewbtns == null ? "-" : viewbtns;
		String prgmauth = service.getUserProgramAuthority(param);
		prgmauth = prgmauth == null ? "-" : prgmauth;
		sw.append("<div class=\"btn-group btn-group-xs\">");
		if (viewbtns.indexOf("L") != -1) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-list\"> <i class=\"fa fa-list\"></i> 목록 </button>");
		}
		if (viewbtns.indexOf(BTN_ADD) != -1 && prgmauth.indexOf(BTN_ADD) != -1) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-add\"> <i class=\"fas fa-plus-circle\"></i> 추가 </button>");
		}
		if (viewbtns.indexOf(BTN_DEL) != -1 && prgmauth.indexOf(BTN_DEL) != -1) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-del\"> <i class=\"fas fa-trash-alt\"></i> 삭제 </button>");
		}
		if (viewbtns.indexOf(BTN_SAVE) != -1 && prgmauth.indexOf(BTN_SAVE) != -1) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-save\"><i class=\"far fa-save\"></i> 저장 </button>");
		}
		if (viewbtns.indexOf(BTN_EXCEL) != -1 && prgmauth.indexOf(BTN_EXCEL) != -1) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-excel\"> <i class=\"fas fa-file-excel\"></i> 엑셀 </button>");
		}
		if (viewbtns.indexOf(BTN_PRINT) != -1 && prgmauth.indexOf(BTN_PRINT) != -1) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-print\"> <i class=\"fas fa-print\"></i> 인쇄 </button>");
		}
		
		/*
		if (viewbtns.indexOf(BTN_EXV) != -1 && prgmauth.indexOf(BTN_EXV) != -1 && StringUtils.isNotEmpty((String) auth.get("vauthnm"))) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-exv\"> ");
			if (StringUtils.isEmpty((String) auth.get("viconnm"))) {
				sw.append("<i class=\"fas fa-caret-right\"> ");
			} else {
				sw.append("<i class=\"").append((String) auth.get("viconnm")).append("\"> ");
			}
			sw.append("</i> ");
			sw.append((String) auth.get("vauthnm")).append(" </button>");
		}
		if (viewbtns.indexOf(BTN_EXW) != -1 && prgmauth.indexOf(BTN_EXW) != -1 && StringUtils.isNotEmpty((String) auth.get("wauthnm"))) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-exw\"> ");
			if (StringUtils.isEmpty((String) auth.get("wiconnm"))) {
				sw.append(ICON_DEFAULT);
			} else {
				sw.append("<i class=\"").append((String) auth.get("wiconnm")).append("\"> ");
			}
			sw.append("</i> ");
			sw.append((String) auth.get("wauthnm")).append(" </button>");
		}
		if (viewbtns.indexOf(BTN_EXX) != -1 && prgmauth.indexOf(BTN_EXX) != -1 && StringUtils.isNotEmpty((String) auth.get("xauthnm"))) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-exx\"> ");
			if (StringUtils.isEmpty((String) auth.get("xiconnm"))) {
				sw.append(ICON_DEFAULT);
			} else {
				sw.append("<i class=\"").append((String) auth.get("xiconnm")).append("\"> ");
			}
			sw.append("</i> ");
			sw.append((String) auth.get("xauthnm")).append(" </button>");
		}
		if (viewbtns.indexOf(BTN_EXY) != -1 && prgmauth.indexOf(BTN_EXY) != -1 && StringUtils.isNotEmpty((String) auth.get("yauthnm"))) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-exy\"> ");
			if (StringUtils.isEmpty((String) auth.get("yiconnm"))) {
				sw.append(ICON_DEFAULT);
			} else {
				sw.append("<i class=\"").append((String) auth.get("yiconnm")).append("\"> ");
			}
			sw.append("</i> ");
			sw.append((String) auth.get("yauthnm")).append(" </button>");
		}
		if (viewbtns.indexOf(BTN_EXZ) != -1 && prgmauth.indexOf(BTN_EXZ) != -1 && StringUtils.isNotEmpty((String) auth.get("zauthnm"))) {
			sw.append("<button type=\"button\" class=\"btn btn-secondary\" data-btn-control=\"");
			sw.append(prefix);
			sw.append("-btn-exz\"> ");
			if (StringUtils.isEmpty((String) auth.get("ziconnm"))) {
				sw.append(ICON_DEFAULT);
			} else {
				sw.append("<i class=\"").append((String) auth.get("ziconnm")).append("\"> ");
			}
			sw.append("</i> ");
			sw.append((String) auth.get("zauthnm")).append(" </button>");
		}
		*/
		sw.append("</div>");
		if (logger.isDebugEnabled()) {
			logger.debug("The prgmcd is {}.", prgmcd + "#" + prgmauth);
		}
		pageContext.getOut().write(sw.toString());
		return SKIP_BODY;
	}
}
