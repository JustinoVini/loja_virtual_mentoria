package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

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

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.MarcaProduto;
import jdev.mentoria.lojavirtual.repository.MarcaProdutoRepository;

@RestController
public class MarcaProdutoController {

	@Autowired
	private MarcaProdutoRepository marcaRepository;

	@ResponseBody
	@PostMapping(value = "**/salvarMarca")
	public ResponseEntity<MarcaProduto> salvarMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionMentoriaJava {
		if (marcaProduto.getId() == null) {
			List<MarcaProduto> marcas = marcaRepository.buscaMarcaDesc(marcaProduto.getNomeDesc().toUpperCase());
			
			if (!marcas.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe uma marca com a descrição: " + marcaProduto.getNomeDesc());
			}
			
		}
		
		MarcaProduto marcaSalva = marcaRepository.save(marcaProduto);
		
		return new ResponseEntity<MarcaProduto>(marcaSalva, HttpStatus.OK);
	}

	@PostMapping(value = "**/deleteMarca")
	public ResponseEntity<String> deleteAcesso(@RequestBody MarcaProduto marcaProduto) {
		marcaRepository.deleteById(marcaProduto.getId());
		return new ResponseEntity<>("Marca removida com sucesso!", HttpStatus.OK);
	}

	@DeleteMapping(value = "**/deleteMarcaPorId/{id}")
	public ResponseEntity<String> deleteAcessoPorId(@PathVariable("id") Long id) {
		marcaRepository.deleteById(id);
		return new ResponseEntity<>("Marca removida com sucesso!", HttpStatus.OK);
	}

	@GetMapping(value = "**/obterMarcaPorId/{id}")
	public ResponseEntity<MarcaProduto> pesquisarPorId(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
		MarcaProduto marcaProduto = marcaRepository.findById(id)
				.orElseThrow(() -> new ExceptionMentoriaJava("Marca de produto com o id " + id + " não encontrado!"));
		return new ResponseEntity<>(marcaProduto, HttpStatus.OK);
	}

	@GetMapping(value = "**/obterMarcaPorDescricao/{desc}")
	public ResponseEntity<List<MarcaProduto>> pesquisarPorDescricao(@PathVariable("desc") String desc) {
		List<MarcaProduto> marcaProdutos = marcaRepository.buscaMarcaDesc(desc.toUpperCase().trim());
		return new ResponseEntity<>(marcaProdutos, HttpStatus.OK);
	}

}
