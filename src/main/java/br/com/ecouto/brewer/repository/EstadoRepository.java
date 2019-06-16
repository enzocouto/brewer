package br.com.ecouto.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ecouto.brewer.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,Long>{

}
