package jdev.mentoria.lojavirtual.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.ContaPagar;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long>{

	@Query("select a from ContaPagar a where upper(trim(a.descricao)) like %?1%")
	List<ContaPagar> buscarContaPorDescricao(String desc);

	@Query("SELECT cp FROM ContaPagar cp WHERE cp.pessoa.id = ?1")
	List<ContaPagar> buscarContaPorPessoa(Long idPessoa);

	@Query("SELECT cp FROM ContaPagar cp WHERE cp.pessoaFornecedor.id = ?1")
	List<ContaPagar> buscarContaPorFornecedor(Long idFornecedor);

	@Query("SELECT cp FROM ContaPagar cp WHERE cp.empresa.id = ?1")
	List<ContaPagar> buscarContaPorEmpresa(Long idEmpresa);
	
}
