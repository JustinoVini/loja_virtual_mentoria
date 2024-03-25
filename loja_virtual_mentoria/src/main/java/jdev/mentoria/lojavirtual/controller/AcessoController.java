package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("**/deleteAcesso")
	@ResponseBody
	public ResponseEntity<Acesso> deletaAcesso(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso removido", HttpStatus.OK);
	}

	@DeleteMapping("**/deleteAcessoPorId/{id}")
	@ResponseBody
	public ResponseEntity<?> deletaAcessoPorId(@PathVariable("id") Long id) {
		acessoRepository.deleteById(id);
		return new ResponseEntity("Acesso removido", HttpStatus.OK);
	}
	
	@GetMapping("**/obterAcesso/{id}")
	@ResponseBody
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) {
		var acesso = acessoRepository.findById(id).get();
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}
	
	@GetMapping("**/buscarPorDescricao/{desc}")
	@ResponseBody
	public ResponseEntity<List<Acesso>> buscarPorDescricao(@PathVariable("desc") String desc) {
		List<Acesso> acessos = acessoRepository.findAcessoByDescricao(desc);
		return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
	}

}
