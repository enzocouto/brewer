package br.com.ecouto.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperaFotoTemporaria(String nome);

	public void salvar(String foto);

	public byte[] recuperar(String nome);
	
	public byte[] recuperarThumbnailByPrefix(String fotoCerveja);

	public void excluir(String foto);
	
}