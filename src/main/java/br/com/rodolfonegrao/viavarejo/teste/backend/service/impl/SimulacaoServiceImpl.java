package br.com.rodolfonegrao.viavarejo.teste.backend.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfonegrao.viavarejo.teste.backend.exception.ServiceException;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.CondicaoPagamento;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.JurosDiarioSelic;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Parcela;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Produto;
import br.com.rodolfonegrao.viavarejo.teste.backend.service.SelicService;
import br.com.rodolfonegrao.viavarejo.teste.backend.service.SimulacaoService;

@Service
public class SimulacaoServiceImpl implements SimulacaoService {

	@Autowired
	private SelicService selicService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.rodolfonegrao.viavarejo.teste.backend.service.impl.
	 * SimulacaoService#calcularParcelas(br.com.rodolfonegrao.viavarejo.teste.
	 * backend.model.Produto,
	 * br.com.rodolfonegrao.viavarejo.teste.backend.model.CondicaoPagamento)
	 */
	@Override
	public List<Parcela> calcularParcelas(Produto produto, CondicaoPagamento condicaoPagamento) {

		validacao(produto, condicaoPagamento);

		List<Parcela> listaParcelas = new ArrayList<>();

		BigDecimal taxaJurosAoMes = getTaxaJurosAoMes(condicaoPagamento);

		BigDecimal valorParcela = produto.getValor().subtract(condicaoPagamento.getValorEntrada())
				.divide(BigDecimal.valueOf(condicaoPagamento.getQtdeParcelas()), 4, RoundingMode.HALF_UP);

		for (int i = 0; i < condicaoPagamento.getQtdeParcelas(); i++) {
			listaParcelas.add(getParcela(taxaJurosAoMes, valorParcela, i));
		}

		return listaParcelas;
	}

	private Parcela getParcela(BigDecimal taxaJurosAoMes, BigDecimal valorParcela, int i) {
		Parcela parcela = new Parcela();
		parcela.setNumeroParcela(i + 1);

		if (i > 5) {
			parcela.setTaxaJurosAoMes(taxaJurosAoMes);
			parcela.setValor(valorParcela.multiply(taxaJurosAoMes.add(BigDecimal.ONE)).divide(BigDecimal.ONE, 2,
					RoundingMode.HALF_UP));
		} else {
			parcela.setTaxaJurosAoMes(BigDecimal.ZERO);
			parcela.setValor(valorParcela.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP));
		}
		return parcela;
	}

	private BigDecimal getTaxaJurosAoMes(CondicaoPagamento condicaoPagamento) {
		BigDecimal taxaJurosAoMes = BigDecimal.ZERO;

		if (condicaoPagamento.getQtdeParcelas() > 6) {
			List<JurosDiarioSelic> listaJuros = selicService.buscarTaxaSelic(SelicService.TRINTA_DIAS);

			taxaJurosAoMes = listaJuros.stream().map(JurosDiarioSelic::getValor).reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).divide(new BigDecimal("100.0000"), 6, RoundingMode.HALF_UP);

		}
		return taxaJurosAoMes;
	}

	private void validacao(Produto produto, CondicaoPagamento condicaoPagamento) {
		if (Objects.isNull(produto) || BigDecimal.ZERO.compareTo(produto.getValor()) >= 0) {
			throw new ServiceException("Produto inválido para calculo de parcelas");
		}

		if (Objects.isNull(condicaoPagamento) || condicaoPagamento.getValorEntrada().compareTo(produto.getValor()) >= 0) {
			throw new ServiceException("Condição de pagamento inválida");
		}
		
		if (Objects.isNull(condicaoPagamento.getQtdeParcelas()) || condicaoPagamento.getQtdeParcelas() <= 0) {
			throw new ServiceException("Condição de pagamento inválida, a quantidade de parcela não pode ser igual ou menor que ZERO");
		}
	}

}
