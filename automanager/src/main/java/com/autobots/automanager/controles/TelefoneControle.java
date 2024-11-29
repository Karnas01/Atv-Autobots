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
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	@Autowired
	private ClienteRepositorio cliRepo;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<String> cadastrar(@RequestBody Telefone telefone) {
		Cliente cliente = cliRepo.findByNome(telefone.getTitular());
		if(cliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Titular não encontrado!");
		}
		if(cliente.getNome().equals(telefone.getTitular())){
			cliente.getTelefones().add(telefone);
			repositorio.save(telefone);
			return ResponseEntity.ok("Telefone cadastrado com sucesso");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algo deu errado :(");
		}
	}
	
	@GetMapping("/telefones")
	public List<Telefone> listar(){
		List<Telefone> telefones = repositorio.findAll();
		return telefones;
	}
	@DeleteMapping("/excluir")
	public ResponseEntity<String> deletar(@RequestBody Telefone exclusao) {
		Telefone telefone = repositorio.findById(exclusao.getId()).orElse(null);
		if (telefone == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Telefone não encontrado!");
		}
		Cliente cliente = cliRepo.findByNome(telefone.getTitular());
		if(cliente != null) {
			boolean removido = cliente.getTelefones().removeIf(tel -> tel.getId().equals(telefone.getId()));
			if(removido) {
				cliRepo.save(cliente);
				repositorio.delete(telefone);
				return ResponseEntity.ok("Telefone excluído com sucesso!");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Telefone não encontrado na lista de telefones do cliente");
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
	}
	@PutMapping("/atualizar")
	public ResponseEntity<String> atualizar(@RequestBody Telefone atualizacao) {
		Telefone telefone = repositorio.findById(atualizacao.getId()).orElse(null);
		if (telefone == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Telefone não encontrado");
		}
		Cliente cliente = cliRepo.findByNome(telefone.getTitular());
		if(cliente != null) {
			boolean remover = cliente.getTelefones().removeIf(tel -> tel.getId().equals(telefone.getId()));
			if(remover) {
				atualizacao.setTitular(cliente.getNome());
				cliente.getTelefones().add(atualizacao);
				
				TelefoneAtualizador atualizador = new TelefoneAtualizador();
				atualizador.atualizar(telefone, atualizacao);
				
				cliRepo.save(cliente);
				repositorio.save(telefone);
				return ResponseEntity.ok("Telefone atualizado com sucesso!");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Telefone não encontrado na lista de telefones do cliente!");
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
	}
}
