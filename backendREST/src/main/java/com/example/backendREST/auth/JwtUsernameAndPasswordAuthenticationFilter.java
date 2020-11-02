package com.example.backendREST.auth;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backendREST.auth.JwtUsernameAndPasswordAuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager; 
	
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
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
		
		String key = "2td_OlaIsd7X5NZHRmB-lbxYpkIvHLWOg0lCCexIu1M";	
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration((Date.from(ZonedDateTime.now().plusMinutes(5).toInstant())))
				.signWith(Keys.hmacShaKeyFor(key.getBytes()))
				.compact();
		
		response.addHeader("Authorization", "Bearer " + token);
	}

}