package br.com.rodolfonegrao.viavarejo.teste.backend.service;

import java.util.List;

import br.com.rodolfonegrao.viavarejo.teste.backend.model.CondicaoPagamento;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Parcela;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Produto;

public interface SimulacaoService {

	List<Parcela> calcularParcelas(Produto produto, CondicaoPagamento condicaoPagamento);

}