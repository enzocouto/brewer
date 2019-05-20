package br.com.ecouto.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CadastroEstilo {

	
	@RequestMapping("estilo/novo")
	public String novo() {
		return "estilo/CadastroEstilo";
	}
}
