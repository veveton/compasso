package br.com.teste.compasso.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.teste.compasso.exceptions.ResourceNotFoundException;
import br.com.teste.compasso.model.Cliente;
import br.com.teste.compasso.repositorio.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public ResponseEntity<?> save(@Valid Cliente item) {
		return new ResponseEntity<>(clienteRepository.save(item), HttpStatus.CREATED);
	}

	public ResponseEntity<?> findByNameIgnoreCase(String nome) {
		List<Cliente> retorno = clienteRepository.findByNameIgnoreCase(nome);
		return new ResponseEntity<>(retorno, (retorno.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	public ResponseEntity<?> getClienteById(Long id) {
		try {
			verifyIfItemExists(id);
			return new ResponseEntity<>(clienteRepository.findById(id), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<?> removeClienteById(Long id) {
		try {
			verifyIfItemExists(id);
			clienteRepository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> removeClienteByEntity(Cliente cliente) {
		clienteRepository.delete(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> alterarNomeCliente(@RequestBody Cliente client) {
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
