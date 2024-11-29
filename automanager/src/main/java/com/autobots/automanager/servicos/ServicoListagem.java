package com.autobots.automanager.servicos;

import java.util.List;

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
public class ServicoListagem {
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
	
	public List<Empresa> empresas(){
		return empresaRepo.findAll();
	}
	public Empresa buscarEmpresa(Long id) {
		return empresaRepo.findById(id).orElse(null);
	}
	
	public List<Usuario> usuarios(){
		return userRepo.findAll();
	}
	public Usuario buscarUsuario(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	public List<Documento> documentos(){
		return documentoRepo.findAll();
	}
	public Documento buscarDocumento(Long id) {
		return documentoRepo.findById(id).orElse(null);
	}
	
	public List<Endereco> enderecos(){
		return enderecoRepo.findAll();
	}
	public Endereco buscarEndereco(Long id) {
		return enderecoRepo.findById(id).orElse(null);
	}
	
	public List<Email> emails(){
		return emailRepo.findAll();
	}
	public Email buscarEmail(Long id) {
		return emailRepo.findById(id).orElse(null);
	}
	
	public List<Mercadoria> mercadorias(){
		return mercadoriaRepo.findAll();
	}
	public Mercadoria buscarMercadoria (Long id) {
		return mercadoriaRepo.findById(id).orElse(null);
	}
	
	public List<Servico> servicos(){
		return servicoRepo.findAll();
	}
	public Servico buscarServico (Long id) {
		return servicoRepo.findById(id).orElse(null);
	}
	
	public List<Veiculo> veiculos(){
		return veiculoRepo.findAll();
	}
	public Veiculo buscarVeiculo (Long id) {
		return veiculoRepo.findById(id).orElse(null);
	}
	
	public List<Venda> vendas(){
		return vendaRepo.findAll();
	}
	public Venda buscarVenda(Long id) {
		return vendaRepo.findById(id).orElse(null);
	}

}
