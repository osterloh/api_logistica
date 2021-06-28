package com.osterloh.logistica.api.controller;

import com.osterloh.logistica.domain.exception.NegocioException;
import com.osterloh.logistica.domain.model.AuthenticationResponse;
import com.osterloh.logistica.domain.model.Usuario;
import com.osterloh.logistica.domain.repository.UsuarioRepository;
import com.osterloh.logistica.security.ImplementsUserDetailsService;
import com.osterloh.logistica.security.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LoginResource {

	private AuthenticationManager authenticationManager;
	private ImplementsUserDetailsService implementsUserDetailsService;
	private JWTUtil jwtUtil;

	@RequestMapping("/hello")
	public String hello(){
		return "Hello World.";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Usuario usuario) throws Exception{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
			);
		} catch (BadCredentialsException e){
			throw new Exception("Usuário ou senha inválidos.", e);
		}

		final UserDetails userDetails = implementsUserDetailsService.loadUserByUsername(usuario.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
