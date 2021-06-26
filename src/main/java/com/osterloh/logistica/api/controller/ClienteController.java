package com.osterloh.logistica.api.controller;
import com.osterloh.logistica.api.assembler.ClienteAssembler;
import com.osterloh.logistica.api.model.ClienteDTO;
import com.osterloh.logistica.api.model.ClienteUpdateDTO;
import com.osterloh.logistica.api.model.input.ClienteInput;
import com.osterloh.logistica.api.model.input.ClienteUpdateInput;
import com.osterloh.logistica.domain.exception.EntidadeNaoEncontradaException;
import com.osterloh.logistica.domain.model.Cliente;
import com.osterloh.logistica.domain.repository.ClienteRepository;
import com.osterloh.logistica.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;
	private ClienteAssembler clienteAssembler;

	@GetMapping
	public List<ClienteDTO> listar(){
		return clienteAssembler.toCollectionModel(clienteRepository.findAll());
	}

	@GetMapping("cliente-nome")
	public List<ClienteDTO> listarNomes(){
		return clienteAssembler.toCollectionModel(clienteRepository.findByNome("Johnatan"));
	}

	@GetMapping("clientes-nome")
	public List<ClienteDTO> listarNome(){
		return clienteAssembler.toCollectionModel(clienteRepository.findByNomeContaining("yar"));
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<ClienteDTO> buscar(@PathVariable Long clienteId){
		return clienteRepository.findById(clienteId)
				.map(cliente -> ResponseEntity.ok(clienteAssembler.toModel(cliente)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO adicionar(@Valid @RequestBody ClienteInput clienteInput){
		Cliente clienteNovo = clienteAssembler.toEntity(clienteInput);
		clienteNovo.getUsuario().setSenha(new BCryptPasswordEncoder().encode(clienteInput.getUsuario().getSenha()));
		Cliente cliente = catalogoClienteService.salvar(clienteNovo);

		return clienteAssembler.toModel(cliente);
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<ClienteUpdateDTO> atualizar(@Valid @PathVariable Long clienteId, @RequestBody ClienteUpdateInput clienteUpdateInput){
		if(!clienteRepository.existsById(clienteId)){
			return ResponseEntity.notFound().build();
		}

		Cliente cliente = clienteAssembler.toEntity(clienteUpdateInput);

		cliente.setId(clienteId);
		cliente.setUsuario(clienteRepository.findById(clienteId)
				.map(cliente1 -> cliente1.getUsuario())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario não encontrado.")));
		cliente = catalogoClienteService.salvar(cliente);

		return ResponseEntity.ok(clienteAssembler.toModelUpdate(cliente));
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)){
			return ResponseEntity.notFound().build();
		}

		catalogoClienteService.excluir(clienteId);

		return ResponseEntity.noContent().build();
	}

}
