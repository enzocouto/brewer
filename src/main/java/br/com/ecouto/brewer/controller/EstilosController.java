package br.com.ecouto.brewer.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ecouto.brewer.controller.page.PageWrapper;
import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.model.Estilo;
import br.com.ecouto.brewer.repository.EstiloRepository;
import br.com.ecouto.brewer.repository.filter.EstiloFilter;
import br.com.ecouto.brewer.service.CadastroEstiloService;
import br.com.ecouto.brewer.service.exception.ImpossivelExcluirEntidadeException;
import br.com.ecouto.brewer.service.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilo")
public class EstilosController {

	@Autowired
	CadastroEstiloService cadastroEstiloService;
	
	@Autowired
	private EstiloRepository repository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		return new ModelAndView("estilo/CadastroEstilo");
	}
	
	@RequestMapping(value= {"/novo","{\\d+}"} , method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result,RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(estilo);
		}
		
		try {
			cadastroEstiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		
		attributes.addFlashAttribute("mensagem","Estilo salvo com sucesso");
		return new ModelAndView("redirect:/estilo/novo");
		
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		estilo = cadastroEstiloService.salvar(estilo);
		return ResponseEntity.ok(estilo);
		
		
	}
	
	
	@GetMapping
	public ModelAndView pesquisar(EstiloFilter filter, 
			BindingResult result,
			@PageableDefault(size=2) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("estilo/PesquisaEstilo");
		
		PageWrapper<Estilo> paginaWrapper = new PageWrapper<>(repository.filtrar(filter,pageable),httpServletRequest);
		mv.addObject("pagina",paginaWrapper);
		
		
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Estilo estilo = repository.findOne(codigo);
		ModelAndView mv = novo(estilo);
		mv.addObject(estilo);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Estilo estilo){
		
		try {
			cadastroEstiloService.excluir(estilo);
			return ResponseEntity.ok().build();
		}catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
