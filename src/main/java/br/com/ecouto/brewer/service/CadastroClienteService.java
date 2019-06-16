package br.com.ecouto.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecouto.brewer.model.Cliente;
import br.com.ecouto.brewer.repository.ClienteRepository;
import br.com.ecouto.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository repository;
	
	
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> clienteExistente = repository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		if(clienteExistente.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		repository.save(cliente);
	}
}
