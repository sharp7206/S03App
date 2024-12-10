/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename PreAccessFilter.java
 */
package com.app.s03.cmn.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <pre>
 * 패키지명: com.app.s03.cmn.security
 * 클래스명: PreAccessFilter
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.22     hslee      1.0 최초작성
 * </pre>
 */
public class PreAccessFilter extends OncePerRequestFilter {

	   private AccessDecisionManager accessDecisionManager;

	    public PreAccessFilter(AccessDecisionManager accessDecisionManager) {
	        this.accessDecisionManager = accessDecisionManager;
	    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
        	 // Try to obtain the Authentication object from the SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Create a dummy secured object for the request (replace this with your actual secured object)
            Object securedObject = new Object();

            // Call the decide method of your AccessDecisionManager
            accessDecisionManager.decide(authentication, securedObject, null);

            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // Handle exceptions if needed
            // You might want to redirect to an access denied page
            response.sendRedirect("/access-denied");
        }
    }

}
