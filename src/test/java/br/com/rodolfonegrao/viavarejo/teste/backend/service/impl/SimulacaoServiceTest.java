package br.com.rodolfonegrao.viavarejo.teste.backend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.rodolfonegrao.viavarejo.teste.backend.exception.ServiceException;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.CondicaoPagamento;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.JurosDiarioSelic;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Parcela;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Produto;
import br.com.rodolfonegrao.viavarejo.teste.backend.service.SelicService;
import br.com.rodolfonegrao.viavarejo.teste.backend.service.SimulacaoService;

public class SimulacaoServiceTest {

	@InjectMocks
	private SimulacaoService simulacaoService = new SimulacaoServiceImpl();
	
	@Mock
	private SelicService selicService;
	
	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testeSimularQuantidadeParcelasESemJuros() {

		Produto produto = new Produto();
		produto.setCodigo(0);
		produto.setNome("Teste");
		produto.setValor(new BigDecimal("500.00"));

		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setQtdeParcelas(3);
		condicaoPagamento.setValorEntrada(new BigDecimal("100.00"));

		List<Parcela> listaParcelas = simulacaoService.calcularParcelas(produto, condicaoPagamento);

		Assert.assertEquals("Esperado 3 parcelas", 3, listaParcelas.size());
	}

	@Test(expected = ServiceException.class)
	public void testeSimularErroProdutoNull() {

		Produto produto = null;

		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setQtdeParcelas(3);
		condicaoPagamento.setValorEntrada(new BigDecimal("100.00"));

		simulacaoService.calcularParcelas(produto, condicaoPagamento);

	}
	
	@Test(expected = ServiceException.class)
	public void testeSimularErroCondicaoNull() {
		Produto produto = new Produto();
		produto.setCodigo(0);
		produto.setNome("Teste");
		produto.setValor(new BigDecimal("500.00"));
		
		CondicaoPagamento condicaoPagamento = null;

		simulacaoService.calcularParcelas(produto, condicaoPagamento);

	}
	
	@Test(expected = ServiceException.class)
	public void testeSimularErroProdutoValorZero() {
		Produto produto = new Produto();
		produto.setCodigo(0);
		produto.setNome("Teste");
		produto.setValor(new BigDecimal("0.00"));
		
		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setQtdeParcelas(3);
		condicaoPagamento.setValorEntrada(new BigDecimal("100.00"));

		simulacaoService.calcularParcelas(produto, condicaoPagamento);

	}
	
	@Test(expected = ServiceException.class)
	public void testeSimularErroCondicaoEntradaMaiorValorProduto() {
		Produto produto = new Produto();
		produto.setCodigo(0);
		produto.setNome("Teste");
		produto.setValor(new BigDecimal("10.00"));
		
		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setQtdeParcelas(3);
		condicaoPagamento.setValorEntrada(new BigDecimal("100.00"));

		simulacaoService.calcularParcelas(produto, condicaoPagamento);

	}
	
	@Test(expected = ServiceException.class)
	public void testeSimularErroCondicaoParcelaZero() {
		Produto produto = new Produto();
		produto.setCodigo(0);
		produto.setNome("Teste");
		produto.setValor(new BigDecimal("500.00"));
		
		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setQtdeParcelas(0);
		condicaoPagamento.setValorEntrada(new BigDecimal("100.00"));

		simulacaoService.calcularParcelas(produto, condicaoPagamento);

	}
	
	@Test
	public void testeSimularParcelasComJuros() {

		Produto produto = new Produto();
		produto.setCodigo(0);
		produto.setNome("Teste");
		produto.setValor(new BigDecimal("500.00"));

		CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
		condicaoPagamento.setQtdeParcelas(7);
		condicaoPagamento.setValorEntrada(new BigDecimal("100.00"));
		
		List<JurosDiarioSelic> listJurosDiarioSelic = new ArrayList<>();
		listJurosDiarioSelic.add(new JurosDiarioSelic("01/01/2019", new BigDecimal("0.05")));
		listJurosDiarioSelic.add(new JurosDiarioSelic("02/01/2019", new BigDecimal("0.04")));
		
		Mockito.when(selicService.buscarTaxaSelic(Mockito.anyInt())).thenReturn(listJurosDiarioSelic);
		
		List<Parcela> listaParcelas = simulacaoService.calcularParcelas(produto, condicaoPagamento);

		Parcela parcelaComJuros = listaParcelas.stream().filter(p -> p.getNumeroParcela() == 7).findFirst().get();
		
		Assert.assertEquals("Esperado 7 parcelas", 7, listaParcelas.size());
		Assert.assertEquals("Esperado o valor da parcela 7 de R$ 57.19", new BigDecimal("57.19"), parcelaComJuros.getValor());
		Assert.assertEquals("Esperado o valor do juros de 0.000900", new BigDecimal("0.000900"), parcelaComJuros.getTaxaJurosAoMes());
	}

}
