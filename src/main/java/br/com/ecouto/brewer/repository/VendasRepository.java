package br.com.ecouto.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ecouto.brewer.model.Venda;
import br.com.ecouto.brewer.repository.helper.venda.VendasQueries;

public interface VendasRepository extends JpaRepository<Venda, Long>, VendasQueries {

}
