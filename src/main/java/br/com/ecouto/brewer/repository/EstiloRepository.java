package br.com.ecouto.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ecouto.brewer.model.Estilo;
import br.com.ecouto.brewer.repository.helper.estilo.EstilosQueries;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long>,EstilosQueries{

	public Optional<Estilo> findByNomeIgnoreCase(String nome);
}
