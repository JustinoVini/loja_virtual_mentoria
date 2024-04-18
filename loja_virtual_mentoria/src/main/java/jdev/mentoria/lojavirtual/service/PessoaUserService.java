package jdev.mentoria.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jdev.mentoria.lojavirtual.dto.CepDTO;
import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.PessoaFisicaRepository;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;
import jdev.mentoria.lojavirtual.util.SenhaGenerator;

@Service
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

		// pessoaJuridica = pessoaRepository.save(pessoaJuridica);

		for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}

		pessoaJuridica = pessoaRepository.save(pessoaJuridica);

		Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

		if (usuarioPj == null) {

			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit; ");
			}

			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(pessoaJuridica);
			usuarioPj.setPessoa(pessoaJuridica);
			usuarioPj.setLogin(pessoaJuridica.getEmail());

			String senha = SenhaGenerator.gerarSenhaAleatoria(6);
			String senhaCript = new BCryptPasswordEncoder().encode(senha);

			usuarioPj.setSenha(senhaCript);

			usuarioPj = usuarioRepository.save(usuarioPj);
			
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");

			StringBuilder mensagemHtml = new StringBuilder();

			mensagemHtml
					.append("<b>Segue abaixo seus dados de acesso Pessoa Jurídica para a loja virtual:</b><br/><br/>");
			mensagemHtml.append("<b>Login:</b> " + pessoaJuridica.getEmail() + "<br/>");
			mensagemHtml.append("<b>Senha:</b> ").append(senha).append("<br/><br/>");
			mensagemHtml.append("<b>Muito obrigado!</b>");

			/* Fazer o envio do email */
			try {
				serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", mensagemHtml.toString(),
						pessoaJuridica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return pessoaJuridica;
	}

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {

		//pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

		for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
			pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
			//pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);
		}

		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

		Usuario usuarioPf = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

		if (usuarioPf == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}
			usuarioPf = new Usuario();
			usuarioPf.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPf.setEmpresa(pessoaFisica.getEmpresa());
			usuarioPf.setPessoa(pessoaFisica);
			usuarioPf.setLogin(pessoaFisica.getEmail());

			String senha = SenhaGenerator.gerarSenhaAleatoria(6);
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);

			usuarioPf.setSenha(senhaCriptografada);
			usuarioPf = usuarioRepository.save(usuarioPf);

			usuarioRepository.insereAcessoUser(usuarioPf.getId());

			// Envio e-mail login e senha
			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso Pessoa Física para a loja virtual:</b><br/><br/>");
			mensagemHtml.append("<b>Login:</b> " + pessoaFisica.getEmail() + "<br/>");
			mensagemHtml.append("<b>Senha:</b> ").append(senha).append("<br/><br/>");
			mensagemHtml.append("<b>Muito obrigado!</b>");
			try {
				serviceSendEmail.enviarEmailHtml(
					"Acesso gerado para Loja Virtual",
					mensagemHtml.toString(),
					pessoaFisica.getEmail()
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return pessoaFisica;
	}
	
	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep +  "/json/", CepDTO.class).getBody();
	}

}
