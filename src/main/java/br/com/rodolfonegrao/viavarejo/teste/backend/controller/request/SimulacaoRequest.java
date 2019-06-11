
package br.com.rodolfonegrao.viavarejo.teste.backend.controller.request;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.rodolfonegrao.viavarejo.teste.backend.model.CondicaoPagamento;
import br.com.rodolfonegrao.viavarejo.teste.backend.model.Produto;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "produto",
    "condicaoPagamento"
})
public class SimulacaoRequest {

    @JsonProperty("produto")
    private Produto produto;

    @JsonProperty("condicaoPagamento")
    private CondicaoPagamento condicaoPagamento;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("produto")
    public Produto getProduto() {
        return produto;
    }

    @JsonProperty("produto")
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @JsonProperty("condicaoPagamento")
    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    @JsonProperty("condicaoPagamento")
    public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
