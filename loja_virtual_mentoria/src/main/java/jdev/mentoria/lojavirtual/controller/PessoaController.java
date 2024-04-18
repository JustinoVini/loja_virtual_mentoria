package jdev.mentoria.lojavirtual.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.dto.CepDTO;
import jdev.mentoria.lojavirtual.model.Endereco;
import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.repository.EnderecoRepository;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.service.PessoaUserService;
import jdev.mentoria.lojavirtual.util.ValidaCNPJ;
import jdev.mentoria.lojavirtual.util.ValidaCPF;

@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@ResponseBody
	@GetMapping("**/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) {
		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
	}
	
	@PostMapping(value = "**/salvarPj")
	@ResponseBody
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser nula.");
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existsByCnpj(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existsByinscEstadual(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava("Já existe Inscrição Estadual cadastrado com o número: " + pessoaJuridica.getInscEstadual());
		}
		
		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("CNPJ : " + pessoaJuridica.getCnpj() + " Está inválido.");
		}
		
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) { // percorrendo os endereços para consulta do cep
				populaCep(pessoaJuridica, p);
			}
		} else {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				Endereco enderecoTempo = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				
				if (!enderecoTempo.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
					populaCep(pessoaJuridica, p);
				}
				
			}
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
	
	@PostMapping(value = "**/salvarPf")
	@ResponseBody
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoriaJava {
		
		if (pessoaFisica == null) {
			throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser nula.");
		}
		
		if (pessoaFisica.getId() == null && pessoaRepository.existsByCpf(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoriaJava("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
		}
				
		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF : " + pessoaFisica.getCpf() + " Está inválido.");
		}
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
	}
	
	private void populaCep(PessoaJuridica pessoaJuridica, int index) {
        CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(index).getCep());
        pessoaJuridica.getEnderecos().get(index).setBairro(cepDTO.getBairro());
        pessoaJuridica.getEnderecos().get(index).setCidade(cepDTO.getLocalidade());
        pessoaJuridica.getEnderecos().get(index).setComplemento(cepDTO.getComplemento());
        pessoaJuridica.getEnderecos().get(index).setRuaLogra(cepDTO.getLogradouro());
        pessoaJuridica.getEnderecos().get(index).setUf(cepDTO.getUf());
    }	

}
