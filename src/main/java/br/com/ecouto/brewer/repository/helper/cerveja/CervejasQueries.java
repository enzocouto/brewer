package br.com.ecouto.brewer.repository.helper.cerveja;

import java.util.List;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public List<Cerveja> filtrar(CervejaFilter filtro);
}
