CREATE TABLE public.tabela_acesso_endpoint
(
  nome_endpoint character varying,
  qtd_acesso_endpoint integer
);

INSERT INTO public.tabela_acesso_end_potin (nome_endpoint, qtd_acesso_endpoint)
VALUES ('END-POINT-NOME-PESSOA-FISICA', 0);

ALTER TABLE tabela_acesso_endpoint ADD CONSTRAINT nome_endpoint_unique UNIQUE (nome_endpoint);