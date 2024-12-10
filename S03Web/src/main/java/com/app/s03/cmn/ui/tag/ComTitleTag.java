package com.app.s03.cmn.ui.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.app.s03.cmn.constant.ComConstants;
import com.app.s03.cmn.service.MenuService;



public class ComTitleTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 3871073894951079034L;
	private static final Logger logger = LoggerFactory.getLogger(ComTitleTag.class);
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

		final StringWriter sw = new StringWriter();
		// getJspBody().invoke( sw );

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("syscd", ComConstants.SYS_CD);
		map.put("prgmcd", prgmcd);
		final Map<String, ?> headInfo = service.getHeadInfo(map);

		sw.append((String) headInfo.get("menunm"));
		if (logger.isDebugEnabled()) {
			logger.debug("The programcd is {}.", prgmcd + "#" + sw.toString());
		}
		pageContext.getOut().write(sw.toString());
		return SKIP_BODY;
	}

}
