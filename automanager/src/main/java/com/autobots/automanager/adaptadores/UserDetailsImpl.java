package com.autobots.automanager.adaptadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.autobots.automanager.enumeracoes.Perfil;

@SuppressWarnings("serial")
public class UserDetailsImpl implements UserDetails {
	private String nome;
	private String senha;
	private Collection<? extends GrantedAuthority> autenticacao;

	public UserDetailsImpl(String nome, String senha, List<Perfil> acesso) {
		this.nome = nome;
		this.senha = senha;
		this.autoridades(acesso);
	}

	public void autoridades(List<Perfil> perfis) {
		List<SimpleGrantedAuthority> autoridades = new ArrayList<>();
		for (Perfil perfil : perfis) {
			autoridades.add(new SimpleGrantedAuthority(perfil.name()));
		}
		this.autenticacao = autoridades;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.nome;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return this.autenticacao;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}