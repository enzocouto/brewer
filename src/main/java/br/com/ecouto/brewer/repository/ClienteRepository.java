package br.com.ecouto.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ecouto.brewer.model.Cliente;
import br.com.ecouto.brewer.repository.helper.cliente.ClienteQueries;

public interface ClienteRepository extends JpaRepository<Cliente,Long>, ClienteQueries{

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome);
}
