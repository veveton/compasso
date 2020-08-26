package br.com.teste.compasso.repositorio;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.teste.compasso.model.Cidade;

public interface CidadeRepository extends PagingAndSortingRepository<Cidade, Long> {
	List<Cidade> findByNameIgnoreCase(String nome);
	List<Cidade> findByEstado(String estado);
}