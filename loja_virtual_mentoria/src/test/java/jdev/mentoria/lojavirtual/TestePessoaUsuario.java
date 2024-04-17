package jdev.mentoria.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import jdev.mentoria.lojavirtual.controller.PessoaController;
import jdev.mentoria.lojavirtual.enums.TipoEndereco;
import jdev.mentoria.lojavirtual.model.Endereco;
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
	
	@Test
	void testCadPessoaFisica() throws ExceptionMentoriaJava {
		PessoaJuridica pessoaJuridica = pessoaRepository.existsByCnpj("50327290000153");

		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("178.497.830-26");
		pessoaFisica.setNome("Maria");
		pessoaFisica.setEmail("tadeu@jdevtreinamento.com.br");
		pessoaFisica.setTelefone("9999999999");
		pessoaFisica.setEmpresa(pessoaJuridica);

		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("65656656");
		endereco1.setComplemento("Casa zcinaza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaFisica);
		endereco1.setRuaLogra("Av. São João sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		endereco1.setCidade("Curitiba");

		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Marana");
		endereco2.setCep("76767676");
		endereco2.setComplemento("Andar 4");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("555");
		endereco2.setPessoa(pessoaFisica);
		endereco2.setRuaLogra("Av. Maaringá");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("PR");
		endereco2.setCidade("Maringá");

		pessoaFisica.getEnderecos().add(endereco1);
		pessoaFisica.getEnderecos().add(endereco2);

		pessoaFisica = pessoaController.salvarPf(pessoaFisica).getBody();

		assertEquals(true, pessoaFisica.getId() > 0);

		for (Endereco endereco : pessoaFisica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}

		assertEquals(2, pessoaFisica.getEnderecos().size());
	}

	
}
