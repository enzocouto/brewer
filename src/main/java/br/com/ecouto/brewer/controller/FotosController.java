package br.com.ecouto.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.ecouto.brewer.dto.FotoDTO;
import br.com.ecouto.brewer.storage.FotoStorage;
import br.com.ecouto.brewer.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController{
	
	@Autowired
	private FotoStorage FotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> resultado = new DeferredResult<FotoDTO>();

		Thread thread = new Thread(new FotoStorageRunnable(files, resultado,FotoStorage));
		thread.start();
		
		return resultado;
	}
	
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperaFotoTemporaria(@PathVariable String nome) {
		return FotoStorage.recuperaFotoTemporaria(nome);
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperaFoto(@PathVariable String nome) {
		return FotoStorage.recuperar(nome);
	}
} 





