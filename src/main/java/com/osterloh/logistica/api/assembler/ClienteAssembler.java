package com.osterloh.logistica.api.assembler;

import com.osterloh.logistica.api.model.ClienteDTO;
import com.osterloh.logistica.api.model.ClienteUpdateDTO;
import com.osterloh.logistica.api.model.input.ClienteInput;
import com.osterloh.logistica.api.model.input.ClienteUpdateInput;
import com.osterloh.logistica.domain.model.Cliente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ClienteAssembler {

	private ModelMapper modelMapper;

	public ClienteDTO toModel(Cliente cliente){
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	public ClienteUpdateDTO toModelUpdate(Cliente cliente){
		return modelMapper.map(cliente, ClienteUpdateDTO.class);
	}

	public List<ClienteDTO> toCollectionModel(List<Cliente> clientes){
		return clientes.stream().map(this::toModel)
				.collect(Collectors.toList());
	}

	public Cliente toEntity(ClienteInput clienteInput){
		return modelMapper.map(clienteInput, Cliente.class);
	}

	public Cliente toEntity(ClienteUpdateInput clienteUpdateInput){
		return modelMapper.map(clienteUpdateInput, Cliente.class);
	}
}
