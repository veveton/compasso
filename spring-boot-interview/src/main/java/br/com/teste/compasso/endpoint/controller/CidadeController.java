package br.com.teste.compasso.endpoint.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.compasso.model.Cidade;
import br.com.teste.compasso.repositorio.CidadeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("cidade")
@Api(value = "Controller Cidade")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "Cadastrar Cidade", response = Cidade.class)
	public ResponseEntity<?> save(@Valid @RequestBody Cidade item) {
		log.info("Save Cidade");
		return new ResponseEntity<>(cidadeRepository.save(item), HttpStatus.CREATED);
	}

	@GetMapping(path = "findByName/{nome}")
	@ApiOperation(value = "Consultar cidade pelo nome")
	public ResponseEntity<?> getCidadePorNome(@PathVariable("nome") String nome) {
		log.info("Get cidade by nome");
		List<Cidade> retorno = cidadeRepository.findByNameIgnoreCase(nome);
		return new ResponseEntity<>(retorno, (retorno.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@GetMapping(path = "findByEstado/{estado}")
	@ApiOperation(value = "Consultar cidade pelo estado", response = Cidade.class)
	public ResponseEntity<?> getCidadePorEstado(@PathVariable("estado") String estado) {
		log.info("Get cidade by estado");
		List<Cidade> retorno = cidadeRepository.findByEstado(estado);
		return new ResponseEntity<>(retorno, (retorno.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

}
