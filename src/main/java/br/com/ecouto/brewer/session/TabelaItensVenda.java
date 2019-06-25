package br.com.ecouto.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.model.ItemVenda;

@Component
@SessionScope
public class TabelaItensVenda {

	private List<ItemVenda> itens = new ArrayList<>();
	
	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public void adicionarItem(Cerveja cerveja, Integer quantidade){
		
		Optional<ItemVenda> itemVendaOptional = buscarItensPorCerveja(cerveja);
		
		ItemVenda itemVenda = null;
		if(itemVendaOptional.isPresent()) {
			itemVenda = itemVendaOptional.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
		}else {
			itemVenda = new ItemVenda();
			itemVenda.setCerveja(cerveja);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(cerveja.getValor());
			itens.add(0,itemVenda);
		}
		
	}

	
	
	public void alterarQuantidadeItens(Cerveja cerveja, Integer quantidade) {
		ItemVenda itemVenda = buscarItensPorCerveja(cerveja).get();
		itemVenda.setQuantidade(quantidade);
	}
	
	
	public int total() {
		return itens.size();
	}
	
	public  List<ItemVenda> getItens(){
		return itens;
	}
	
	private Optional<ItemVenda> buscarItensPorCerveja(Cerveja cerveja) {
		Optional<ItemVenda> itemVendaOptional = itens.stream().filter(i -> i.getCerveja().equals(cerveja)).findAny();
		return itemVendaOptional;
	}

	
}
