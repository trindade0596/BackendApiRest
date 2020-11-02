package com.example.backendREST.model;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "pais")
public class Pais implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String sigle;
    private String gentilico;
    
    
	public Pais() {

	}


	public Pais(String nome, String sigle, String gentilico) {
		this.nome = nome;
		this.sigle = sigle;
		this.gentilico = gentilico;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSigle() {
		return sigle;
	}


	public void setSigle(String sigle) {
		this.sigle = sigle;
	}


	public String getGentilico() {
		return gentilico;
	}


	public void setGentilico(String gentilico) {
		this.gentilico = gentilico;
	}
	
	

   
}