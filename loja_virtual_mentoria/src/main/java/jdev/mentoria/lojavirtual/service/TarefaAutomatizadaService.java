package jdev.mentoria.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;

@Service
public class TarefaAutomatizadaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;

	// @Scheduled(initialDelay = 2000, fixedDelay = 86400000) /*Roda a cada 24h*/
	@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") /*Roda todo dia as 11h manhã*/
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		/*Pega lista de usuarios*/
		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
		
		/*Percorre os encontrados*/
		for (Usuario usuario : usuarios) {
			
			/*Cria uma mensagem*/
			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
			msg.append("Está na hora de trocar sua senha, já passou de 90 dias de validade").append("<br/>");
			msg.append("Troque sua senha da Loja Virtual");
			
			/*Envia a mensagem*/
			serviceSendEmail.enviarEmailHtml("Troca de Senha", msg.toString(), usuario.getLogin());
			
			/*Faz com que não envie mensagem a cada milesimo, mas sim a cada 5 segundos caso forem muitos*/
			Thread.sleep(5000);
		}
		
	}
	
}
