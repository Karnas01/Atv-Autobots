package com.autobots.automanager.servicos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import com.autobots.automanager.repositorios.RepositorioEmail;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;

@Service
public class ServicoCadastro {
	@Autowired
	private RepositorioEmpresa empresaRepo;
	@Autowired 
	private RepositorioUsuario userRepo;
	@Autowired
	private RepositorioDocumento documentoRepo;
	@Autowired
	private RepositorioEndereco enderecoRepo;
	@Autowired
	private RepositorioEmail emailRepo;
	@Autowired
	private RepositorioMercadoria mercadoriaRepo;
	@Autowired
	private RepositorioServico servicoRepo;
	@Autowired
	private RepositorioVeiculo veiculoRepo;
	@Autowired
	private RepositorioVenda vendaRepo;
	
	public void cadastrar(Empresa empresa) {
		empresa.setCadastro(new Date());
		empresaRepo.save(empresa);
	}
	public void cadastrar(Usuario user) {
		userRepo.save(user);
	}
	public void cadastrar(Documento documento) {
		documentoRepo.save(documento);
	}
	public void cadastrar(Endereco endereco) {
		enderecoRepo.save(endereco);
	}
	public void cadastrar(Email email) {
		emailRepo.save(email);
	}
	public void cadastrar(Mercadoria mercadoria) {
		mercadoria.setCadastro(new Date());
		mercadoriaRepo.save(mercadoria);
	}
	public void cadastrar(Servico servico) {
		servicoRepo.save(servico);
	}
	public void cadastrar(Veiculo veiculo) {
		veiculoRepo.save(veiculo);
	}
	public void cadastrar(Venda venda) {
		venda.setCadastro(new Date());
		vendaRepo.save(venda);
	}
	
}
