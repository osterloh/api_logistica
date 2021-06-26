package com.osterloh.logistica.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

	private Long id;
	private String nome;
	private UsuarioResumoDTO usuario;
	private String telefone;

}
