/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2023
 * @filename DispatcherServletConfig.java
 */
package com.app.s03.cmn.config;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;


/**
 * dispatcher servlet 에 등록될 bean 작성
 * @author sbshin
 *
 */
@Configuration
@ComponentScan(
		basePackages = "com.app.s03",
		includeFilters = {
				@Filter(type = FilterType.ANNOTATION, classes = {Controller.class})	
		},
		excludeFilters = {
				@Filter(type = FilterType.ANNOTATION, classes = {Service.class, Configuration.class})
		})
public class DispatcherServletConfig extends WebMvcConfigurationSupport {

	private static final long MAX_UPLOAD_SIZE = 20000000l;
	
	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/res/**/*", "/plug/**/*")
			.addResourceLocations("/res/", "/plug/")
			.setCachePeriod(60);
	}
	
	@Bean
    public MultipartResolver multipartResolver() {
		
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        return multipartResolver;
    }

	@Bean 
	public InternalResourceViewResolver internalResourceViewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/page/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		
		return viewResolver;
	}
	
	@Override
	public SessionLocaleResolver localeResolver() {
		
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.KOREA);
		return localeResolver;
	}

	@Bean
    public TilesConfigurer tilesConfigurer() {
		
        final TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions(new String[]{"/WEB-INF/tiles-conf/tiles-define.xml"});
        configurer.setCheckRefresh(true);
        return configurer;
    }

    @Bean
    public TilesViewResolver tilesViewResolver() {
    	
        final TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        tilesViewResolver.setOrder(1);
        return tilesViewResolver;
    }
	
//	@Bean
//	public UrlBasedViewResolver viewResolver() {
//		
//		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
//		urlBasedViewResolver.setViewClass(JstlView.class);
//		urlBasedViewResolver.setPrefix("/WEB-INF/page/");
//		urlBasedViewResolver.setSuffix(".jsp");
//		return urlBasedViewResolver;
//	}
}
