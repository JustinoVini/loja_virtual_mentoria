package jdev.mentoria.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;

	@PostMapping("**/salvarAcesso")
	@ResponseBody
	public ResponseEntity<Acesso> salvar(@RequestBody Acesso acesso) {
		var acessoNovo = acessoService.save(acesso);
		return new ResponseEntity<>(acessoNovo, HttpStatus.OK);
	}

	@DeleteMapping("**/deleteAcesso")
	@ResponseBody
	public ResponseEntity<Acesso> deletaAcesso(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso removido", HttpStatus.OK);
	}

}
