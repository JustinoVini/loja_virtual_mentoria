package jdev.mentoria.lojavirtual.util;

import java.util.Random;

public class SenhaGenerator {

	// gerador de senha
	public static String gerarSenhaAleatoria(int tamanho) {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder senha = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < tamanho; i++) {
			int indice = random.nextInt(caracteres.length());
			senha.append(caracteres.charAt(indice));
		}

		return senha.toString();
	}

}
