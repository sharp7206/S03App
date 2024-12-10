/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2023
 * @filename ApplicationInitializer.java
 */
package com.app.s03.cmn.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * java based configuration 을 읽어 세팅하는 web xml 역할
 * @author sbshin
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { CommonConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { DispatcherServletConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	
}
