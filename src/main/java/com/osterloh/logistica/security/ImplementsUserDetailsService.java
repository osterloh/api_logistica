package com.osterloh.logistica.security;

import com.osterloh.logistica.domain.exception.EntidadeNaoEncontradaException;
import com.osterloh.logistica.domain.model.Usuario;
import com.osterloh.logistica.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email);

		if(usuario == null){
			throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
		}

		return new User(
				usuario.getEmail(),
				usuario.getSenha(),
				true,
				true,
				true,
				true,
				usuario.getAuthorities()
		);
	}
}
