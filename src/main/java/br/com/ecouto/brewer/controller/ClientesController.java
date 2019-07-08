package br.com.ecouto.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ecouto.brewer.controller.page.PageWrapper;
import br.com.ecouto.brewer.model.Cliente;
import br.com.ecouto.brewer.model.Estilo;
import br.com.ecouto.brewer.model.TipoPessoa;
import br.com.ecouto.brewer.repository.ClienteRepository;
import br.com.ecouto.brewer.repository.EstadoRepository;
import br.com.ecouto.brewer.repository.filter.ClienteFilter;
import br.com.ecouto.brewer.service.CadastroClienteService;
import br.com.ecouto.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import br.com.ecouto.brewer.service.exception.ImpossivelExcluirEntidadeException;

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
	
	@RequestMapping(value= {"/novo","{\\d+}"} , method = RequestMethod.POST)
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
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Cliente cliente = repository.findOne(codigo);
		ModelAndView mv = novo(cliente);
		mv.addObject(cliente);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cliente cliente){
		try {
			cadastroClienteService.excluir(cliente);
			return ResponseEntity.ok().build();
		}catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cliente> pesquisar(String nome) {
		validarTamanhoNome(nome);
		return repository.findByNomeStartingWithIgnoreCase(nome);
	}

	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
	
}