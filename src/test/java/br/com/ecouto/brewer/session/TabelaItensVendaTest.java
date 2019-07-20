package br.com.ecouto.brewer.session;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;

import br.com.ecouto.brewer.model.Cerveja;
import br.com.ecouto.brewer.session.TabelaItensVenda;

public class TabelaItensVendaTest {

	
	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setUp() {
		tabelaItensVenda = new TabelaItensVenda("1");
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void calcularValorTotalComUmItem() {
		Cerveja cerveja = new Cerveja();
		BigDecimal valor = new BigDecimal("8.90");
		cerveja.setValor(valor);
		
		tabelaItensVenda.adicionarItem(cerveja, 1);
		assertEquals(valor, tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void calcularValorTotalComVariosItens() {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		BigDecimal v1 = new BigDecimal("8.90");
		c1.setValor(v1);
		
		Cerveja c2 = new Cerveja();
		c2.setCodigo(2L);
		BigDecimal v2 = new BigDecimal("4.99");
		c2.setValor(v2);
		
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c2, 2);
		assertEquals(new BigDecimal("18.88"), tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmasCervejas() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c1, 1);
		
		assertEquals(1, tabelaItensVenda.total());
	    assertEquals(new BigDecimal("9.00"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveAlterarQuantidadeDoItem()  throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.alterarQuantidadeItens(c1, 3);
		
		assertEquals(new BigDecimal("13.50"), tabelaItensVenda.getValorTotal());
		assertEquals(1, tabelaItensVenda.total());
	}
	
	@Test
	public void gerarData() {
		System.out.println(LocalDate.of(2019, Month.FEBRUARY, 10).toEpochDay() * 24 * 60 * 60);
		System.out.println(LocalDate.of(2019, Month.JULY, 15).toEpochDay() * 24 * 60 * 60);
	}
	
	@Test
	public void deveExcluirItem() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("8.90"));
		
		Cerveja c2 = new Cerveja();
		c2.setCodigo(2L);
		c2.setValor(new BigDecimal("4.99"));
		
		Cerveja c3 = new Cerveja();
		c3.setCodigo(3L);
		c3.setValor(new BigDecimal("2.00"));
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c2, 2);
		tabelaItensVenda.adicionarItem(c3, 1);
		
		tabelaItensVenda.excluirItem(c2);
		
		assertEquals(2, tabelaItensVenda.total());
		assertEquals(new BigDecimal("10.90"), tabelaItensVenda.getValorTotal());
	}
	
}
