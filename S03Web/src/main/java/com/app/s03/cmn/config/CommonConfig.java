/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2023
 * @filename CommonConfig.java
 */
package com.app.s03.cmn.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * context loader listener 에 등록될 bean 작성
 * 
 * @author sbshin
 *
 */
@EnableWebMvc
@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackages = "com.app.s03", includeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = { Service.class, Component.class }) }, excludeFilters = {
				@Filter(type = FilterType.ANNOTATION, classes = { Controller.class }) })
public class CommonConfig {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(new String[] { "classpath:message/message-common" });
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setCacheSeconds(5);
//	    messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean
	public RequestCache requestCache() {
		return new HttpSessionRequestCache();
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
	
	@Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
	/**/
}
