package br.com.teste.compasso;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import br.com.teste.compasso.repositorio.CidadeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CidadeEndpointTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@MockBean
	private CidadeRepository cidadeRepository;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void CadastrarCidadeRestTest201() {
		Cidade cidade = new Cidade(1L, "Surubim", "Pernambuco");
		BDDMockito.when(cidadeRepository.save(cidade)).thenReturn(cidade);
		ResponseEntity<Cidade> response = restTemplate.postForEntity("/cidade/", cidade, Cidade.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		Assertions.assertThat(response.getBody().getId()).isNotNull();

	}

	@Test
	public void CadastrarCidadeMockMvcTest201() throws Exception {
		Cidade cidade = new Cidade(1L, "Surubim", "Pernambuco");
		mockMvc.perform(
				post("/cidade/").contentType("application/json").content(objectMapper.writeValueAsString(cidade)))
				.andExpect(status().isCreated());
	}

	@Test
	public void findByNameIgnoreCaseTest200() throws Exception {
		List<Cidade> cidade = java.util.Arrays.asList(new Cidade(1L, "Surubim", "Pernambuco"));
		BDDMockito.when(cidadeRepository.findByNameIgnoreCase("Surubim")).thenReturn(cidade);
		mockMvc.perform(MockMvcRequestBuilders.get("/cidade/findByName/{nome}", "Surubim")
				.contentType("application/json").accept("application/json")).andExpect(status().isOk());
	}

	@Test
	public void findByNameIgnoreCaseTest404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cidade/findByName/{nome}", "Pernambuco")
				.contentType("application/json").accept("application/json")).andExpect(status().isNotFound());
	}

	@Test
	public void findByEstadoTest200() throws Exception {
		List<Cidade> cidade = java.util.Arrays.asList(new Cidade(1L, "Surubim", "Pernambuco"));
		BDDMockito.when(cidadeRepository.findByEstado("Pernambuco")).thenReturn(cidade);
		mockMvc.perform(MockMvcRequestBuilders.get("/cidade/findByEstado/{estado}", "Pernambuco")
				.contentType("application/json").accept("application/json")).andExpect(status().isOk());
	}

	@Test
	public void findByEstadoTest404() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cidade/findByEstado/{estado}", "Pernambuco")
				.contentType("application/json").accept("application/json")).andExpect(status().isNotFound());
	}

}
