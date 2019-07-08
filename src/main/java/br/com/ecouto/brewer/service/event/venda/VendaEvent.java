package br.com.ecouto.brewer.service.event.venda;

import br.com.ecouto.brewer.model.Venda;

public class VendaEvent  {

	private Venda venda;
	
	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}
}
