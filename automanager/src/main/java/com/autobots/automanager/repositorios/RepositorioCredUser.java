package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;

public interface RepositorioCredUser extends JpaRepository<CredencialUsuarioSenha, Long> {

}
