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

import br.com.montanini.orcamonta.dto.DespesaDto;
import br.com.montanini.orcamonta.service.DespesaService;

@RestController
@RequestMapping("/despesas")
public class ControllerDespesas {
	
	@Autowired
    private DespesaService despesaService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DespesaDto> cadastrar(@RequestBody @Valid DespesaDto dto, UriComponentsBuilder uriBuilder) {
		
		DespesaDto despesa = despesaService.cadastrar(dto);
        URI endereco = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
        return ResponseEntity.created(endereco).body(despesa);

	}

	@GetMapping
    public ResponseEntity<List<DespesaDto>> listarTodos(@RequestParam(name = "descricao", required = false) String descricao) {
		if (descricao == null) {
			return new ResponseEntity<>(despesaService.listar(), HttpStatus.FOUND);			
		} else {
			List<DespesaDto> dto = despesaService.obterPorDescricao(descricao);
			
			if (!dto.isEmpty()) {
				return new ResponseEntity<>(dto, HttpStatus.FOUND);				
			} else {
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
    }

	@GetMapping("/{id}")
    public ResponseEntity<DespesaDto> detalhar(@PathVariable @NotNull Long id) {
        Optional<DespesaDto> dto = despesaService.obterPorId(id);

        if (dto.isPresent()) {
            return new ResponseEntity<>(dto.get(), HttpStatus.FOUND);    
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<DespesaDto> atualizar(@PathVariable Long id, @RequestBody @Valid DespesaDto dto) {
        Optional<DespesaDto> despesa = despesaService.obterPorId(id);
       
        if (despesa.isPresent()){  
          return new ResponseEntity<>(despesaService.atualizar(id, dto), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<DespesaDto> remover(@PathVariable @NotNull Long id) {
		despesaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<List<DespesaDto>> ListarPorAnoMes(@PathVariable @NotNull int ano, @PathVariable @NotNull int mes) {
		
		List<DespesaDto> dto = despesaService.obterPorAnoMes(ano, mes);

        if (!dto.isEmpty()) {
            return new ResponseEntity<>(dto, HttpStatus.FOUND);    
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}

}
