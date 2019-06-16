package br.com.ecouto.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.Cidade;
import br.com.ecouto.brewer.repository.CidadeRepository;
import br.com.ecouto.brewer.service.exception.NomeCidadeJaCadastradaException;



@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository repository;
	
	@Transactional
	public void salvar(Cidade cidade) {
		Optional<Cidade> cidadeExistente = repository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		if (cidadeExistente.isPresent()) {
			throw new NomeCidadeJaCadastradaException("Nome de cidade já cadastrado");
		}
		
		repository.save(cidade);
	}

	
}
