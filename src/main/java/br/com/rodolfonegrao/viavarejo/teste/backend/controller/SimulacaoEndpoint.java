package br.com.rodolfonegrao.viavarejo.teste.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfonegrao.viavarejo.teste.backend.controller.request.SimulacaoRequest;
import br.com.rodolfonegrao.viavarejo.teste.backend.controller.response.ErrorResponse;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Parcela;
import br.com.rodolfonegrao.viavarejo.teste.backend.service.SimulacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Não foi autorizado para realizar essa operação", response = ErrorResponse.class),
        @ApiResponse(code = 422, message = "Erro de negócio", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Erro de infraestrutrua", response = ErrorResponse.class)})
@Api(value = "Endpoint de acesso ao sistema de simulação de parcelas")
@Scope(scopeName = "prototype")
@RestController
public class SimulacaoEndpoint {

	@Autowired
	private SimulacaoService simulacaoService;
	
    @PostMapping("/simulacao")
    public ResponseEntity<List<Parcela>> simularParcelas(@RequestBody SimulacaoRequest simulacaoRequest) {

        List<Parcela> listaParcelas = simulacaoService.calcularParcelas(simulacaoRequest.getProduto(), simulacaoRequest.getCondicaoPagamento());

        return ResponseEntity.ok().body(listaParcelas);
    }
}
