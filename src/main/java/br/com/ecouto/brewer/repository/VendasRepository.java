package br.com.ecouto.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ecouto.brewer.model.Venda;

public interface VendasRepository extends JpaRepository<Venda, Long> {

}
