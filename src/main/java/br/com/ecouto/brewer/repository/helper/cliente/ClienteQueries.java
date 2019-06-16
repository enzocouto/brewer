package br.com.ecouto.brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ecouto.brewer.model.Cliente;
import br.com.ecouto.brewer.repository.filter.ClienteFilter;

public interface ClienteQueries {

	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);
}
