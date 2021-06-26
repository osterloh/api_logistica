CREATE TABLE usuario(
	id serial,
	email varchar(60) not null,
	senha varchar(60) not null,
	primary key (id)
);