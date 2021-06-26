package com.osterloh.logistica.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JWTBasicAuthenticationFilter extends BasicAuthenticationFilter {

	public JWTBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		Cookie token = WebUtils.getCookie(request, "token");
		if(token == null){
			chain.doFilter(request, response);
			return;
		}

		try {
			String jwt = token.getValue();

			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(Auth.secret))
					.build()
					.verify(jwt);

			String login = decodedJWT.getClaim("login").asString();
			String authority = decodedJWT.getClaim("authority").asString();

			List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(authority));
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					login, null, authorities
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			chain.doFilter(request, response);

		} catch (JWTVerificationException ex){
			response.sendError(HttpStatus.UNAUTHORIZED.value());
			return;
		}
	}
}
