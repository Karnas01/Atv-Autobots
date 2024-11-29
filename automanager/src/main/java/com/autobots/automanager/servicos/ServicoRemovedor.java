package com.autobots.automanager.servicos;

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
public class ServicoRemovedor {
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
	
	public void deletar(Empresa data) {
		empresaRepo.delete(data);
	}
	public void deletar(Usuario data) {
		for(Empresa empresa : empresaRepo.findAll()) {
			for(Usuario user : empresa.getUsuarios()) {
				if(user.getId().equals(data.getId())) {
					empresa.getUsuarios().remove(user);
					empresaRepo.save(empresa);
				}
			}
		}
		for(Venda venda : vendaRepo.findAll()) {
			if(venda.getFuncionario().getId().equals(data.getId())) {
				venda.setFuncionario(null);
				vendaRepo.save(venda);
			}
			if(venda.getCliente().getId().equals(data.getId())) {
				venda.setCliente(null);
				vendaRepo.save(venda);
			}
		}
		for (Veiculo veiculo : veiculoRepo.findAll()) {
			if(veiculo.getProprietario().getId().equals(data.getId())) {
				veiculo.setProprietario(null);
				veiculoRepo.save(veiculo);
			}
		}
		userRepo.delete(data);
	}
	public void deletar(Documento data) {
		for(Usuario user : userRepo.findAll()) {
			for(Documento documento : user.getDocumentos()) {
				if(documento.getId().equals(data.getId())) {
					user.getDocumentos().remove(documento);
					userRepo.save(user);
				}
			}
		}
		documentoRepo.delete(data);
	}
	public void deletar (Endereco data) {
		for (Usuario user : userRepo.findAll()) {
			if(user.getEndereco().getId().equals(data.getId())) {
				user.setEndereco(null);
				userRepo.save(user);
			}
		}
		enderecoRepo.delete(data);
	}
	public void deletar (Email data) {
		for(Usuario user : userRepo.findAll()) {
			for(Email email : user.getEmails()) {
				if(email.getId().equals(data.getId())) {
					user.getEmails().remove(email);
					userRepo.save(user);
				}
			}
		}
		emailRepo.delete(data);
	}
	public void deletar(Mercadoria data) {
		for (Usuario user: userRepo.findAll()) {
			for(Mercadoria mercadoria: user.getMercadorias()) {
				if(mercadoria.getId() == data.getId()) {
					user.getMercadorias().remove(mercadoria);
					userRepo.save(user);
				}
			}
		}
		for (Empresa empresa : empresaRepo.findAll()) {
			for(Mercadoria mercadoria : empresa.getMercadorias()) {
				if(mercadoria.getId().equals(data.getId())) {
					empresa.getMercadorias().remove(mercadoria);
					empresaRepo.save(empresa);
				}
			}
		}
		mercadoriaRepo.delete(data);
	}
	public void deletar(Servico data) {
		for (Empresa empresa : empresaRepo.findAll()) {
			for(Servico servico : empresa.getServicos()) {
				if(servico.getId().equals(data.getId())) {
					empresa.getServicos().remove(servico);
					empresaRepo.save(empresa);
				}
			}
		}
		servicoRepo.delete(data);
	}
	public void deletar(Veiculo data) {
		for(Usuario user : userRepo.findAll()) {
			for(Veiculo veiculo: user.getVeiculos()) {
				if(veiculo.getId().equals(data.getId())) {
					user.getVeiculos().remove(veiculo);
					userRepo.save(user);
				}
			}
		}
		veiculoRepo.delete(data);
	}
	public void deletar(Venda data) {
		vendaRepo.delete(data);
	}
}
