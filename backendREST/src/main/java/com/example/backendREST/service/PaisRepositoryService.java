package com.example.backendREST.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backendREST.model.Pais;
import com.example.backendREST.repository.PaisRepository;

@Service
public class PaisRepositoryService {
	
	private PaisRepository paisRepository;

	@Autowired
	public PaisRepositoryService(PaisRepository paisRepository) {
		this.paisRepository = paisRepository;
	}
	
	public List<Pais> findAll(){
        return paisRepository.findAll();
    }

	public Optional<Pais> listaProdutoUnico( long id) {
		return paisRepository.findById(id);
	}
	
	public Pais save( Pais pais){
		return paisRepository.save(pais);
	}
	
	public ResponseEntity<Optional<Pais>> deleteById(Long id){
		try {
			paisRepository.deleteById(id);
			return new ResponseEntity<Optional<Pais>>(HttpStatus.OK);
		}catch (NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Pais>>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Pais> update(Long id,  Pais newPais){
		return paisRepository.findById(id)
				.map(pais -> {
					pais.setNome(newPais.getNome());
					pais.setSigle(newPais.getSigle());
					pais.setGentilico(newPais.getGentilico());
					Pais paisUpdate = paisRepository.save(pais);
					return ResponseEntity.ok().body(paisUpdate);
				}).orElse(ResponseEntity.notFound().build());
	}
}
