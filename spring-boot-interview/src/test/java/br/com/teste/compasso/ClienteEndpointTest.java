package br.com.teste.compasso;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.teste.compasso.model.Cidade;
import br.com.teste.compasso.model.Cliente;
import br.com.teste.compasso.repositorio.ClienteRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClienteEndpointTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@MockBean
	private ClienteRepository clienteRepository;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void CadastrarClienteRestTest201() {

		Cliente cliente = new Cliente(1L, "Everton", "Masculino", new Date(System.currentTimeMillis()), 20,
				new Cidade(1L, "Surubim", "Pernambuco"));
		BDDMockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		ResponseEntity<Cliente> response = restTemplate.postForEntity("/cliente/", cliente, Cliente.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		Assertions.assertThat(response.getBody().getId()).isNotNull();

	}

	@Test
	public void CadastrarClienteMockMvcTest201() throws Exception {
		Cliente cliente = new Cliente(1L, "Everton", "Masculino", new Date(System.currentTimeMillis()), 20,
				new Cidade(1L, "Surubim", "Pernambuco"));
		mockMvc.perform(
				post("/cliente/").contentType("application/json").content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isCreated());
	}

	@Test
	public void findByNameIgnoreCaseTest200() throws Exception {
		List<Cliente> cliente = java.util.Arrays.asList(new Cliente(1L, "Everton", "Masculino",
				new Date(System.currentTimeMillis()), 20, new Cidade(1L, "Surubim", "Pernambuco")));
		BDDMockito.when(clienteRepository.findByNameIgnoreCase("Everton")).thenReturn(cliente);
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/findByName/{nome}", "Everton")
				.contentType("application/json").accept("application/json")).andExpect(status().isOk());
	}

	@Test
	public void findByNameIgnoreCaseTest404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/findByName/{nome}", "Pernambuco")
				.contentType("application/json").accept("application/json")).andExpect(status().isNotFound());
	}

	@Test
	public void findByIdTest200() throws Exception {
		Optional<Cliente> cliente = Optional.of(new Cliente(1L, "Everton", "Masculino",
				new Date(System.currentTimeMillis()), 20, new Cidade(1L, "Surubim", "Pernambuco")));
		BDDMockito.when(clienteRepository.findById(1L)).thenReturn(cliente);
		BDDMockito.when(clienteRepository.existsById(1L)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/findById/{id}", 1).contentType("application/json")
				.accept("application/json")).andExpect(status().isOk());
	}

	@Test
	public void findByIdTest404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cliente/findById/{id}", 1).contentType("application/json")
				.accept("application/json")).andExpect(status().isNotFound());
	}

	@Test
	public void RemoverByIdTest() throws Exception {
		BDDMockito.doNothing().when(clienteRepository).deleteById(1L);
		mockMvc.perform(MockMvcRequestBuilders.delete("/cliente/{id}", 1).contentType("application/json")
				.accept("application/json")).andExpect(status().isOk());
	}

	@Test
	public void updateEmployeeAPI() throws Exception {
		Optional<Cliente> cliente = Optional.of(new Cliente(1L, "Everton", "Masculino",
				new Date(System.currentTimeMillis()), 20, new Cidade(1L, "Surubim", "Pernambuco")));
		BDDMockito.when(clienteRepository.findById(1L)).thenReturn(cliente);
		BDDMockito.when(clienteRepository.existsById(1L)).thenReturn(true);
		BDDMockito.when(clienteRepository.save(cliente.get())).thenReturn(cliente.get());

		mockMvc.perform(MockMvcRequestBuilders.put("/cliente/")
				.content(objectMapper.writeValueAsString(new Cliente(1L, "Everton Alterado", "Masculino",
						new Date(System.currentTimeMillis()), 20, new Cidade(1L, "Surubim", "Pernambuco"))))
				.contentType("application/json").accept("application/json")).andExpect(status().isOk());
	}

}