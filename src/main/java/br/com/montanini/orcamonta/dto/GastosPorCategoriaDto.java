package br.com.montanini.orcamonta.dto;

import java.math.BigDecimal;

import br.com.montanini.orcamonta.modelo.CategoriaDespesa;

public class GastosPorCategoriaDto {

	private CategoriaDespesa categoria;
	private BigDecimal valorTotal;

	public GastosPorCategoriaDto(CategoriaDespesa categoria, BigDecimal valorTotal) {
		this.categoria = categoria;
		this.valorTotal = valorTotal;
	}

	public CategoriaDespesa getCategoria() {
		return categoria;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

}
