package br.com.montanini.orcamonta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class ReceitaDto {

	private Long id;
	@NotEmpty(message = "Descrição deve ser preenchida")
	private String descricao;
	@Positive(message = "Valor deve ser informado")
	private BigDecimal valor;
	private LocalDateTime data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
}
