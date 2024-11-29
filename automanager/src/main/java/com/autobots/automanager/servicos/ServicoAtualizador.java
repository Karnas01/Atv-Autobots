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
public class ServicoAtualizador {
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
	
	public void atualizar (Empresa empresa, Empresa novaEmpresa) {
		if(!empresa.getNomeFantasia().equals(novaEmpresa.getNomeFantasia())) {
			empresa.setNomeFantasia(novaEmpresa.getNomeFantasia());
		}
		if(!empresa.getEndereco().equals(novaEmpresa.getEndereco())) {
			empresa.setEndereco(novaEmpresa.getEndereco());
		}
		empresaRepo.save(empresa);
	}
	
	public void atualizar (Usuario user, Usuario novoUser) {
		if(!user.getNome().equals(novoUser.getNome())) {
			user.setNome(novoUser.getNome());
		}
		if(!user.getNomeSocial().equals(novoUser.getNome())){
			user.setNomeSocial(novoUser.getNomeSocial());			
		}
		if(user.getIdEmpresa() != novoUser.getIdEmpresa()) {
			Empresa empresa = empresaRepo.findById(novoUser.getIdEmpresa()).orElse(null);
			if(empresa != null) {
				user.setIdEmpresa(novoUser.getIdEmpresa());
				user.setEmpresa(novoUser.getEmpresa());
			}
			else {
				user.setIdEmpresa(null);
				user.setEmpresa(null);
			}
		}
		userRepo.save(user);
	}
	
	public void atualizar (Documento documento, Documento novoDocumento) {
		if(!documento.getDataEmissao().equals(novoDocumento.getDataEmissao())) {
			documento.setDataEmissao(novoDocumento.getDataEmissao());
		}
		if(!documento.getNumero().equals(novoDocumento.getNumero())) {
			documento.setNumero(novoDocumento.getNumero());
		}
		documentoRepo.save(documento);
	}
	
	public void atualizar (Endereco endereco, Endereco novoEndereco) {
		if(!endereco.getEstado().equals(novoEndereco.getEstado())) {
			endereco.setEstado(novoEndereco.getEstado());
		}
		if(!endereco.getCidade().equals(novoEndereco.getCidade())) {
			endereco.setCidade(novoEndereco.getCidade());
		}
		if(!endereco.getBairro().equals(novoEndereco.getBairro())) {
			endereco.setBairro(novoEndereco.getBairro());
		}
		if(!endereco.getRua().equals(novoEndereco.getRua())) {
			endereco.setRua(novoEndereco.getRua());
		}
		if(!endereco.getNumero().equals(novoEndereco.getNumero())) {
			endereco.setNumero(novoEndereco.getNumero());
		}
		if(!endereco.getCodigoPostal().equals(novoEndereco.getCodigoPostal())) {
			endereco.setCodigoPostal(novoEndereco.getCodigoPostal());
		}
		if(!endereco.getInformacoesAdicionais().equals(novoEndereco.getInformacoesAdicionais()) && endereco.getInformacoesAdicionais() == null) {
			endereco.setInformacoesAdicionais(novoEndereco.getInformacoesAdicionais());
		}
		enderecoRepo.save(endereco);
	}
	
	public void atualizar (Email email, Email novoEmail) {
		if(!email.getEndereco().equals(novoEmail.getEndereco())) {
			email.setEndereco(novoEmail.getEndereco());
		}
		emailRepo.save(email);
	}
	
	public void atualizar (Mercadoria mercadoria, Mercadoria novaMercadoria) {
		if(!mercadoria.getValidade().equals(novaMercadoria.getValidade())) {
			mercadoria.setValidade(novaMercadoria.getValidade());
		}
		if(!mercadoria.getFabricao().equals(novaMercadoria.getFabricao())) {
			mercadoria.setFabricao(novaMercadoria.getFabricao());
		}
		if(!mercadoria.getNome().equals(novaMercadoria.getNome())) {
			mercadoria.setNome(novaMercadoria.getNome());
		}
		if(mercadoria.getQuantidade() != novaMercadoria.getQuantidade()){
			mercadoria.setQuantidade(novaMercadoria.getQuantidade());
		}
		if(mercadoria.getValor() != novaMercadoria.getValor()) {
			mercadoria.setValor(novaMercadoria.getValor());
		}
		if(!mercadoria.getDescricao().equals(novaMercadoria.getDescricao())) {
			mercadoria.setDescricao(novaMercadoria.getDescricao());
		}	
		mercadoriaRepo.save(mercadoria);
	}
	
	public void atualizar (Servico servico, Servico novoServico) {
		if(!servico.getNome().equals(novoServico.getNome())) {
			servico.setNome(novoServico.getNome());
		}
		if(servico.getValor() != novoServico.getValor()) {
			servico.setValor(novoServico.getValor());
		}
		if(!servico.getDescricao().equals(novoServico.getDescricao())) {
			servico.setDescricao(novoServico.getDescricao());
		}
		servicoRepo.save(servico);
	}
	
	public void atualizar (Veiculo veiculo, Veiculo novoVeiculo) {
		if(!veiculo.getTipo().equals(novoVeiculo.getTipo())) {
			veiculo.setTipo(novoVeiculo.getTipo());
		}
		if(!veiculo.getModelo().equals(novoVeiculo.getModelo())) {
			veiculo.setModelo(novoVeiculo.getModelo());
		}
		if(!veiculo.getPlaca().equals(novoVeiculo.getPlaca())) {
			veiculo.setPlaca(novoVeiculo.getPlaca());
		}
		if(!veiculo.getProprietario().equals(novoVeiculo.getProprietario())) {
			veiculo.setProprietario(novoVeiculo.getProprietario());
		}
		veiculoRepo.save(veiculo);
	}
	
	public void atualizar (Venda venda, Venda novaVenda) {
		if(!venda.getCliente().equals(novaVenda.getCliente())) {
			venda.setCliente(novaVenda.getCliente());
		}
		if(!venda.getFuncionario().equals(novaVenda.getFuncionario())) {
			venda.setFuncionario(novaVenda.getFuncionario());
		}
		if(!venda.getVeiculo().equals(novaVenda.getVeiculo())) {
			venda.setVeiculo(novaVenda.getVeiculo());
		}	
		vendaRepo.save(venda);
	}
}
