package com.app.s03.cmn.ui.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.security.ComUserDetails;
import com.app.s03.cmn.service.MenuService;


public class ComHeaderInfoTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 3871073894951079034L;
	private static final Logger logger = LoggerFactory.getLogger(ComHeaderInfoTag.class);

	private String prgmcd;
	private MenuService service;

	public void setPrgmcd(String prgmcd) {
		this.prgmcd = prgmcd;
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
			service = ctx.getBean(MenuService.class);
		}
		ComUserDetails userDetails = (ComUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final StringWriter sw = new StringWriter();
		// getJspBody().invoke( sw );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("syscd", ComConstants.SYS_CD);
		map.put("prgmcd", prgmcd);
		map.put("roleCd", userDetails.getRoleCd());
		final Map<String, ?> headInfo = service.getHeadInfo(map);

//		sw.append("<div class=\"d-flex justify-content-between mb-1\">\n");
		sw.append("<div class=\"align both vm\">\n");
		sw.append("      <h2 class='h2'>"+(String) headInfo.get("menuNm")+"</h2>\n");
		sw.append("      <div class=\"location\">");
		sw.append("          <ul>");
		sw.append((String) headInfo.get("breadcrumb2"));
		sw.append("          </ul>");
		sw.append("      </div>");
		sw.append("<input type='hidden' name='crud' id='crud' value='"+(String) headInfo.get("winPriv")+"'>");
		sw.append("<input type='hidden' name='_prgmcd' id='_prgmcd' value='"+prgmcd+"'>");
		sw.append("</div>");
		
		//		+ "                </div>");
		/*sw.append("<div class=\"fw-bolder\">");
	    if(StringUtils.isNotEmpty((String) headInfo.get("iconnm"))) {
	    	sw.append("<i class=\"").append((String)headInfo.get("iconnm")).append(" nav-icon\"></i> ");
	    } else {
	    	sw.append("<i class=\"fa fa-info-circle nav-icon\"></i> ");
	    }
		sw.append((String) headInfo.get("menuNm"));
		sw.append("</div>");
		sw.append("<div class=\"fs-6 fw-lighter text-secondary\"><small>");
		sw.append("<i class=\"fa fa-home\"></i> ");
		sw.append((String) headInfo.get("breadcrumb"));
		sw.append("</small></div>");
		sw.append("</div>");
		*/
		if (logger.isDebugEnabled()) {
			logger.debug(headInfo.toString());
		}
		pageContext.getOut().write(sw.toString());
		return SKIP_BODY;
	}

}
