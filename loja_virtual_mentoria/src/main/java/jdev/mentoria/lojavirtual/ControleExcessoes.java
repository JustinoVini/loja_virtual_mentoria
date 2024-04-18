package jdev.mentoria.lojavirtual;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jdev.mentoria.lojavirtual.dto.ObjetoErroDTO;
import jdev.mentoria.lojavirtual.service.ServiceSendEmail;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcessoes extends ResponseEntityExceptionHandler {

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	@ExceptionHandler(ExceptionMentoriaJava.class)
	public ResponseEntity<Object> handleCustomException(ExceptionMentoriaJava ex) {

		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

		objetoErroDTO.setError(ex.getMessage());
		objetoErroDTO.setCode(HttpStatus.OK.toString());

		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.OK);
	}

	/* Captura excessões do sistema */
	@ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

		String msg = "";

		if (ex instanceof MethodArgumentNotValidException) { // se for uma instancia dessa exception joga
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		} else if (ex instanceof HttpMessageNotReadableException) {

			msg = "Não está sendo enviado dados para o body (Corpo da requisição)";

		} else {
			msg = ex.getMessage();
		}

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(status.value() + "==>" + status.getReasonPhrase());

		ex.printStackTrace();
		try {
			serviceSendEmail.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex),
					"viniciusemailjavaweb@gmail.com");
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/* Captura erro na parte de banco de dados */
	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

		String msg = "";

		if (ex instanceof DataIntegrityViolationException) {
			msg = "Erro de integridade do banco: "
					+ ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
		} else if (ex instanceof ConstraintViolationException) {
			msg = "Erro de chave estrangeira: "
					+ ((ConstraintViolationException) ex).getCause().getCause().getMessage();
		} else if (ex instanceof SQLException) {
			msg = "Erro de sql do banco: " + ((SQLException) ex).getCause().getCause().getMessage();
		} else {
			msg = ex.getMessage();
		}

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString()); // Erro interno do sistema, passe default

		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
