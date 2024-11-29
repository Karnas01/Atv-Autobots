package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/veiculo")
public class VeiculoControle {
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Veiculo veiculo, @PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if(usuario != null) {
			veiculo.setProprietario(usuario);
			cadastro.cadastrar(veiculo);
			usuario.getVeiculos().add(veiculo);
			cadastro.cadastrar(usuario);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Veiculo>> listar(){
		List<Veiculo> veiculos = listagem.veiculos();
		if(!veiculos.isEmpty()) {
			return new ResponseEntity<List<Veiculo>>(veiculos, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Veiculo>>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Veiculo> listar(@PathVariable Long id){
		Veiculo veiculo = listagem.buscarVeiculo(id);
		if(veiculo != null) {
			return new ResponseEntity<Veiculo>(veiculo, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Veiculo>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Veiculo veiculo = listagem.buscarVeiculo(id);
		if(veiculo != null) {
			removedor.deletar(veiculo);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Veiculo data){
		Veiculo veiculo = listagem.buscarVeiculo(data.getId());
		if(veiculo != null) {
			atualizador.atualizar(veiculo, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
