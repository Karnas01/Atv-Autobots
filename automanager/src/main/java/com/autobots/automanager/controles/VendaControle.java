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
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/venda")
public class VendaControle {
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
	public ResponseEntity<?> cadastrar(@RequestBody Venda venda,@PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		Usuario funcionario = listagem.buscarUsuario(venda.getFuncionario().getId());
		Usuario cliente = listagem.buscarUsuario(venda.getCliente().getId());
		Veiculo veiculo = listagem.buscarVeiculo(venda.getVeiculo().getId());
		if (empresa != null && funcionario != null && cliente != null && veiculo != null) {
			venda.setFuncionario(funcionario);
			venda.setCliente(cliente);
			venda.setVeiculo(veiculo);
			
			empresa.getVendas().add(venda);
			cadastro.cadastrar(empresa);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<Venda>> listar(){
		List<Venda> vendas = listagem.vendas();
		if(!vendas.isEmpty()) {
			return new ResponseEntity<List<Venda>>(vendas, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Venda>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Venda> buscar(@PathVariable Long id){
		Venda venda = listagem.buscarVenda(id);
		if(venda != null) {
			return new ResponseEntity<Venda>(venda, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Venda>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Venda venda = listagem.buscarVenda(id);
		if(venda != null) {
			removedor.deletar(venda);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Venda data){
		Venda venda = listagem.buscarVenda(data.getId());
		if(venda != null) {
			atualizador.atualizar(venda, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
