package br.com.montanini.orcamonta.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.montanini.orcamonta.dto.DespesaDto;
import br.com.montanini.orcamonta.modelo.CategoriaDespesa;
import br.com.montanini.orcamonta.modelo.Despesa;
import br.com.montanini.orcamonta.repository.DespesaRespository;

@Service
public class DespesaService {

	@Autowired
	private DespesaRespository despesaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public DespesaDto cadastrar(@Valid DespesaDto dto) {

		Despesa despesa = modelMapper.map(dto, Despesa.class);
		System.out.println(despesa.getDescricao());

		if (despesa.getCategoria() == null) {
			despesa.setCategoria(CategoriaDespesa.OUTRAS);
		}

		List<Despesa> lista = despesaRepository.findAll();
		boolean jaExite = jaExisteCadatro(lista, dto);

		if (jaExite) {
			return modelMapper.map(new ResponseEntity<DespesaDto>(HttpStatus.NOT_ACCEPTABLE), DespesaDto.class);
		}

		despesa = despesaRepository.save(despesa);
		return modelMapper.map(despesa, DespesaDto.class);

	}

	public List<DespesaDto> listar() {
		List<Despesa> despesas = despesaRepository.findAll();

		return despesas.stream().map(c -> modelMapper.map(c, DespesaDto.class)).collect(Collectors.toList());
	}

	public Optional<DespesaDto> obterPorId(Long id) {
		Optional<Despesa> despesa = despesaRepository.findById(id);

		if (despesa.isPresent()) {
			return Optional.of(modelMapper.map(despesa.get(), DespesaDto.class));
		}

		return Optional.empty();

	}

	public List<DespesaDto> obterPorDescricao(@NotNull String descricao) {

		List<Despesa> despesas = despesaRepository.findByDescricaoContainingIgnoreCase(descricao);

		return despesas.stream().map(c -> modelMapper.map(c, DespesaDto.class)).collect(Collectors.toList());

	}

	public void excluir(Long id) {
		despesaRepository.deleteById(id);
	}

	public DespesaDto atualizar(Long id, DespesaDto dto) {
		Despesa despesa = modelMapper.map(dto, Despesa.class);

		List<Despesa> lista = despesaRepository.findAll();
		boolean jaExite = jaExisteAtualizado(lista, dto, id);

		if (jaExite) {
			return modelMapper.map(new ResponseEntity<DespesaDto>(HttpStatus.NOT_ACCEPTABLE), DespesaDto.class);
		}

		if (despesa.getCategoria() == null) {
			despesa.setCategoria(CategoriaDespesa.OUTRAS);
		}

		despesa.setId(id);
		despesa = despesaRepository.save(despesa);
		return modelMapper.map(despesa, DespesaDto.class);
	}

	public boolean jaExisteCadatro(List<Despesa> lista, @Valid DespesaDto dto) {

		for (Despesa despesa : lista) {
			if (despesa.getDescricao().equals(dto.getDescricao())
					&& despesa.getData().getMonth().equals(dto.getData().getMonth())
					&& despesa.getData().getYear() == dto.getData().getYear()) {
				return true;
			}
		}

		return false;
	}

	public boolean jaExisteAtualizado(List<Despesa> lista, @Valid DespesaDto dto, Long id) {

		for (Despesa despesa : lista) {
			if (despesa.getDescricao().equals(dto.getDescricao())
					&& despesa.getData().getMonth().equals(dto.getData().getMonth())
					&& despesa.getData().getYear() == dto.getData().getYear() && !despesa.getId().equals(id)) {
				return true;
			}
		}

		return false;
	}

	public List<DespesaDto> obterPorAnoMes(@NotNull int ano, @NotNull int mes) {

		List<Despesa> despesas = despesaRepository.findByAnoMes(ano, mes);

		return despesas.stream().map(c -> modelMapper.map(c, DespesaDto.class)).collect(Collectors.toList());
	}
}
