package com.osterloh.logistica.api.assembler;

import com.osterloh.logistica.api.model.EntregaModel;
import com.osterloh.logistica.api.model.input.EntregaInput;
import com.osterloh.logistica.domain.model.Entrega;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EntregaAssembler {

	private ModelMapper modelMapper;

	public EntregaModel toModel(Entrega entrega){
		return modelMapper.map(entrega, EntregaModel.class);
	}

	public List<EntregaModel> toCollectionModel(List<Entrega> entregas){
		return entregas.stream().map(this::toModel)
				.collect(Collectors.toList());
	}

	public Entrega toEntity(EntregaInput entregaInput){
		return modelMapper.map(entregaInput, Entrega.class);
	}

}
