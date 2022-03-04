package br.com.montanini.orcamonta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.montanini.orcamonta.dto.ResumoDoMesDto;
import br.com.montanini.orcamonta.service.ResumoService;

@RestController
@RequestMapping("/resumo")
public class ControllerResumo {
	
	@Autowired
	private ResumoService resumoService;

	@GetMapping("/{ano}/{mes}")
	public ResumoDoMesDto resumoDoMes(@PathVariable Integer ano, @PathVariable Integer mes) {
		return resumoService.resumoDoMes(ano, mes);
	}

}
