package br.com.montanini.orcamonta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import br.com.montanini.orcamonta.modelo.CategoriaDespesa;

public class DespesaDto {

	private Long id;
	@NotEmpty(message = "Descrição deve ser preenchida")
	private String descricao;
	@Positive(message = "Valor deve ser informado")
	private BigDecimal valor;
	private LocalDate  data;
	@Enumerated(EnumType.STRING)
	private CategoriaDespesa categoria;

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

	public LocalDate  getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDespesa categoria) {
		this.categoria = categoria;
	}

}