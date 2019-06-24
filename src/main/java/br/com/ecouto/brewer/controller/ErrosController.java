package br.com.ecouto.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrosController {

	
	@GetMapping("/404")
	public String paginaNaoEncontrada() {
		return "404";
	}
	
	@GetMapping("/405")
	public String erroRuntime() {
		return "405";
	}
	
	@GetMapping("/500")
	public String erroServidor() {
		return "500";
	}
}
