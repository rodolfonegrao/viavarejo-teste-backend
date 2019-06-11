
package br.com.rodolfonegrao.viavarejo.teste.backend.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "valorEntrada",
    "qtdeParcelas"
})
public class CondicaoPagamento {

    @JsonProperty("valorEntrada")
    private BigDecimal valorEntrada;
    
    @JsonProperty("qtdeParcelas")
    private Integer qtdeParcelas;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("valorEntrada")
    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    @JsonProperty("valorEntrada")
    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    @JsonProperty("qtdeParcelas")
    public Integer getQtdeParcelas() {
        return qtdeParcelas;
    }

    @JsonProperty("qtdeParcelas")
    public void setQtdeParcelas(Integer qtdeParcelas) {
        this.qtdeParcelas = qtdeParcelas;
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
