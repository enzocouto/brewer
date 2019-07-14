package br.com.ecouto.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.ecouto.brewer.service.CadastroCervejaService;
import br.com.ecouto.brewer.storage.FotoStorage;

@Configuration
@ComponentScan(basePackageClasses = {CadastroCervejaService.class, FotoStorage.class})
public class ServiceConfig {

	
	
}
