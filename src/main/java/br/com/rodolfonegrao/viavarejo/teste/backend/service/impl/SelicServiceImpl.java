package br.com.rodolfonegrao.viavarejo.teste.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.rodolfonegrao.viavarejo.teste.backend.model.JurosDiarioSelic;
import br.com.rodolfonegrao.viavarejo.teste.backend.service.SelicService;

@Service
public class SelicServiceImpl implements SelicService {
	
	@Autowired
	private RestTemplate restTemplate;

	/* (non-Javadoc)
	 * @see br.com.rodolfonegrao.viavarejo.teste.backend.infraestrutura.service.indicador.impl.SelicService#buscarTaxaSelic(int)
	 */
	@Override
	public List<JurosDiarioSelic> buscarTaxaSelic(int dias) {

		ResponseEntity<List<JurosDiarioSelic>> response = restTemplate.exchange(
				String.format("https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados/ultimos/%s?formato=json", dias), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<JurosDiarioSelic>>() {
				});

		return response.getBody();
	}

}
