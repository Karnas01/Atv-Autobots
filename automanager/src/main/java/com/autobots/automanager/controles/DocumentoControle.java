package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private DocumentoRepositorio repositorio;
	@Autowired
	private ClienteRepositorio cliRepo;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<String> cadastrar(@RequestBody Documento documento) {
		Cliente cliente = cliRepo.findByNome(documento.getTitular());
		if(cliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Titular não encontrado!");
		}
		if(cliente.getNome().equals(documento.getTitular())){
			cliente.getDocumentos().add(documento);
			repositorio.save(documento);
			return ResponseEntity.ok("Documento cadastrado com sucesso");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algo deu errado :(");
		}
	}
	
	@GetMapping("/documentos")
	public List<Documento> listar(){
		List<Documento> documentos = repositorio.findAll();
		return documentos;
	}
	@DeleteMapping("/excluir")
	public ResponseEntity<String> deletar(@RequestBody Documento exclusao) {
		Documento documento = repositorio.findById(exclusao.getId()).orElse(null);
		if (documento == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado!");
		}
		Cliente cliente = cliRepo.findByNome(documento.getTitular());
		if(cliente != null) {
			boolean removido = cliente.getDocumentos().removeIf(doc -> doc.getId().equals(documento.getId()));
			if(removido) {
				cliRepo.save(cliente);
				repositorio.delete(documento);
				return ResponseEntity.ok("Documento excluído com sucesso!");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado na lista de documentos do cliente");
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
	}
	@PutMapping("/atualizar")
	public ResponseEntity<String> atualizar(@RequestBody Documento atualizacao) {
		Documento documento = repositorio.findById(atualizacao.getId()).orElse(null);
		if (documento == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado");
		}
		Cliente cliente = cliRepo.findByNome(documento.getTitular());
		if(cliente != null) {
			boolean remover = cliente.getDocumentos().removeIf(doc -> doc.getId().equals(documento.getId()));
			if(remover) {
				atualizacao.setTitular(cliente.getNome());
				cliente.getDocumentos().add(atualizacao);
				
				DocumentoAtualizador atualizador = new DocumentoAtualizador();
				atualizador.atualizar(documento, atualizacao);
				
				cliRepo.save(cliente);
				repositorio.save(documento);
				return ResponseEntity.ok("Documento atualizado com sucesso!");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado na lista de documentos do cliente!");
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
	}
	
}
