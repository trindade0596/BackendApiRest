package com.example.backendREST.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.backendREST.model.Usuario;



public class ApplicationUser implements UserDetails{

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	
	@Autowired
	public ApplicationUser(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities  = new ArrayList<>();
		
		this.usuario.getRoleList().forEach(r -> {
			GrantedAuthority authority = new SimpleGrantedAuthority(r);
			authorities.add(authority);
		});
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return this.usuario.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.usuario.getActive() == 1;
	}

	
}