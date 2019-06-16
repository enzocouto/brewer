package br.com.ecouto.brewer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.ecouto.brewer.model.Cidade;
import br.com.ecouto.brewer.repository.CidadeRepository;

@Controller
@RequestMapping("/cidade")
public class CidadeController {

	
	@Autowired
	CidadeRepository cidadeRepository;
	
	
	@RequestMapping("/novo")
	public String novo() {
		return "cidade/CadastroCidade";
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name="estado", defaultValue = "-1") Long codigoEstado){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return cidadeRepository.findByEstadoCodigo(codigoEstado);
	}
	
}
