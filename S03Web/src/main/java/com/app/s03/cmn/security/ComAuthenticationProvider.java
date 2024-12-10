/**
 * 
 */
package com.app.s03.cmn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hslee
 *
 */
@Slf4j
@Component
public class ComAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		if (userDetails == null) {
			
			log.error("존재하지 않는 유저", username);
			throw new UsernameNotFoundException("존재하지 않는 유저입니다 : " + username);
		}
		
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			
			throw new BadCredentialsException("password 불일치");
		}
		
		return new UsernamePasswordAuthenticationToken(
				userDetails,
				authentication.getCredentials(), 
				userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
