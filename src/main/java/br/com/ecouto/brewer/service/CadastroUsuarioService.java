package br.com.ecouto.brewer.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.ecouto.brewer.controller.CervejasController;
import br.com.ecouto.brewer.model.Usuario;
import br.com.ecouto.brewer.repository.UsuarioRepository;
import br.com.ecouto.brewer.repository.VendasRepository;
import br.com.ecouto.brewer.service.exception.EmailUsuarioJaCadastradoException;
import br.com.ecouto.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	VendasRepository vendaRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	
	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new EmailUsuarioJaCadastradoException("Email já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if(usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			String senha = this.passwordEncoder.encode(usuario.getSenha());
			logger.info("criado password "+senha+ "usuario: "+usuario.getEmail());
			usuario.setSenha(senha);
		}else if(StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.get().getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if(!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}
		repository.save(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
	
		statusUsuario.executar(codigos, repository);
	}

	@Transactional
	public void excluir(Usuario usuario) {
		//Usuario será apenas inativado , exclusão lógica
		usuario.setAtivo(false);
		repository.save(usuario);
		
	}
	
}
