package com.example.backendREST.security;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtUtil {
	
	private String key = "2td_OlaIsd7X5NZHRmB-lbxYpkIvHLWOg0lCCexIu1M";	
	
	public String generateToken(String username, Collection<? extends GrantedAuthority> collection) {
		return  Jwts.builder()
				.setSubject(username)
				.claim("authorities", collection)
				.setIssuedAt(new Date())
				.setExpiration((Date.from(ZonedDateTime.now().plusMinutes(5).toInstant())))
				.signWith(Keys.hmacShaKeyFor(key.getBytes()))
				.compact();
		
	}
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}

}
