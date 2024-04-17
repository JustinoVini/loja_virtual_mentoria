package jdev.mentoria.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.PessoaJuridica;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {

	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	PessoaJuridica existsByCnpj(String cnpj);
	
	@Query(value = "select pj from PessoaJuridica pj where pj.inscEstadual = ?1")
	PessoaJuridica existsByinscEstadual(String inscEstadual);
	
}
