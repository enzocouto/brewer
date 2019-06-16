package br.com.ecouto.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.ecouto.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.ecouto.brewer.thymeleaf.processor.MessageElementTagProcessor;
import br.com.ecouto.brewer.thymeleaf.processor.OrderElementTagProcessor;
import br.com.ecouto.brewer.thymeleaf.processor.PaginatorElementTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect{

	
	public BrewerDialect() {
		super("Algaworks Brewer","brewer",StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginatorElementTagProcessor(dialectPrefix));
		return processadores;
	}

}
