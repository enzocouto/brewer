package br.com.ecouto.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ecouto.brewer.model.Usuario;
import br.com.ecouto.brewer.repository.helper.usuario.UsuarioQueries;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>,UsuarioQueries{

	public Optional<Usuario> findByEmail(String email);

	public List<Usuario> findByCodigoIn(Long[] codigos);
	
	
}
