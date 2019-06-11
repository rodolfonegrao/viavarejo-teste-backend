package br.com.rodolfonegrao.viavarejo.teste.backend.service;

import java.util.List;

import br.com.rodolfonegrao.viavarejo.teste.backend.model.JurosDiarioSelic;

public interface SelicService {

    static final int TRINTA_DIAS = 30;
	
	List<JurosDiarioSelic> buscarTaxaSelic(int dias);

}