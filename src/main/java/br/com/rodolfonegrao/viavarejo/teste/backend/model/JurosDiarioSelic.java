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
	"data", 
	"valor" 
})
public class JurosDiarioSelic {

	@JsonProperty("data")
	private String data;

	@JsonProperty("valor")
	private BigDecimal valor;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public JurosDiarioSelic(){}
	
	public JurosDiarioSelic(String data, BigDecimal valor) {
		super();
		this.data = data;
		this.valor = valor;
	}

	@JsonProperty("data")
	public String getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(String data) {
		this.data = data;
	}

	@JsonProperty("valor")
	public BigDecimal getValor() {
		return valor;
	}

	@JsonProperty("valor")
	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
