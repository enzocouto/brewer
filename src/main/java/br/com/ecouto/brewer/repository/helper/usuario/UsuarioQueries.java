package br.com.ecouto.brewer.repository.helper.usuario;

import java.util.Optional;

import br.com.ecouto.brewer.model.Usuario;

public interface UsuarioQueries {

	public Optional<Usuario> porEmailEAtivo(String email);
}
