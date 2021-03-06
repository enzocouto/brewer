package br.com.ecouto.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.Estilo;
import br.com.ecouto.brewer.repository.EstiloRepository;
import br.com.ecouto.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	EstiloRepository repository;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		Optional<Estilo> estiloOptional = repository.findByNomeIgnoreCase(estilo.getNome());
		if(estiloOptional.isPresent() && !estiloOptional.get().equals(estilo)){
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado");
		}
		
		return repository.saveAndFlush(estilo);
	}

	@Transactional
	public void excluir(Estilo estilo) {	
		repository.delete(estilo);
	}
}
