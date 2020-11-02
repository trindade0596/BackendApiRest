package com.example.backendREST.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.backendREST.model.Usuario;
import com.example.backendREST.repository.UsuariosRepository;


@Repository
@Service
public class ApplicationUserService implements UserDetailsService {
	

	private UsuariosRepository usuario;
	
	@Autowired
	public ApplicationUserService(UsuariosRepository usuario) {
		this.usuario = usuario;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Usuario user = this.usuario.findByUsername(username);
		ApplicationUser applicationUser = new ApplicationUser(user);
		
		return applicationUser;
	}
	
	

}
