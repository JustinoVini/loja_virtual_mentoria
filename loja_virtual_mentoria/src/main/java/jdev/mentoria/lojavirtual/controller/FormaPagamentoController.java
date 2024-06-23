package jdev.mentoria.lojavirtual.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.FormaPagamento;
import jdev.mentoria.lojavirtual.repository.FormaPagamentoRepository;

@RestController
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@PostMapping(value = "**/salvarFormaPagamento")
	public ResponseEntity<FormaPagamento> salvarFormaPagamento(@RequestBody @Valid FormaPagamento formaPagamento) throws ExceptionMentoriaJava {		
		FormaPagamento formaPagamentoSalva = formaPagamentoRepository.save(formaPagamento);
		return new ResponseEntity<>(formaPagamentoSalva, HttpStatus.OK);
	}
	
}
