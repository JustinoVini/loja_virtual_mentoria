package jdev.mentoria.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jdev.mentoria.lojavirtual.model.Usuario;

/*Estabelece o nosso gerenciador de token*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	/*Configurando o gerenciador de authenticacao*/
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		/*Obriga auth da url*/
		super(new AntPathRequestMatcher(url));
		
		/*Gerenciador de auth*/
		setAuthenticationManager(authenticationManager);
	}

	/*Retorna o usuario ao processar autenticacao*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		/*esta pegando o token para validar*/
		Usuario user = new ObjectMapper()
				.readValue(request.getInputStream(), Usuario.class);
		
		
		/*Retorna o usuário login, senha e acessos*/
		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		try {
			new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*Adiciona um filtro a requisições rejeitadas*/
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		if (failed instanceof BadCredentialsException) {
			response.getWriter().write("Usuário e Senha não encontrado");
		} else {
			response.getWriter().write("Falha ao logar: " + failed.getMessage());
		}
		
		// super.unsuccessfulAuthentication(request, response, failed);
	}

}
