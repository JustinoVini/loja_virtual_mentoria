package jdev.mentoria.lojavirtual.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jdev.mentoria.lojavirtual.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);

	@Query("select u from Usuario u where u.id = ?1 or u.login = ?2")
	Usuario findUserByPessoa(Long id, String email);

	@Query(value = "SELECT constraint_name \n" + "FROM information_schema.constraint_column_usage\n"
			+ "WHERE table_name = 'usuarios_acesso'\n" + "AND column_name = 'acesso_id'\n"
			+ "AND constraint_name <> 'unique_acesso_user'", nativeQuery = true)
	String consultaConstraintAcesso();

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO usuarios_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))", nativeQuery = true)
	void insereAcessoUserPj(Long idUser);

}
