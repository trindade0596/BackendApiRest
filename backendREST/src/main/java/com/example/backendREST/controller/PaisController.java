package com.example.backendREST.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendREST.model.Pais;
import com.example.backendREST.repository.PaisRepository;
import com.example.backendREST.service.PaisRepositoryService;



@CrossOrigin
@RestController
@RequestMapping(path ="/pais")
public class PaisController {

private PaisRepositoryService service;

	@Autowired
	public PaisController(PaisRepositoryService service) {
	this.service = service;
}

	@GetMapping(path = "/listar")
    public List<Pais> FindAll(){
        return service.findAll();
    }
	
	@GetMapping("/pesquisar/{id}")
	public Optional<Pais> listaProdutoUnico(@PathVariable(value="id") long id) {
		return service.listaProdutoUnico(id);
	}
	
	@PostMapping(path = "salvar")
	public Pais Save(@RequestBody Pais pais){
		return service.save(pais);
	}
	
	@DeleteMapping(path = "/excluir/{id}")
	public ResponseEntity<Optional<Pais>> deleteById(@PathVariable Long id){

			return service.deleteById(id);
		}
	
	
	@PutMapping(value="/salvar/{id}")
	public ResponseEntity<Pais> update(@PathVariable Long id, @RequestBody Pais newPais){
		return service.update(id, newPais);
	}
	


}

