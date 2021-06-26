package com.osterloh.logistica.domain.service;

import com.osterloh.logistica.domain.exception.NegocioException;
import com.osterloh.logistica.domain.model.Entrega;
import com.osterloh.logistica.domain.model.Ocorrencia;
import com.osterloh.logistica.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistroOcorrenciaService {

	private BuscaEntregaService buscaEntregaService;

	@Transactional
	public Ocorrencia registrar(Long entregaId, String descricao){
		Entrega entrega = buscaEntregaService.buscar(entregaId);

		return  entrega.adicionarOcorrencia(descricao);
	}

}
