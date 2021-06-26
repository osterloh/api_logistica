package com.osterloh.logistica.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateDTO {

	private Long id;
	private String nome;
	private String telefone;

}
