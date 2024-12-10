/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2023
 * @filename CacheConfig.java
 */
package com.app.s03.cmn.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ehcache bean 작성
 * @author sbshin
 *
 */
@Configuration
public class CacheConfig {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(applicationContext.getResource("classpath:cache/ehcache.xml"));
		ehCacheManagerFactoryBean.setShared(true);
		return ehCacheManagerFactoryBean;
	}
	
	@Bean
	public EhCacheCacheManager ehCacheManager() {
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
		return ehCacheCacheManager;
	}
	
	@Primary
    @Bean("cacheManager")
    public CompositeCacheManager cacheManager() {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setFallbackToNoOpCache(true);
        compositeCacheManager.setCacheManagers(Collections.singleton(ehCacheManager()));
        return compositeCacheManager;
    }
}
