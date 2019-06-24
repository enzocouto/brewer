package br.com.ecouto.brewer.config;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer{

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
		//sistema ficara no maximop 20 segundos com sessao ativa
		//servletContext.getSessionCookieConfig().setMaxAge(20);
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter",
				new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}
