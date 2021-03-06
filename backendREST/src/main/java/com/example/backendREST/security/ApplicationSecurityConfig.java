package com.example.backendREST.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.backendREST.auth.ApplicationUserService;
import com.example.backendREST.auth.JwtTokenVerifier;
import com.example.backendREST.auth.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final JwtUtil jwtUtil;
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
									 ApplicationUserService applicationUserService, JwtUtil jwtUtil) {
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.jwtUtil = jwtUtil;
	}
	


	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
				.csrf().disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtUtil))
				.addFilterAfter(new JwtTokenVerifier(authenticationManager(), jwtUtil, applicationUserService) ,JwtUsernameAndPasswordAuthenticationFilter.class )
				.authorizeRequests()
				
					.antMatchers(HttpMethod.DELETE, "/pais").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST, "/pais/post").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT, "/pais").hasRole("ADMIN")
					.antMatchers(HttpMethod.GET, "/pais/all").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST,"/usuario/renovar-ticket").hasRole("ADMIN")
					.antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.anyRequest()
					.authenticated();
					
				
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

	
	
}
