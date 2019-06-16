package br.com.ecouto.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ecouto.brewer.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long>{

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);
}
