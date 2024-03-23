package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.Acesso;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long>{

	// @Query("select a from Acesso where upper(trim(a.descricao)) like %?1%");
	List<Acesso> findAcessoByDescricao(String descricao);
	
}
