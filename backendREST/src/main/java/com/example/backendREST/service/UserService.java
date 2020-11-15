package com.example.backendREST.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.backendREST.auth.ApplicationUser;

@Service
@Component
public class UserService {
	
	public  ApplicationUser authenticated() {
		try {
			return ((ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		}
		catch (Exception e) {
			 throw new RuntimeException(e);
		}
	}
		

}
