package com.example.backendREST.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendREST.auth.ApplicationUser;
import com.example.backendREST.security.JwtUtil;
import com.example.backendREST.service.UserService;



@CrossOrigin(origins="*")
@RestController
@RequestMapping(path ="/login")
public class LoginController {
	
	private UserService userS;
	private JwtUtil jwt;
	
	
	@Autowired
	public LoginController(UserService userS,JwtUtil  jwt) {
		this.userS = userS;
		this.jwt = jwt;
	}



	@PostMapping(path = "refresh")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		ApplicationUser user = userS.authenticated();
		String token = jwt.generateToken(user.getUsername(),user.getAuthorities());
		response.addHeader("Authorization","Bearer "+token);
		return ResponseEntity.ok().build();
	}
	
	
	


}

