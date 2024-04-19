package jdev.mentoria.lojavirtual.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.dto.CategoriaProdutoDTO;
import jdev.mentoria.lojavirtual.model.CategoriaProduto;
import jdev.mentoria.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutoController {

	private final CategoriaProdutoRepository categoriaProdutoRepository;

	public CategoriaProdutoController(CategoriaProdutoRepository categoriaProdutoRepository) {
		this.categoriaProdutoRepository = categoriaProdutoRepository;
	}

	@PostMapping("**/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto categoriaProduto)
			throws ExceptionMentoriaJava {

		if (categoriaProduto.getEmpresa() == null || categoriaProduto.getEmpresa().getId() == null) {
			throw new ExceptionMentoriaJava("A empresa seve ser informada!");
		}

		if (categoriaProduto.getId() == null
				&& categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase())) {
			throw new ExceptionMentoriaJava("Categoria já existe com o nome informado!");
		}

		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);

		CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();
		categoriaProdutoDTO.setId(categoriaSalva.getId());
		categoriaProdutoDTO.setNomeDesc(categoriaSalva.getNomeDesc());
		categoriaProdutoDTO.setEmpresa(categoriaSalva.getEmpresa().getId().toString());

		return new ResponseEntity<>(categoriaProdutoDTO, HttpStatus.OK);
	}

	@PostMapping(value = "**/deleteCategoria")
	public ResponseEntity<String> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) {
		if (!categoriaProdutoRepository.findById(categoriaProduto.getId()).isPresent()) {
			return new ResponseEntity<>("Categoria já foi removida!", HttpStatus.OK);
		}

		categoriaProdutoRepository.deleteById(categoriaProduto.getId());
		return new ResponseEntity<>("Categoria removida com sucesso!", HttpStatus.OK);
	}

	@GetMapping(value = "**/obterCategoriaPorDescricao/{desc}")
	public ResponseEntity<List<CategoriaProduto>> pesquisarPorDescricao(@PathVariable("desc") String desc) {
		List<CategoriaProduto> categorias = categoriaProdutoRepository.buscarCategoriaDesc(desc.toUpperCase());
		return new ResponseEntity<>(categorias, HttpStatus.OK);
	}

}
