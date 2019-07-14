package br.com.ecouto.brewer.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.storage.FotoStorage;


public class CervejaEntityListner {

	
	@Autowired
	private FotoStorage fotoStorage;
	
	@PostLoad
	public void postLoad(final Cerveja cerveja) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		cerveja.setUrlFoto(fotoStorage.getUrl(cerveja.getFotoOuMock()));
		cerveja.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOuMock()));
	}
}
