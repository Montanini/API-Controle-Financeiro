package br.com.montanini.orcamonta.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.montanini.orcamonta.modelo.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	List<Receita> findByDescricaoContainingIgnoreCase(@NotNull String descricao);
	
	@Query("SELECT r FROM Receita r WHERE year(r.data) = :ano AND month(r.data) = :mes")
	List<Receita> findByAnoMes(Integer ano, Integer mes);
	
	@Query("SELECT sum(r.valor) FROM Receita r WHERE year(r.data) = :ano AND month(r.data) = :mes")
	Optional<BigDecimal> valorTotalNoMes(Integer ano, Integer mes);

}
