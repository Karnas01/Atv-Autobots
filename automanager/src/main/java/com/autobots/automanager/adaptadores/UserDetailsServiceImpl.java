package com.autobots.automanager.adaptadores;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioCredUser;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private RepositorioUsuario repositorio;
	
	@Autowired
	private RepositorioCredUser credRepo; 

	public Usuario selecionar(List<Usuario> usuarios, String id) {
		Usuario usuario = null;
		for (Usuario user : usuarios) {
			Set<Credencial> credenciais = user.getCredenciais();
			for(Credencial credencial : credenciais) {
				for(CredencialUsuarioSenha cred : credRepo.findAll()) {
					if(credencial.getId().equals(cred.getId())) {
						String nomeUsuario = cred.getNomeUsuario();
						if(nomeUsuario.trim().equals(id.trim())) {
							usuario = user;
							break;
						}
					}
				}
			}
		}
		return usuario;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Usuario> usuarios = repositorio.findAll();
		Usuario selecionado = this.selecionar(usuarios, username);
		if (selecionado == null) {
			throw new UsernameNotFoundException(username);
		}
		String nomeUsuario = "";
		String senha = "";
		for(Credencial credencial : selecionado.getCredenciais()) {
			for(CredencialUsuarioSenha cred : credRepo.findAll()) {
				if(credencial.getId().equals(cred.getId())) {
					nomeUsuario = cred.getNomeUsuario();
					senha = cred.getSenha();
				}
			}
		}
		return new UserDetailsImpl(nomeUsuario, senha, selecionado.getNivelDeAcesso());
	}
}