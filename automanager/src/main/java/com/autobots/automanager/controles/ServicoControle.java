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

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/servico")
public class ServicoControle {
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Servico servico, @PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if(empresa != null) {
			cadastro.cadastrar(servico);
			empresa.getServicos().add(servico);
			cadastro.cadastrar(empresa);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<Servico>> listar(){
		List<Servico> servicos = listagem.servicos();
		if(!servicos.isEmpty()) {
			return new ResponseEntity<List<Servico>>(servicos, HttpStatus.FOUND);
		}
		else{
			return new ResponseEntity<List<Servico>>(HttpStatus.NOT_FOUND);
		}
			
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Servico> buscar(@PathVariable Long id){
		Servico servico = listagem.buscarServico(id);
		if(servico != null) {
			return new ResponseEntity<Servico>(servico, HttpStatus.FOUND);
		}
		else{
			return new ResponseEntity<Servico>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Servico servico = listagem.buscarServico(id);
		if(servico != null) {
			removedor.deletar(servico);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Servico data){
		Servico servico = listagem.buscarServico(data.getId());
		if(servico != null) {
			atualizador.atualizar(servico, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
