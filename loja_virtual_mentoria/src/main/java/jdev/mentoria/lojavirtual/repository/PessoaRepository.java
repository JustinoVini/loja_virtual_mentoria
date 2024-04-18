package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.PessoaJuridica;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaJuridica, Long> {
	
	@Query(value = "SELECT pj FROM PessoaJuridica pj WHERE upper(trim(pj.nome)) LIKE %?1%")
    List<PessoaJuridica> pesquisaPorNomePJ(String nome);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
    PessoaJuridica existeCnpjCadastrado(String cnpj);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.cnpj = ?1")
    List<PessoaJuridica> existeCnpjCadastradoList(String cnpj);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscEstadual = ?1")
    PessoaJuridica existeInscEstadualCadastrado(String inscEstadual);

    @Query(value = "SELECT pj FROM PessoaJuridica pj WHERE pj.inscEstadual = ?1")
    List<PessoaJuridica> existeInscEstadualCadastradoList(String inscEstadual);
	
}
