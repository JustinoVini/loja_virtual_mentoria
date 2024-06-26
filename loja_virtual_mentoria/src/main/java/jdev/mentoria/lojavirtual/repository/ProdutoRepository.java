package jdev.mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query(value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1))", nativeQuery = true)
	public boolean existeProduto(String nome);
	
	@Query(value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1)) and empresa_id = ?2", nativeQuery = true)
	public boolean existeProduto(String nome, Long idEmpresa);

	@Query("select c from Produto c where upper(trim(c.nome)) like %?1%")
	List<Produto> buscarProdutoNome(String nome);
	
	 @Query("select p from Produto p where upper(trim(p.nome)) like %?1% and p.empresa.id = ?2")
	List<Produto> buscarProdutoNome(String nome, Long idEmpresa);
	
}
