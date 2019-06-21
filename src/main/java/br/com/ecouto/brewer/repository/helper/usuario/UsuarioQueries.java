package br.com.ecouto.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import br.com.ecouto.brewer.model.Usuario;
import br.com.ecouto.brewer.repository.filter.UsuarioFilter;

public interface UsuarioQueries {

	public List<Usuario> filtrar(UsuarioFilter filtro);
	
	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
}
