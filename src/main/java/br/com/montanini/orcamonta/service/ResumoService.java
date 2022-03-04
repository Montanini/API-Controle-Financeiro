package br.com.montanini.orcamonta.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.montanini.orcamonta.dto.GastosPorCategoriaDto;
import br.com.montanini.orcamonta.dto.ResumoDoMesDto;
import br.com.montanini.orcamonta.repository.DespesaRespository;
import br.com.montanini.orcamonta.repository.ReceitaRepository;

@Service
public class ResumoService {
	
	@Autowired
	private ReceitaRepository receiteRespository;
	
	@Autowired
	private DespesaRespository despesaRespository;

	public ResumoDoMesDto resumoDoMes(Integer ano, Integer mes) {
		
		BigDecimal valorTotalReceitas = receiteRespository.valorTotalNoMes(ano, mes).orElse(BigDecimal.ZERO);
		BigDecimal valorTotalDespesas = despesaRespository.valorTotalNoMes(ano, mes).orElse(BigDecimal.ZERO);
		BigDecimal saldoFinal = valorTotalReceitas.subtract(valorTotalDespesas);
		List<GastosPorCategoriaDto> gastosPorCategoria = despesaRespository.valorPorCategoria(ano, mes);
		
		return new ResumoDoMesDto(valorTotalReceitas, valorTotalDespesas, saldoFinal, gastosPorCategoria);
	}

}
