package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CredencialCodigoBarra extends Credencial{
	@Column(unique = true)
	private long codigo;
}
