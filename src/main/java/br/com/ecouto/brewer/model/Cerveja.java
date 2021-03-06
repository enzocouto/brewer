package br.com.ecouto.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import br.com.ecouto.brewer.repository.listener.CervejaEntityListner;
import br.com.ecouto.brewer.validation.SKU;

@EntityListeners(CervejaEntityListner.class)
@Entity
@Table(name="cerveja")
public class Cerveja implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@SKU
	@NotBlank
	private String sku;
	
	@Transient
	private String urlFoto;
	
	@Transient
	private String urlThumbnailFoto;
	
	@NotBlank
	private String nome;
	
	private String descricao;
	
	@NotNull
	@DecimalMin(value = "0.01")
	@DecimalMax(value = "9999.99" , message = "O valor de cerveja deve ser menor que R$9.999,99")
	private BigDecimal valor;
	
	@NotNull
	@DecimalMax(value="100.0", message = "O valor de teor alcóolico deve ser menor que 100")
	@Column(name="teor_alcolico")
	private BigDecimal teorAlcolico;
	
	@NotNull
	@DecimalMax(value="100.0", message = "A commissão deve ser menor que 100")
	private BigDecimal comissao;
	
	@NotNull
	@Min(value = 0, message = "A quantidade em estoque deve ser maior que 0")
	@Max(value=999999, message = "A quantidade em estoque deve ser no máximo 999.999")
	@Column(name="quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="codigo_estilo")
	private Estilo estilo;
	
	private String foto;
	
	@Column(name="content_type")
	private String contentType;
	
	@Transient
	private boolean novaFoto;
	
	
	@PrePersist @PreUpdate
	private void prePersisteUpdate() {
		sku = sku.toUpperCase();
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getTeorAlcolico() {
		return teorAlcolico;
	}
	public void setTeorAlcolico(BigDecimal teorAlcolico) {
		this.teorAlcolico = teorAlcolico;
	}
	public BigDecimal getComissao() {
		return comissao;
	}
	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public Origem getOrigem() {
		return origem;
	}
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	public Sabor getSabor() {
		return sabor;
	}
	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}
	public Estilo getEstilo() {
		return estilo;
	}
	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "cerveja-mock.png";
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		return "Cerveja [sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + "]";
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(foto);
	}
	
	public boolean isNova() {
		return codigo == null;
	}
	
	public boolean isNovaFoto() {
		return novaFoto;
	}
	public void setNovaFoto(boolean novaFoto) {
		this.novaFoto = novaFoto;
	}
	
	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}
	public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		this.urlThumbnailFoto = urlThumbnailFoto;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
