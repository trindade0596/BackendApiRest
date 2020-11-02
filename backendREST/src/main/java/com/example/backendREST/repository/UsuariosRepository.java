package com.example.backendREST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendREST.model.Usuario;



@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
	Usuario findByUsername(String username);

}

