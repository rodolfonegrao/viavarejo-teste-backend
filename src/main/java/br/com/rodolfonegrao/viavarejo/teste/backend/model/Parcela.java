
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
    "numeroParcela",
    "valor",
    "taxaJurosAoMes"
})
public class Parcela {

    @JsonProperty("numeroParcela")
    private int numeroParcela;

    @JsonProperty("valor")
    private BigDecimal valor;

    @JsonProperty("taxaJurosAoMes")
    private BigDecimal taxaJurosAoMes;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("numeroParcela")
    public int getNumeroParcela() {
        return numeroParcela;
    }

    @JsonProperty("numeroParcela")
    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    @JsonProperty("valor")
    public BigDecimal getValor() {
        return valor;
    }

    @JsonProperty("valor")
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @JsonProperty("taxaJurosAoMes")
    public BigDecimal getTaxaJurosAoMes() {
        return taxaJurosAoMes;
    }

    @JsonProperty("taxaJurosAoMes")
    public void setTaxaJurosAoMes(BigDecimal taxaJurosAoMes) {
        this.taxaJurosAoMes = taxaJurosAoMes;
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
