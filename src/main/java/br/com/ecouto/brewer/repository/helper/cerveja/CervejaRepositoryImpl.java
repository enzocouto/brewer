package br.com.ecouto.brewer.repository.helper.cerveja;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.ecouto.brewer.dto.CervejaDTO;
import br.com.ecouto.brewer.dto.TotaisEstoqueCervejaDTO;
import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.filter.CervejaFilter;
import br.com.ecouto.brewer.repository.paginacao.PaginacaoUtil;
import br.com.ecouto.brewer.storage.FotoStorage;
    

public class CervejaRepositoryImpl implements CervejasQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	PaginacaoUtil paginacaoUtil;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	@Override
	public TotaisEstoqueCervejaDTO getTotaisEstoque() {
		//select sum(quantidade_estoque) quantidade, sum(quantidade_estoque * valor) valor_estoque from brewer.cerveja
		String query = "select new br.com.ecouto.brewer.dto.TotaisEstoqueCervejaDTO(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) from Cerveja";
		TotaisEstoqueCervejaDTO totaisEstoque = manager.createQuery(query,TotaisEstoqueCervejaDTO.class)
				.getSingleResult();
		
		return totaisEstoque;
	}
	
	private Long total(CervejaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CervejaFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getSku())) {
				criteria.add(Restrictions.eq("sku", filtro.getSku()));
			}
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if (isEstiloPresente(filtro)) {
				criteria.add(Restrictions.eq("estilo", filtro.getEstilo()));
			}

			if (filtro.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filtro.getSabor()));
			}

			if (filtro.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filtro.getOrigem()));
			}

			if (filtro.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filtro.getValorDe()));
			}

			if (filtro.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filtro.getValorAte()));
			}
		}
	}
	
	private boolean isEstiloPresente(CervejaFilter filtro) {
		return filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null;
	}

	@Override
	public List<CervejaDTO> porSkuOuNome(String skuOuNome) {
		String jpql = "select new br.com.ecouto.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) "
				+ "from Cerveja where lower(sku) like  lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";
		List<CervejaDTO> cervejasFiltradas = manager.createQuery(jpql,CervejaDTO.class)
				.setParameter("skuOuNome", skuOuNome+"%")
				.getResultList();
		
		cervejasFiltradas.forEach(c -> c.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + c.getFoto())));
		return cervejasFiltradas;
	}

}
