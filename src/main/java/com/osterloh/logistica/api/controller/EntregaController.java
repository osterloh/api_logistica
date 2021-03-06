package com.osterloh.logistica.api.controller;

import com.osterloh.logistica.api.assembler.EntregaAssembler;
import com.osterloh.logistica.api.model.EntregaModel;
import com.osterloh.logistica.api.model.input.EntregaInput;
import com.osterloh.logistica.domain.model.Entrega;
import com.osterloh.logistica.domain.repository.EntregaRepository;
import com.osterloh.logistica.domain.service.FinalizacaoEntregaService;
import com.osterloh.logistica.domain.service.SolicitacaoEntregaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private EntregaRepository entregaRepository;
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaAssembler entregaAssembler;
	private FinalizacaoEntregaService finalizacaoEntregaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput){
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(novaEntrega);

		return entregaAssembler.toModel(entregaSolicitada);
	}

	@GetMapping
	public List<EntregaModel> listar(){
		return entregaAssembler.toCollectionModel(entregaRepository.findAll());
	}

	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId)
				.map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId){
		finalizacaoEntregaService.finalizar(entregaId);
	}
}
