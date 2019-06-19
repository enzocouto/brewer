package br.com.ecouto.brewer.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ecouto.brewer.model.Usuario;
import br.com.ecouto.brewer.repository.UsuarioRepository;

@Service
public class AppUserDetailService implements UserDetailsService{

	@Autowired
	UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = repository.porEmailEAtivo(email);	
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha ioncorreta"));
		return new User(usuario.getEmail(),usuario.getSenha(),new HashSet<>());
	}

}
