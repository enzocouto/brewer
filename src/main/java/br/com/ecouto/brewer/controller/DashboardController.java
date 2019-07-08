package br.com.ecouto.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.ecouto.brewer.dto.TotaisEstoqueCervejaDTO;
import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.CervejaRepository;
import br.com.ecouto.brewer.repository.ClienteRepository;
import br.com.ecouto.brewer.repository.VendasRepository;

@Controller
public class DashboardController {

	@Autowired
	VendasRepository vendasRepository;
	
	@Autowired
	ClienteRepository clientesRepository;
	
	@Autowired
    ClienteRepository clienteRepository;
	
	@Autowired
	CervejaRepository CervejaRepository;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		mv.addObject("vendasNoMes",vendasRepository.vendasNoMes());
		mv.addObject("vendasNoAno",vendasRepository.vendasNoAno());
		mv.addObject("ticketMediaAno", vendasRepository.getValorTicketMediaAno());
	    mv.addObject("totalClientes",clienteRepository.count());
	    
	    TotaisEstoqueCervejaDTO totaisEstoque = CervejaRepository.getTotaisEstoque();
	    mv.addObject("itensEstoque",totaisEstoque.getSumItensEstoque());
	    mv.addObject("valorEstoque",totaisEstoque.getValorTotalEstoque());
		return mv;
	}
}
