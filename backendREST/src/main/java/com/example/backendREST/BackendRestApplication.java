package com.example.backendREST;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.backendREST.model.Pais;
import com.example.backendREST.model.Usuario;
import com.example.backendREST.repository.PaisRepository;
import com.example.backendREST.repository.UsuariosRepository;

@SpringBootApplication
public class BackendRestApplication {
	
	private static PasswordEncoder pass;
	private static UsuariosRepository usuario;
	private static PaisRepository pais;
	
	@Autowired
	public BackendRestApplication( PaisRepository pais,PasswordEncoder pass, UsuariosRepository usuario) {
		this.pass = pass;
		this.usuario = usuario;
		this.pais = pais;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendRestApplication.class, args);
		
		Usuario convidado = new Usuario("convidado",pass.encode("manager"),1,"ROLE_USER");
		Usuario admin = new Usuario("admin",pass.encode("suporte"),1,"ROLE_ADMIN");
		List<Usuario> usuarios = Arrays.asList(convidado,admin);
		usuario.saveAll(usuarios);
		
		Pais brasil = new Pais("Brasil","BR","Brasileiro");
		Pais argentina = new Pais("Argentina","AR","Argentino");
		Pais alemanha = new Pais("Alemanha","AL","Alemão");
		List<Pais> paises = Arrays.asList(brasil,argentina,alemanha);
		pais.saveAll(paises);
	}

}
