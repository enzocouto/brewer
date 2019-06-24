package br.com.ecouto.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import br.com.ecouto.brewer.model.Usuario;
import br.com.ecouto.brewer.repository.filter.UsuarioFilter;

public interface UsuarioQueries {

	public Page<Usuario> filtrar(UsuarioFilter filtro,Pageable pegeable);
	
	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
}
