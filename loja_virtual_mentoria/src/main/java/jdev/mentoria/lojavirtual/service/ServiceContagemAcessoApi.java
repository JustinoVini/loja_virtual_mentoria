package jdev.mentoria.lojavirtual.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceContagemAcessoApi {
	
	private final JdbcTemplate jdbcTemplate;
	
	public ServiceContagemAcessoApi(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void atualizaAcessoEndpointPF() {
		jdbcTemplate.execute("begin; update tabela_acesso_endpoint set qtd_acesso_endpoint = qtd_acesso_endpoint + 1 where nome_endpoint = 'END-POINT-NOME-PESSOA-FISICA'; commit");
    }

}
