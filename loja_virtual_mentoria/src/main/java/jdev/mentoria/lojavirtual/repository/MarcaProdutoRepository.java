package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.MarcaProduto;

@Repository
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long>{

	@Query("select a from MarcaProduto a where upper(trim(a.nomeDesc)) like %?1%")
	List<MarcaProduto> buscaMarcaDesc(String desc);
	
}
