package br.com.ecouto.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ecouto.brewer.controller.page.PageWrapper;
import br.com.ecouto.brewer.model.Cliente;
import br.com.ecouto.brewer.model.TipoPessoa;
import br.com.ecouto.brewer.repository.ClienteRepository;
import br.com.ecouto.brewer.repository.EstadoRepository;
import br.com.ecouto.brewer.repository.filter.ClienteFilter;
import br.com.ecouto.brewer.service.CadastroClienteService;
import br.com.ecouto.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Controller
@RequestMapping("/cliente")
public class ClientesController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@Autowired
	private ClienteRepository repository;
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoRepository.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cliente);
		}
		
		try {
			cadastroClienteService.salvar(cliente);
		}catch (CpfCnpjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(),e.getMessage());
			return novo(cliente);
		}
		
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		return new ModelAndView("redirect:/cliente/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter filter, 
			BindingResult result,
			@PageableDefault(size=2) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cliente/PesquisaCliente");
		
		PageWrapper<Cliente> paginaWrapper = new PageWrapper<>(repository.filtrar(filter,pageable),httpServletRequest);
		mv.addObject("pagina",paginaWrapper);	
		return mv;
	}
	
}