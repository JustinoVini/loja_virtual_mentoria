package jdev.mentoria.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.service.PessoaUserService;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Test
	public void testCadPessoa() {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("48266148880");
		pessoaJuridica.setNome("Primeiro teste");
		pessoaJuridica.setEmail("emailteste@teste.com");
		pessoaJuridica.setTelefone("123456789");
		pessoaJuridica.setInscEstadual("123456789");
		pessoaJuridica.setInscMunicipal("123456789");
		pessoaJuridica.setNomeFantasia("testew");
		pessoaJuridica.setRazaoSocial("testew");
		
		pessoaRepository.save(pessoaJuridica);
		
		/*PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setCpf("48266148880");
		pessoaFisica.setNome("Primeiro teste");
		pessoaFisica.setEmail("emailteste@teste.com");
		pessoaFisica.setTelefone("123456789");
		
		pessoaFisica.setEmpresa(pessoaFisica);*/
		
	}
	
}
