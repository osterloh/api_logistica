package com.osterloh.logistica.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClienteInput {

	@NotBlank
	private String nome;

	@NotNull
	private UsuarioInput usuario;

	@NotBlank
	private String telefone;

}
