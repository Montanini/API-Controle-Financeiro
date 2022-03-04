package br.com.montanini.orcamonta.dto;

import java.math.BigDecimal;
import java.util.List;

public class ResumoDoMesDto {

	private BigDecimal valorTotalReceitas;
	private BigDecimal valorTotalDespesas;
	private BigDecimal saldoFinal;
	private List<GastosPorCategoriaDto> gastosPorCategoria;

	
	
	public ResumoDoMesDto(BigDecimal valorTotalReceitas, BigDecimal valorTotalDespesas, BigDecimal saldoFinal,
			List<GastosPorCategoriaDto> gastosPorCategoria) {
		this.valorTotalReceitas = valorTotalReceitas;
		this.valorTotalDespesas = valorTotalDespesas;
		this.saldoFinal = saldoFinal;
		this.gastosPorCategoria = gastosPorCategoria;
	}

	public BigDecimal getValorTotalReceitas() {
		return valorTotalReceitas;
	}

	public BigDecimal getValorTotalDespesas() {
		return valorTotalDespesas;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public List<GastosPorCategoriaDto> getGastosPorCategoria() {
		return gastosPorCategoria;
	}

}
