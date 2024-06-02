package jdev.mentoria.lojavirtual.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.dto.ImagemProdutoDTO;
import jdev.mentoria.lojavirtual.model.ImagemProduto;
import jdev.mentoria.lojavirtual.repository.ImagemProdutoRepository;

@RestController
public class ImagemProdutoController {

	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;

	@GetMapping(value = "**/obterImagemPorProduto/{idProduto}")
	public ResponseEntity<List<ImagemProdutoDTO>> obterImagemPorProduto(@PathVariable("idProduto") Long idProduto) {
		List<ImagemProdutoDTO> dtos = new ArrayList<>();
		
		List<ImagemProduto> imagensProduto = imagemProdutoRepository.buscaImagemProduto(idProduto);
		
		for (ImagemProduto imagemProduto : imagensProduto) {
			ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
			imagemProdutoDTO.setId(imagemProduto.getId());
			imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
			imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
			imagemProdutoDTO.setImagemMiniatura(imagemProduto.getImagemMiniatura());
			imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());
			dtos.add(imagemProdutoDTO);
		}
		
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping(value = "**/salvarImagemProduto")
	public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto) {
		imagemProduto = imagemProdutoRepository.saveAndFlush(imagemProduto);
		ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
		imagemProdutoDTO.setId(imagemProduto.getId());
		imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
		imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
		imagemProdutoDTO.setImagemMiniatura(imagemProduto.getImagemMiniatura());
		imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());
		return new ResponseEntity<>(imagemProdutoDTO, HttpStatus.OK);
	}

	@DeleteMapping(value = "**/deleteImagemProdutoPorId/{id}")
	public ResponseEntity<?> deleteImagemProdutoPorId(@PathVariable("id") Long id) {
		imagemProdutoRepository.deleteById(id);

		return new ResponseEntity<String>("Produto Deletado com sucesso!", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deleteTodasImagensProduto/{idProduto}")
	public ResponseEntity<String> deleteTodasImagensProduto(@PathVariable("idProduto") Long idProduto) {
		imagemProdutoRepository.deleteImagens(idProduto);
		return new ResponseEntity<>("Imagens do produto removidas!", HttpStatus.OK);
	}

	@DeleteMapping(value = "**/deleteImagemObjeto")
	public ResponseEntity<String> deleteImagemProdutoPorId(@RequestBody ImagemProduto imagemProduto) {
		if (!imagemProdutoRepository.existsById(imagemProduto.getId())) {
			return new ResponseEntity<>("Imagem já foi removida ou no existe com esse id: " + imagemProduto.getId(),
					HttpStatus.OK);
		}
		imagemProdutoRepository.deleteById(imagemProduto.getId());
		return new ResponseEntity<>("Imagem removida!", HttpStatus.OK);
	}

}
