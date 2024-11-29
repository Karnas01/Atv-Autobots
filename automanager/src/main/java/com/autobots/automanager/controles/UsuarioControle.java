package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.Perfil;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar-login/{id}")
	public ResponseEntity<?> login (@RequestBody CredencialUsuarioSenha cred, @PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if(usuario != null) {
			BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
			cred.setNomeUsuario(cred.getNomeUsuario());
			cred.setSenha(codificador.encode(cred.getSenha()));
			usuario.getCredenciais().add(cred);
			cadastro.cadastrar(usuario);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@PostMapping("/cadastrar-vendedor/{id}")
	public ResponseEntity<?> cadastrar (@RequestBody Usuario user, @PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if(empresa != null) {
			if(user.getMercadorias().size() > 0) {
				empresa.getMercadorias().addAll(user.getMercadorias());
			}
			user.getPerfis().add(PerfilUsuario.FORNECEDOR);
			user.getNivelDeAcesso().add(Perfil.ROLE_VENDEDOR);
			user.setIdEmpresa(empresa.getId());
			user.setEmpresa(empresa.getNomeFantasia());
			cadastro.cadastrar(user);
			
			empresa.getUsuarios().add(user);
			cadastro.cadastrar(empresa);
			
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar-cliente")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Usuario user){
		user.getPerfis().add(PerfilUsuario.CLIENTE);
		user.getNivelDeAcesso().add(Perfil.ROLE_CLIENTE);
		cadastro.cadastrar(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/cadastrar-admin")
	public ResponseEntity<?> cadatrarAdmin (@RequestBody Usuario admin){
		admin.getNivelDeAcesso().add(Perfil.ROLE_ADMIN);
		cadastro.cadastrar(admin);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@PostMapping("/cadastrar-gerente/{id}")
	public ResponseEntity<?> cadatrarGerente (@RequestBody Usuario gerente, @PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if(empresa != null) {
			gerente.getNivelDeAcesso().add(Perfil.ROLE_GERENTE);
			gerente.setIdEmpresa(empresa.getId());
			gerente.setEmpresa(empresa.getNomeFantasia());
			cadastro.cadastrar(gerente);
			
			empresa.getUsuarios().add(gerente);
			cadastro.cadastrar(empresa);
			
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<Usuario>> listar(){
		List<Usuario> usuarios = listagem.usuarios();
		if (!usuarios.isEmpty()) {
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Usuario>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Usuario> buscar(@PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if (usuario != null) {
			return new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if(usuario != null && usuario.getIdEmpresa() != null) {
			Empresa empresa = listagem.buscarEmpresa(usuario.getIdEmpresa());
			empresa.getUsuarios().remove(usuario);
			removedor.deletar(usuario);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Usuario data){
		Usuario usuario = listagem.buscarUsuario(data.getId());
		if(usuario != null) {
			atualizador.atualizar(usuario, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
