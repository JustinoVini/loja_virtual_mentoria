package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.util.ValidaCNPJ;
import jdev.mentoria.lojavirtual.util.ValidaCPF;

public class TesteCPFCNPJ {

	public static void main(String[] args) {
		boolean isCnpj = ValidaCNPJ.isCNPJ("71.530.067/0001-86");

		System.out.println("Cnpj Valido: " + isCnpj);

		boolean isCpf = ValidaCPF.isCPF("195.641.380-43");

		System.out.println("Cpf Valido: " + isCpf);
	}

}
