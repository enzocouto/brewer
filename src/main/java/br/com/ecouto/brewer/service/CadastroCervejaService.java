package br.com.ecouto.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.repository.CervejaRepository;
import br.com.ecouto.brewer.service.event.cerveja.CervejaSalvaEvent;
import br.com.ecouto.brewer.service.exception.ImpossivelExcluirEntidadeException;
import br.com.ecouto.brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejaRepository repository;
	
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
		
		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
	
	@Transactional
	public void excluir(Cerveja cerveja) {
		try {
			String foto = cerveja.getFoto();
			repository.delete(cerveja);
			repository.flush();
			fotoStorage.excluir(foto);
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossivel apagar cerveja com venda associada.");
		}
		
	}
}
