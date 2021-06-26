package com.osterloh.logistica.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Role implements GrantedAuthority {

	@Id
	private String nomeRole;

	@ManyToMany
	private List<Usuario> usuarios;

	@Override
	public String getAuthority() {
		return this.nomeRole;
	}
}
