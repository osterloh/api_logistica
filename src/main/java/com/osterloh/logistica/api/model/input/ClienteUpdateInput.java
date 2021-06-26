package com.osterloh.logistica.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ClienteUpdateInput {

	@NotBlank
	private String nome;

	@NotBlank
	private String telefone;

}
