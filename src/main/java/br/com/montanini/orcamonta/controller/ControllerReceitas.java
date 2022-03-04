package br.com.montanini.orcamonta.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.montanini.orcamonta.dto.ReceitaDto;
import br.com.montanini.orcamonta.service.ReceitaService;

@RestController
@RequestMapping("/receitas")
public class ControllerReceitas {

	@Autowired
	private ReceitaService receitaService;

	@GetMapping
    public ResponseEntity<List<ReceitaDto>> listarTodos(@RequestParam(name = "descricao", required = false) String descricao) {
		if (descricao == null) {
			return new ResponseEntity<>(receitaService.listar(), HttpStatus.FOUND);			
		} else {
			List<ReceitaDto> dto = receitaService.obterPorDescricao(descricao);
			
			if (!dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.FOUND);				
			} else {
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
    }

	@PostMapping
	@Transactional
	public ResponseEntity<ReceitaDto> cadastrar(@RequestBody @Valid ReceitaDto dto, UriComponentsBuilder uriBuilder) {

		ReceitaDto receita = receitaService.cadastrar(dto);
		URI endereco = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(endereco).body(receita);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDto> detalhar(@PathVariable @NotNull Long id) {
		Optional<ReceitaDto> dto = receitaService.obterPorId(id);

		if (dto.isPresent()) {
			return new ResponseEntity<>(dto.get(), HttpStatus.FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ReceitaDto> atualizar(@PathVariable Long id, @RequestBody @Valid ReceitaDto dto) {
		ReceitaDto atualizada = receitaService.atualizar(id, dto);
		return ResponseEntity.ok(atualizada);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		receitaService.excluir(id);
		return ResponseEntity.noContent().build();
		
	}

}
