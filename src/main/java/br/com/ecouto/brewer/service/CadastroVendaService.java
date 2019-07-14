package br.com.ecouto.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.StatusVenda;
import br.com.ecouto.brewer.model.Venda;
import br.com.ecouto.brewer.repository.VendasRepository;
import br.com.ecouto.brewer.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {

	@Autowired
	private VendasRepository vendasRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public Venda salvar(Venda venda) {
		
		if(!venda.isSalvarPermitido()) {
			throw new RuntimeException("Usuario tentando salvar uma venda cancelada");
		}
		
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}else {
			Venda vendaExistente = vendasRepository.findOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		}
		
		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}
		
		return vendasRepository.saveAndFlush(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
		publisher.publishEvent(new VendaEvent(venda));
		
	}
    
	@Transactional
	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
	public void cancelar(Venda venda) {
		Venda vendaExistente = vendasRepository.findOne(venda.getCodigo());
		vendaExistente.setStatus(StatusVenda.CANCELADA);
	    vendasRepository.save(vendaExistente);
	}
	
}

