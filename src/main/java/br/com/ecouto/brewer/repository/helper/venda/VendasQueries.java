package br.com.ecouto.brewer.repository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ecouto.brewer.model.Venda;
import br.com.ecouto.brewer.repository.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
}
