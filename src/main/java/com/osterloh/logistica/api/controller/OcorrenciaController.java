package com.osterloh.logistica.api.controller;

import com.osterloh.logistica.api.assembler.OcorrenciaAssembler;
import com.osterloh.logistica.api.model.OcorrenciaModel;
import com.osterloh.logistica.api.model.input.OcorrenciaInput;
import com.osterloh.logistica.domain.model.Entrega;
import com.osterloh.logistica.domain.model.Ocorrencia;
import com.osterloh.logistica.domain.service.BuscaEntregaService;
import com.osterloh.logistica.domain.service.RegistroOcorrenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias")
public class OcorrenciaController {

	private BuscaEntregaService buscaEntregaService;
	private RegistroOcorrenciaService registroOcorrenciaService;
	private OcorrenciaAssembler ocorrenciaAssembler;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaModel registrar(@PathVariable Long entregaId, @Valid @RequestBody OcorrenciaInput ocorrenciaInput){
		Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService
				.registrar(entregaId, ocorrenciaInput.getDescricao());

		return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);
	}

	@GetMapping
	public List<OcorrenciaModel> listar(@PathVariable Long entregaId){
		Entrega entrega = buscaEntregaService.buscar(entregaId);

		return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
	}
}
