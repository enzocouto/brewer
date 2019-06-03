package br.com.ecouto.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import br.com.ecouto.brewer.model.Cerveja;

public class CervejaSalvaEvent {

	
	@Autowired
	private Cerveja cerveja;
	
	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(cerveja.getFoto());
	}

	
}
