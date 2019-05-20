package br.com.ecouto.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientesController {

	
	@RequestMapping("cliente/novo")
	public String novo() {
		return "cliente/CadastroCliente";
	}
}
