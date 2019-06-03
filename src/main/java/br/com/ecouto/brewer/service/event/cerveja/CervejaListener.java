package br.com.ecouto.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.ecouto.brewer.storage.FotoStorage;

@Component
public class CervejaListener {

	
	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition= "#evento.temFoto()")
	public void cervejaSalva(CervejaSalvaEvent evento) {
		System.out.println("Novo cerveja salva: "+ evento.getCerveja().getNome());	
		
		System.out.println("tem foto sim: "+evento.getCerveja().getFoto());
		fotoStorage.salvar(evento.getCerveja().getFoto());
	}
}
