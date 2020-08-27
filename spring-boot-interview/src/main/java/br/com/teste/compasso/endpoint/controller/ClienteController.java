package br.com.teste.compasso.endpoint.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.compasso.model.Cliente;
import br.com.teste.compasso.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("cliente")
@Api(value = "Controller Cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Cadastrar Cliente", response = Cliente.class)
	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Cliente item) {
		log.info("Salvando Cliente");
		return clienteService.save(item);
	}

	@ApiOperation(value = "Consultar cliente pelo Id", response = Cliente.class)
	@GetMapping(path = "findById/{id}")
	public ResponseEntity<?> getClienteById(@PathVariable("id") Long id) {
		log.info("Procurando Cliente by id");
		return clienteService.getClienteById(id);
	}

	@ApiOperation(value = "Consultar cliente pelo nome", response = Cliente.class)
	@GetMapping(path = "findByName/{name}")
	public ResponseEntity<?> getClientePorNome(@PathVariable("name") String nome) {
		log.info("Procurando Cliente by nome");
		return clienteService.findByNameIgnoreCase(nome);
	}

	@ApiOperation(value = "Remover cliente apartir do ID")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> removeClienteById(@PathVariable Long id) {
		log.info("Removendo Cliente pelo ID");
		return clienteService.removeClienteById(id);
	}

	@ApiOperation(value = "Remover cliente apartir de uma entidade Cliente")
	@DeleteMapping
	public ResponseEntity<?> removeClienteByEntity(@RequestBody Cliente cliente) {
		log.info("Removendo Clientes por Entidade");
		return clienteService.removeClienteByEntity(cliente);
	}

	@ApiOperation(value = "Alterar o nome do cliente", response = Cliente.class)
	@PutMapping
	public ResponseEntity<?> alterarNomeCliente(@RequestBody Cliente client) {
		log.info("Atualizando Nome do Cliente");
		return clienteService.alterarNomeCliente(client);
	}

	
}
