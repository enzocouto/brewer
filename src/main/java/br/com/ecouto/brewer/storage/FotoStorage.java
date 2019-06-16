package br.com.ecouto.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperaFotoTemporaria(String nome);

	public void salvar(String foto);

	public byte[] recuperaFoto(String nome);
	
}