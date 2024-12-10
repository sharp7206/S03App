/**
 * 
 */
package com.app.s03.cmn.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * security filter chain 등록
 * @author sbshin
 *
 */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
	    characterEncodingFilter.setInitParameter("encoding", "UTF-8");
	    characterEncodingFilter.setInitParameter("forceEncoding", "true");
	    characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}

}
