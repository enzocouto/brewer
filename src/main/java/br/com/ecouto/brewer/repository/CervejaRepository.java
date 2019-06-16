package br.com.ecouto.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.helper.cerveja.CervejasQueries;


@Repository
public interface CervejaRepository extends JpaRepository<Cerveja,Long>, CervejasQueries{

	public Optional<Cerveja> findBySkuIgnoreCase(String sku);
}
