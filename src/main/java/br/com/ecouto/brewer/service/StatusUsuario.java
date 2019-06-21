package br.com.ecouto.brewer.service;

import br.com.ecouto.brewer.repository.UsuarioRepository;

public enum StatusUsuario {

	ATIVAR {
		@Override
		public void executar(Long[] codigos, UsuarioRepository repository) {
			repository.findByCodigoIn(codigos).forEach(u -> u.setAtivo(true));
			
		}
	},
	
	DESASTIVAR {
		@Override
		public void executar(Long[] codigos, UsuarioRepository repository) {
			repository.findByCodigoIn(codigos).forEach(u -> u.setAtivo(false));
			
		}
	};
	
	
	public abstract void executar(Long[] codigos, UsuarioRepository repository);
		
	
}
