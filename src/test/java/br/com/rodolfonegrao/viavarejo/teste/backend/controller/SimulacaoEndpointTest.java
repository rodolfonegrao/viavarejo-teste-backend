package br.com.rodolfonegrao.viavarejo.teste.backend.controller;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rodolfonegrao.viavarejo.teste.backend.Application;
import br.com.rodolfonegrao.viavarejo.teste.backend.controller.request.SimulacaoRequest;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.CondicaoPagamento;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Parcela;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class SimulacaoEndpointTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	private int port;
	
	@Test
	public void testeSimularQuantidadeParcelasESemJuros() {
		Produto produto = new Produto();
		produto.setCodigo(0);
		produto.setNome("Teste");
		produto.setValor(new BigDecimal("500.00"));

		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setQtdeParcelas(3);
		condicaoPagamento.setValorEntrada(new BigDecimal("100.00"));
		
		SimulacaoRequest simulacaoRequest = new SimulacaoRequest();
		simulacaoRequest.setProduto(produto);
		simulacaoRequest.setCondicaoPagamento(condicaoPagamento);
		
		HttpEntity<SimulacaoRequest> requestEntity = new HttpEntity<SimulacaoRequest>(simulacaoRequest, new HttpHeaders());

		ResponseEntity<List<Parcela>> response = testRestTemplate.exchange(
				String.format("http://localhost:%s/simulacao", port), 
				HttpMethod.POST, 
				requestEntity, 
				new ParameterizedTypeReference<List<Parcela>>() {});
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
