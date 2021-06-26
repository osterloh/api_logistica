CREATE TABLE ocorrencia (
	id serial,
	entrega_id bigint not null,
	descricao text not null,
	data_registro timestamp not null,
	primary key(id)
);

ALTER TABLE ocorrencia ADD CONSTRAINT fk_ocorrencia_entrega
FOREIGN KEY (entrega_id) REFERENCES entrega (id);