package br.com.ecouto.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.Estilo;
import br.com.ecouto.brewer.repository.EstiloRepository;
import br.com.ecouto.brewer.service.exception.NomeEstiloCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	EstiloRepository repository;
	
	@Transactional
	public void salvar(Estilo estilo) {
		Optional<Estilo> estiloOptional = repository.findByNomeIgnoreCase(estilo.getNome());
		if(estiloOptional.isPresent()){
			throw new NomeEstiloCadastradoException("Nome de estilo já cadastrado");
		}
		repository.save(estilo);
	}
}
