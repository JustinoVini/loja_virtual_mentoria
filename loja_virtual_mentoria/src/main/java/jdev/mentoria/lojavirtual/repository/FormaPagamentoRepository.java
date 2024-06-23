package jdev.mentoria.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.FormaPagamento;
import jdev.mentoria.lojavirtual.model.NotaFiscalVenda;

@Repository
@Transactional
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

}
