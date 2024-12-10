/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2023
 * @filename SecurityConfig.java
 */
package com.app.s03.cmn.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import com.app.s03.cmn.security.ComAccessDeniedHandler;
import com.app.s03.cmn.security.ComAuthenticationEntryPoint;
import com.app.s03.cmn.security.ComAuthenticationFailureHandler;
import com.app.s03.cmn.security.ComAuthenticationProvider;
import com.app.s03.cmn.security.ComAuthenticationSuccessHandler;
import com.app.s03.cmn.security.CustomAccessDecisionManager;

/**
 * spring security 설정, bean 작성
 * @author sbshin
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/**/*.js",
            "/**/*.css",
            "/res/**/*",
            "/plug/**/*",
            "/plug/sheet/**/*",
            "/plug/sheet/js/ibmsg.ko", // 새로운 패턴 추가
            "/page/tiles/*",
            "/api/cmn/*",
            "/api/cmn/common/message.do",
            "/api/cmn/SQL/executeSql.do",
            "/index.do",
            "/page/cmn/include/**",
            "/page/cmn/login/**",
            "/page/cmn/**",
            "/page/tiles/**"
    };

    @Autowired
    private ComAuthenticationProvider comAuthenticationProvider;

    @Autowired
    private ComAuthenticationEntryPoint comAuthenticationEntryPoint;

    @Autowired
    private ComAuthenticationSuccessHandler comAuthenticationSuccessHandler;

    @Autowired
    private ComAuthenticationFailureHandler comAuthenticationFailureHandler;

    @Autowired
    private CustomAccessDecisionManager customAccessDecisionManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .headers(headers ->
                headers.frameOptions(options ->
                    options.sameOrigin())
            )
            .authorizeRequests(authz ->
                authz
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    .antMatchers("/page/**").hasRole("ADMIN") // "/page/**" 패턴에 대한 접근은 ADMIN 역할이 있어야 함

            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login.do")
                    .usernameParameter("userid")
                    .passwordParameter("password")
                    .successHandler(comAuthenticationSuccessHandler)
                    .failureHandler(comAuthenticationFailureHandler)
            )
            .authenticationProvider(comAuthenticationProvider)
            .authorizeRequests()
            .accessDecisionManager(customAccessDecisionManager)
            .and()
            .logout(logout ->
                logout
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login.do")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
            )
            .sessionManagement(session ->
                session
                    .invalidSessionUrl("/login.do")
                    .sessionFixation(sessionFixation -> sessionFixation.newSession())
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl("/page/cmn/login/expired.do")
            )
            .csrf().disable()
            .exceptionHandling()
            .accessDeniedHandler(comAccessDeniedHandler())
            .authenticationEntryPoint(comAuthenticationEntryPoint);
    }

    @Bean
    public CustomAccessDecisionManager accessDecisionManager() {
    	/*
        List<AccessDecisionVoter<? extends Object>> decisionVoters =
                Arrays.asList(new WebExpressionVoter(), new RoleVoter(), new AuthenticatedVoter());
        return new CustomAccessDecisionManager(decisionVoters);
        */
    	List<AccessDecisionVoter<? extends Object>> decisionVoters =
                Arrays.asList(new WebExpressionVoter(), new RoleVoter(), new AuthenticatedVoter());
        CustomAccessDecisionManager accessDecisionManager = new CustomAccessDecisionManager(decisionVoters);
        accessDecisionManager.setNotCheckPattern(Arrays.asList(AUTH_WHITELIST));
        return accessDecisionManager;    	
    }

    @Bean
    public AccessDeniedHandler comAccessDeniedHandler() {
        ComAccessDeniedHandler comAccessDeniedHandler = new ComAccessDeniedHandler();
        comAccessDeniedHandler.setErrorPage("/page/cmn/common/accessDenied.do");
        return comAccessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptVersion.$2Y, 12);
        return passwordEncoder;
    }
}