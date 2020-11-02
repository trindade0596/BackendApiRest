package com.example.backendREST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendREST.model.Pais;


@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
}

