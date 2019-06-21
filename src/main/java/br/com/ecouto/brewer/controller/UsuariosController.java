package br.com.ecouto.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ecouto.brewer.model.Usuario;
import br.com.ecouto.brewer.repository.GrupoRepository;
import br.com.ecouto.brewer.repository.UsuarioRepository;
import br.com.ecouto.brewer.repository.filter.UsuarioFilter;
import br.com.ecouto.brewer.service.CadastroUsuarioService;
import br.com.ecouto.brewer.service.StatusUsuario;
import br.com.ecouto.brewer.service.exception.EmailUsuarioJaCadastradoException;
import br.com.ecouto.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	UsuarioRepository repository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos",grupoRepository.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			cadastroUsuarioService.salvar(usuario);
		}catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(),e.getMessage());
			return novo(usuario);
		}catch(SenhaObrigatoriaUsuarioException ex) {
			result.rejectValue("senha", ex.getMessage(),ex.getMessage());
			return novo(usuario);
		}
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
		usuario = new Usuario();
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter filter, 
			BindingResult result,
			@PageableDefault(size=2) Pageable pageable,
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");
		mv.addObject("grupos",grupoRepository.findAll());
		mv.addObject("usuarios", repository.filtrar(filter));
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		cadastroUsuarioService.alterarStatus(codigos,statusUsuario);
	}


}
