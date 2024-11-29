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
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private ClienteRepositorio cliRepo;
	@Autowired
	private EnderecoRepositorio repositorio;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<String> cadastrar(@RequestBody Endereco endereco){
		Cliente cliente = cliRepo.findByNome(endereco.getTitular());
		if(cliente.getEndereco() != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O titular fornecido já tem endereço cadastrado!");
		}
		else if(cliente.getEndereco() == null) {
			cliente.setEndereco(endereco);
			cliRepo.save(cliente);
			repositorio.save(endereco);
			return ResponseEntity.ok("Endereço cadastrado com sucesso!");			
		}
		return null;
	}
	
	@GetMapping("/enderecos")
	public List<Endereco> listar(){
		return repositorio.findAll();
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<String> atualizar(@RequestBody Endereco atualizacao){
		Endereco endereco = repositorio.findById(atualizacao.getId()).orElse(null);
		if (endereco == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado!");
		}
		Cliente cliente = cliRepo.findByNome(endereco.getTitular());
		if(cliente != null) {
			atualizacao.setTitular(cliente.getNome());
			cliente.setEndereco(atualizacao);
			
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(endereco, atualizacao);
			
			cliRepo.save(cliente);
			repositorio.save(endereco);
			return ResponseEntity.ok("Endereço atualizado com sucesso!");
			
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<String> deletar(@RequestBody Endereco exclusao) {
		Endereco endereco = repositorio.findById(exclusao.getId()).orElse(null);
		if (endereco == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado!");
		}
		Cliente cliente = cliRepo.findByNome(endereco.getTitular());
		if(cliente != null) {
			cliente.setEndereco(null);
			cliRepo.save(cliente);
			repositorio.delete(endereco);
			return ResponseEntity.ok("Endereço excluído com sucesso!");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
	}
	
	
	
}
