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

import br.com.montanini.orcamonta.dto.ReceitaDto;
import br.com.montanini.orcamonta.modelo.Receita;
import br.com.montanini.orcamonta.repository.ReceitaRepository;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository receitaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<ReceitaDto> listar() {
		List<Receita> receitas = receitaRepository.findAll();

		return receitas.stream().map(c -> modelMapper.map(c, ReceitaDto.class)).collect(Collectors.toList());
	}

	public ReceitaDto cadastrar(@Valid ReceitaDto dto) {

		Receita receita = modelMapper.map(dto, Receita.class);

		List<Receita> lista = receitaRepository.findAll();
		boolean jaExite = jaExisteCadastro(lista, dto);

		if (jaExite) {
			return modelMapper.map(new ResponseEntity<ReceitaDto>(HttpStatus.NOT_ACCEPTABLE), ReceitaDto.class);
		}

		receita = receitaRepository.save(receita);
		return modelMapper.map(receita, ReceitaDto.class);

	}

	public Optional<ReceitaDto> obterPorId(Long id) {
		Optional<Receita> receita = receitaRepository.findById(id);

		if (receita.isPresent()) {
			return Optional.of(modelMapper.map(receita.get(), ReceitaDto.class));
		}
		//alteração
		return Optional.empty();
	}

	public ReceitaDto atualizar(Long id, ReceitaDto dto) {
		Receita receita = modelMapper.map(dto, Receita.class);

		List<Receita> lista = receitaRepository.findAll();
		boolean jaExite = jaExisteAtualizado(lista, dto, id);

		if (jaExite) {
			return modelMapper.map(new ResponseEntity<ReceitaDto>(HttpStatus.NOT_ACCEPTABLE), ReceitaDto.class);
		}

		receita.setId(id);
		receita = receitaRepository.save(receita);
		return modelMapper.map(receita, ReceitaDto.class);
	}

	public ResponseEntity<?> excluir(Long id) {

		Optional<Receita> receita = receitaRepository.findById(id);
		if (receita.isPresent()) {
			receitaRepository.deleteById(id);

			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();

	}

	public boolean jaExisteCadastro(List<Receita> lista, ReceitaDto dto) {

		for (Receita receita : lista) {
			if (receita.getDescricao().equals(dto.getDescricao())
					&& receita.getData().getMonth().equals(dto.getData().getMonth())
					&& receita.getData().getYear() == dto.getData().getYear()) {
				return true;
			}
		}

		return false;
	}

	public boolean jaExisteAtualizado(List<Receita> lista, @Valid ReceitaDto dto, Long id) {

		for (Receita receita : lista) {
			if (receita.getDescricao().equals(dto.getDescricao())
					&& receita.getData().getMonth().equals(dto.getData().getMonth())
					&& receita.getData().getYear() == dto.getData().getYear() && !receita.getId().equals(id)) {
				return true;
			}
		}

		return false;
	}

	public List<ReceitaDto> obterPorDescricao(@NotNull String descricao) {

		List<Receita> receitas = receitaRepository.findByDescricaoContainingIgnoreCase(descricao);

		return receitas.stream().map(c -> modelMapper.map(c, ReceitaDto.class)).collect(Collectors.toList());

	}
	
	public List<ReceitaDto> obterPorAnoMes(@NotNull int ano, @NotNull int mes) {

		List<Receita> receitas = receitaRepository.findByAnoMes(ano, mes);

		return receitas.stream().map(c -> modelMapper.map(c, ReceitaDto.class)).collect(Collectors.toList());
	}

}
