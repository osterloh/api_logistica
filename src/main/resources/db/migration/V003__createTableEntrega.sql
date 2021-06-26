CREATE TABLE entrega(
	id serial,
	cliente_id int not null,
	taxa decimal(10,2) not null,
	status varchar(60) not null,
	data_pedido timestamp not null,
	data_finalizacao timestamp,

	destinatario_nome varchar(60) not null,
	destinatario_logradouro varchar(255) not null,
	destinatario_numero varchar(30) not null,
	destinatario_complemento varchar(60) not null,
	destinatario_bairro varchar(30) not null,

	primary key (id)
);

ALTER TABLE entrega ADD CONSTRAINT fk_entrega_cliente
FOREIGN KEY (cliente_id) REFERENCES cliente (id)