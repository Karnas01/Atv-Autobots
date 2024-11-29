package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/email")
public class EmailControle {
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Email email, @PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if(usuario != null) {
			cadastro.cadastrar(email);
			usuario.getEmails().add(email);
			cadastro.cadastrar(usuario);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
	@GetMapping("/listar")
	public ResponseEntity<List<Email>> listar(){
		List<Email> emails = listagem.emails();
		if(!emails.isEmpty()) {
			return new ResponseEntity<List<Email>>(emails, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Email>>(HttpStatus.FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Email> buscar(@PathVariable Long id){
		Email email = listagem.buscarEmail(id);
		if(email != null) {
			return new ResponseEntity<Email>(email, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Email>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Email email = listagem.buscarEmail(id);
		if(email != null) {
			removedor.deletar(email);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Email data){
		Email email = listagem.buscarEmail(data.getId());
		if(email != null) {
			atualizador.atualizar(email, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
