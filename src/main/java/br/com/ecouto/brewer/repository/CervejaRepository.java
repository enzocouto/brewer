package br.com.ecouto.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ecouto.brewer.model.Cerveja;


@Repository
public interface CervejaRepository extends JpaRepository<Cerveja,Long>{

	public Optional<Cerveja> findBySkuIgnoreCase(String sku);
}
