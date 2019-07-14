package br.com.ecouto.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ecouto.brewer.dto.CervejaDTO;
import br.com.ecouto.brewer.dto.TotaisEstoqueCervejaDTO;
import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome);
	public TotaisEstoqueCervejaDTO getTotaisEstoque();
}
