package com.osterloh.logistica.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.osterloh.logistica.domain.ValidationGroups;
import com.osterloh.logistica.domain.exception.NegocioException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id")	//default - nao necessita informar caso seja igual classe e id
	private Cliente cliente;

	@Valid
	@NotNull
	@Embedded	//abstrair os dados para outra classe, porém mapeando para a mesma tabela entrega
	private Destinatario destinatario;

	@NotNull
	private BigDecimal taxa;

	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
	private List<Ocorrencia> ocorrencias = new ArrayList<>();

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private StatusEntrega status;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime dataPedido;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(LocalDateTime.now());
		ocorrencia.setEntrega(this);

		this.getOcorrencias().add(ocorrencia);

		return  ocorrencia;
	}

	public void finalizar() {
		if(naoPodeSerFinalizada()){
			throw new NegocioException("Entrega não pode ser finalizada.");
		}

		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(LocalDateTime.now());
	}

	public boolean podeSerFinalizada(){
		return StatusEntrega.PENDENTE.equals(getStatus());
	}

	public boolean naoPodeSerFinalizada(){
		return !podeSerFinalizada();
	}
}
