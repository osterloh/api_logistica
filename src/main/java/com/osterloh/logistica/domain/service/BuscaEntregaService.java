package com.osterloh.logistica.domain.service;

import com.osterloh.logistica.domain.exception.EntidadeNaoEncontradaException;
import com.osterloh.logistica.domain.exception.NegocioException;
import com.osterloh.logistica.domain.model.Entrega;
import com.osterloh.logistica.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BuscaEntregaService {

	private EntregaRepository entregaRepository;

	public Entrega buscar(Long entregaId){
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada!"));
	}
}
