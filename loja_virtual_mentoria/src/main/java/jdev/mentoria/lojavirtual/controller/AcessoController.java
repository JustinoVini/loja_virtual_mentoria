package jdev.mentoria.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.service.AcessoService;

@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@PostMapping("/salvarAcesso")
	public ResponseEntity<Acesso> salvar(@RequestBody Acesso acesso) {
		var acessoNovo = acessoService.save(acesso);
		return new ResponseEntity<>(acessoNovo, HttpStatus.CREATED);
	}
	
}
