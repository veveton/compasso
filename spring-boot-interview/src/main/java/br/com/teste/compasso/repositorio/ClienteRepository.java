package br.com.teste.compasso.repositorio;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.teste.compasso.model.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {
	List<Cliente> findByNameIgnoreCase(String nome);
}