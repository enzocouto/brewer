package br.com.ecouto.brewer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.ItemVenda;
import br.com.ecouto.brewer.model.StatusVenda;
import br.com.ecouto.brewer.model.Venda;
import br.com.ecouto.brewer.repository.VendasRepository;

@Service
public class CadastroVendaService {

	@Autowired
	private VendasRepository vendasRepository;
	
	@Transactional
	public Venda salvar(Venda venda) {
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
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
		
	}
	
}

