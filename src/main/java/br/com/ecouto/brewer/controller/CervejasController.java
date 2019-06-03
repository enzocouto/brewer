package br.com.ecouto.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.model.Origem;
import br.com.ecouto.brewer.model.Sabor;
import br.com.ecouto.brewer.repository.CervejaRepository;
import br.com.ecouto.brewer.repository.EstiloRepository;
import br.com.ecouto.brewer.repository.filter.CervejaFilter;
import br.com.ecouto.brewer.service.CadastroCervejaService;

@Controller
@RequestMapping("/cerveja")
public class CervejasController {

	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@Autowired
	private EstiloRepository estiloRepository;
	
	
	@Autowired
	private CervejaRepository repository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView( "cerveja/CadastroCerveja");
		mv.addObject("estilos",estiloRepository.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens",Origem.values());
		
		return mv;
	}
	
	@RequestMapping(value="/novo" , method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model,RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(cerveja);
		}
		
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem","Cerveja salva com sucesso");
		return new ModelAndView("redirect:/cerveja/novo");
		
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter filter, BindingResult result) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCerveja");
		mv.addObject("estilos",estiloRepository.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens",Origem.values());
		mv.addObject("cervejas",repository.filtrar(filter));
		return mv;
	}
	
}
