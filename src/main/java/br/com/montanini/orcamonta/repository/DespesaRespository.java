package br.com.montanini.orcamonta.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.montanini.orcamonta.dto.GastosPorCategoriaDto;
import br.com.montanini.orcamonta.modelo.Despesa;

public interface DespesaRespository extends JpaRepository<Despesa, Long> {

	List<Despesa> findByDescricaoContainingIgnoreCase(@NotNull String descricao);

	@Query("SELECT d FROM Despesa d WHERE year(d.data) = :ano AND month(d.data) = :mes")
	List<Despesa> findByAnoMes(Integer ano, Integer mes);
	
	@Query("SELECT sum(d.valor) FROM Despesa d WHERE year(d.data) = :ano AND month(d.data) = :mes")
	Optional<BigDecimal> valorTotalNoMes(Integer ano, Integer mes);
	
	@Query("SELECT new br.com.montanini.orcamonta.dto.GastosPorCategoriaDto(d.categoria, sum(d.valor)) "
			+ "FROM Despesa d "
			+ "WHERE year(d.data) = :ano AND month(d.data) = :mes "
			+ "group by d.categoria")
	List<GastosPorCategoriaDto> valorPorCategoria(Integer ano, Integer mes);

}
