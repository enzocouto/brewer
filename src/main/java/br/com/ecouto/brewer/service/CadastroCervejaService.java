package br.com.ecouto.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.CervejaRepository;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejaRepository repository;
	
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
	}
}
