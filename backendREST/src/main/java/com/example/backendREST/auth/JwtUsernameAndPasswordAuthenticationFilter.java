package com.example.backendREST.auth;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.backendREST.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager; 
	private final JwtUtil jwtUtil;
	
	@Autowired
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil= jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException{
	  try {
		JwtUsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
		    .readValue(request.getInputStream(), JwtUsernameAndPasswordAuthenticationRequest.class);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(),
				authenticationRequest.getPassword()
				);
		
		Authentication authenticate = authenticationManager.authenticate(authentication);
		return authenticate;
		
	  }catch (IOException e) {
		  throw new RuntimeException(e);
	  }
	
}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,	
											Authentication authResult) throws IOException, ServletException {
		String username = ((ApplicationUser) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username, authResult.getAuthorities());
		
		response.addHeader("Authorization", "Bearer " + token);
	}

	

}