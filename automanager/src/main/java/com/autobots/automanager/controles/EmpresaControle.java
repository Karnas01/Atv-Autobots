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
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody Empresa empresa){
		cadastro.cadastrar(empresa);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/listar")
	public ResponseEntity<List<Empresa>> listar(){
		List<Empresa> empresas = listagem.empresas();
		if(empresas.isEmpty()) {
			return new ResponseEntity<List<Empresa>>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<Empresa>>(empresas, HttpStatus.FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Empresa> buscar(@PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if (empresa != null) {
			return new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if (empresa == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			removedor.deletar(empresa);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Empresa data){
		Empresa empresa = listagem.buscarEmpresa(data.getId());
		if (empresa == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			atualizador.atualizar(empresa, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	}
}
