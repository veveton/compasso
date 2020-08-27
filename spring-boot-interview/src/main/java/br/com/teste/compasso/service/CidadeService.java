package br.com.teste.compasso.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.teste.compasso.model.Cidade;
import br.com.teste.compasso.repositorio.CidadeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	public ResponseEntity<?> save(@Valid Cidade item) {
		return new ResponseEntity<>(cidadeRepository.save(item), HttpStatus.CREATED);
	}

	public ResponseEntity<?> findByNameIgnoreCase(String nome) {
		List<Cidade> retorno = cidadeRepository.findByNameIgnoreCase(nome);
		return new ResponseEntity<>(retorno, (retorno.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	public ResponseEntity<?> findByEstado(String estado) {
		List<Cidade> retorno = cidadeRepository.findByEstado(estado);
		return new ResponseEntity<>(retorno, (retorno.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

}
