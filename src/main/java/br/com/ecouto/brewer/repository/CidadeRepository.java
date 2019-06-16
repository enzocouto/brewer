package br.com.ecouto.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ecouto.brewer.model.Cidade;
import br.com.ecouto.brewer.model.Estado;
import br.com.ecouto.brewer.repository.helper.cidade.CidadesQueries;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadesQueries {

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);
}
