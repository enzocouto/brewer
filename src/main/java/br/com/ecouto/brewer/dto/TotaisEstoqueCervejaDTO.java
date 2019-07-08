package br.com.ecouto.brewer.dto;

import java.math.BigDecimal;

public class TotaisEstoqueCervejaDTO {

	private BigDecimal valorTotalEstoque;
	private Long sumItensEstoque;

	public TotaisEstoqueCervejaDTO( BigDecimal valorTotalEstoque,Long sumItensEstoque) {
		this.sumItensEstoque = sumItensEstoque;
		this.valorTotalEstoque = valorTotalEstoque;
	}
	
	public BigDecimal getValorTotalEstoque() {
		return valorTotalEstoque;
	}
	public void setValorTotalEstoque(BigDecimal valorTotalEstoque) {
		this.valorTotalEstoque = valorTotalEstoque;
	}
	public Long getSumItensEstoque() {
		return sumItensEstoque;
	}
	public void setSumItensEstoque(Long sumItensEstoque) {
		this.sumItensEstoque = sumItensEstoque;
	}
}
