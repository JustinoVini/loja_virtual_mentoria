package jdev.mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import jdev.mentoria.lojavirtual.controller.PessoaController;
import jdev.mentoria.lojavirtual.enums.TipoEndereco;
import jdev.mentoria.lojavirtual.model.Endereco;
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
	
	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void testCadPessoa() throws ExceptionMentoriaJava {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Segundo Teste");
		pessoaJuridica.setEmail("testejunitpj@teste.com");
		pessoaJuridica.setTelefone("123456789");
		pessoaJuridica.setInscEstadual("123456789");
		pessoaJuridica.setInscMunicipal("123456789");
		pessoaJuridica.setNomeFantasia("testew");
		pessoaJuridica.setRazaoSocial("testew");
		
		Endereco end1 = new Endereco();
		
		end1.setBairro("Jd Dias");
		end1.setCep("65656565");
		end1.setComplemento("Casa branca");
		end1.setCidade("Primeira Cidade");
		end1.setEmpresa(pessoaJuridica);
		end1.setNumero("395");
		end1.setPessoa(pessoaJuridica);
		end1.setRuaLogra("Av São João sexto");
		end1.setTipoEndereco(TipoEndereco.COBRANCA);
		end1.setUf("SP");
		
		Endereco end2 = new Endereco();
		end2.setBairro("Jd Bandeiras");
		end2.setCep("65656565");
		end2.setComplemento("Casa cinza");
		end2.setCidade("Segunda Cidade");
		end2.setEmpresa(pessoaJuridica);
		end2.setNumero("395");
		end2.setPessoa(pessoaJuridica);
		end2.setRuaLogra("Av São João setimo");
		end2.setTipoEndereco(TipoEndereco.ENTREGA);
		end2.setUf("SP");
		
		pessoaJuridica.getEnderecos().add(end1);
		pessoaJuridica.getEnderecos().add(end2);
		
		pessoaController.salvarPj(pessoaJuridica);
		
		assertEquals(true, pessoaJuridica.getId() > 0);
		
		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());
				
	}
	
}
