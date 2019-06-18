package br.com.ecouto.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ecouto.brewer.model.Usuario;
import br.com.ecouto.brewer.repository.GrupoRepository;
import br.com.ecouto.brewer.service.CadastroUsuarioService;
import br.com.ecouto.brewer.service.exception.EmailUsuarioJaCadastradoException;
import br.com.ecouto.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	GrupoRepository grupoRepository;
	
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
	
}
