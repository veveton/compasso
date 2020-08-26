package br.com.teste.compasso.endpoint.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.com.teste.compasso.exceptions.ResourceNotFoundException;
import br.com.teste.compasso.model.Cliente;
import br.com.teste.compasso.repositorio.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("cliente")
@Api(value = "Controller Cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@ApiOperation(value = "Cadastrar Cliente", response = Cliente.class)
	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Cliente item) {
		log.info("Salvando Cliente");
		return new ResponseEntity<>(clienteRepository.save(item), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Consultar cliente pelo Id", response = Cliente.class)
	@GetMapping(path = "findById/{id}")
	public ResponseEntity<?> getClienteById(@PathVariable("id") Long id) {
		log.info("Procurando Cliente by id");
		try {
			verifyIfItemExists(id);
			return new ResponseEntity<>(clienteRepository.findById(id), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Consultar cliente pelo nome", response = Cliente.class)
	@GetMapping(path = "findByName/{name}")
	public ResponseEntity<?> getClientePorNome(@PathVariable("name") String nome) {
		log.info("Procurando Cliente by nome");
		List<Cliente> retorno = clienteRepository.findByNameIgnoreCase(nome);
		return new ResponseEntity<>(retorno, (retorno.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@ApiOperation(value = "Remover cliente apartir do ID")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> removeClienteById(@PathVariable Long id) {
		log.info("Removendo Cliente pelo ID");
		try {
			verifyIfItemExists(id);
			clienteRepository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Remover cliente apartir de uma entidade Cliente")
	@DeleteMapping
	public ResponseEntity<?> removeClienteByEntity(@RequestBody Cliente cliente) {
		log.info("Removendo Clientes por Entidade");
		clienteRepository.delete(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Alterar o nome do cliente", response = Cliente.class)
	@PutMapping
	public ResponseEntity<?> alterarNomeCliente(@RequestBody Cliente client) {
		log.info("Atualizando Nome do Cliente");
		try {
			verifyIfItemExists(client.getId());

			// Se existir busca cliente no repositório para somente setar o novo "Nome" que
			// veio na requisição não modificando outros atributos

			Cliente cliente = clienteRepository.findById(client.getId()).get();
			cliente.setName(client.getName());

			return new ResponseEntity<>(clienteRepository.save(cliente), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private void verifyIfItemExists(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new ResourceNotFoundException("ResourceNotFoundException");
		}
	}
}
