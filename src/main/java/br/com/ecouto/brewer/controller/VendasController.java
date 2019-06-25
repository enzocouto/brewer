package br.com.ecouto.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.CervejaRepository;
import br.com.ecouto.brewer.session.TabelaItensVenda;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	CervejaRepository cervejaRepository;
	
	@Autowired
	TabelaItensVenda tabelaItensVenda;
	
	@GetMapping("/nova")
	public String nova() {
		return "venda/CadastroVenda";
	}
	
	@PostMapping("/item")
	public @ResponseBody ModelAndView adicionarItem(Long codigoCerveja) {
		Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		System.out.println("total de itens:"+tabelaItensVenda.total());
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens",tabelaItensVenda.getItens());
		return mv;
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoCerveja, Integer quantidade) {
		Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
		tabelaItensVenda.alterarQuantidadeItens(cerveja, quantidade);
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		return mv;
	}

}
