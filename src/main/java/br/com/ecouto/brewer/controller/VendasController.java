package br.com.ecouto.brewer.controller;


import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ecouto.brewer.controller.page.PageWrapper;
import br.com.ecouto.brewer.controller.validator.VendaValidator;
import br.com.ecouto.brewer.dto.VendaMes;
import br.com.ecouto.brewer.dto.VendaOrigem;
import br.com.ecouto.brewer.mail.Mailer;
import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.model.ItemVenda;
import br.com.ecouto.brewer.model.StatusVenda;
import br.com.ecouto.brewer.model.TipoPessoa;
import br.com.ecouto.brewer.model.Venda;
import br.com.ecouto.brewer.repository.CervejaRepository;
import br.com.ecouto.brewer.repository.VendasRepository;
import br.com.ecouto.brewer.repository.filter.VendaFilter;
import br.com.ecouto.brewer.security.UsuarioSistema;
import br.com.ecouto.brewer.service.CadastroVendaService;
import br.com.ecouto.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	CervejaRepository cervejaRepository;
	
	@Autowired
	TabelasItensSession tabelaItens;
	
	@Autowired
	CadastroVendaService cadastroVendaService;
	
	@Autowired
	VendaValidator vendaValidator;
	
	@Autowired
	VendasRepository repository;
	
	@Autowired
	private Mailer mailer;
	
	@InitBinder("venda")
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		setUUid(venda);
		mv.addObject("itens",venda.getItens());
		mv.addObject("valorFrete",venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens",tabelaItens.getValorTotal(venda.getUuid()));
		return mv;
	}
	
	@PostMapping(value="/nova", params= "salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		venda.setUsuario(usuarioSistema.getUsuario());
		cadastroVendaService.salvar(venda);
		attributes.addFlashAttribute("mensagem","Venda salva com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping(value="/nova", params= "emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		venda.setUsuario(usuarioSistema.getUsuario());
		cadastroVendaService.emitir(venda);
		attributes.addFlashAttribute("mensagem","Venda emitida com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}

	
	
	@PostMapping(value="/nova",  params= "enviaremail")
	public ModelAndView enviaremail(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		venda.setUsuario(usuarioSistema.getUsuario());
		try {
			venda.setStatus(StatusVenda.EMITIDA);
			venda = cadastroVendaService.salvar(venda);
		}catch(RuntimeException e) {
			attributes.addFlashAttribute("mensagem",e.getMessage());
		}
	
		mailer.enviar(venda);
		attributes.addFlashAttribute("mensagem",String.format("Venda nº %d salva e email enviado",venda.getCodigo()));
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping("/item")
	public @ResponseBody ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
		tabelaItens.adicionarItem(uuid,cerveja, 1);
		return mvTabelaItensVenda(uuid);
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoCerveja, Integer quantidade,String uuid) {
		Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
		tabelaItens.alterarQuantidadeItens(uuid,cerveja, quantidade);
		return mvTabelaItensVenda(uuid);
	}
	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable String uuid) {
		tabelaItens.excluirItem(uuid,cerveja);
		return mvTabelaItensVenda(uuid);
	}
	
	@PostMapping(value="/nova",  params= "cancelar")
	public ModelAndView cancelar(Venda venda, BindingResult result, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		try {
			cadastroVendaService.cancelar(venda);
		}catch(AccessDeniedException e) {
			return new ModelAndView("/403");
		}
		
		
		attributes.addFlashAttribute("mensagem","Venda cancelada com sucesso");
		return new ModelAndView("redirect:/vendas/" + venda.getCodigo());
	}

	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/venda/PesquisaVendas");
		mv.addObject("todosStatus", StatusVenda.values());
		mv.addObject("tiposPessoa", TipoPessoa.values());
		
		PageWrapper<Venda> paginaWrapper = new PageWrapper<>(repository.filtrar(vendaFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping("/totalPorMes")
	public @ResponseBody List<VendaMes> listarTotalVendaPorMes() {
		return repository.totalPorMes();
	}
	
	@GetMapping("/porOrigem")
	public @ResponseBody List<VendaOrigem> listarTotalVendaPorOrigem() {
		return repository.totalPorOrigem();
	}
	
	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}
	
	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();
		vendaValidator.validate(venda, result);
	}
	

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Venda venda = repository.buscarComItens(codigo);
		
		setUUid(venda);
		for(ItemVenda item : venda.getItens()) {
			tabelaItens.adicionarItem(venda.getUuid(), item.getCerveja(), item.getQuantidade());
		}
		
		ModelAndView mv = nova(venda);
		mv.addObject(venda);
		return mv;
	}

	private void setUUid(Venda venda) {
		if(StringUtils.isEmpty(venda.getUuid())) {
			venda.setUuid(UUID.randomUUID().toString());
		}
	}

}
