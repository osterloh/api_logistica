package com.osterloh.logistica.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class JWTUserAutheticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		String login = user.getUsername();
		String authority = user.getAuthorities()
				.stream().map(authorityUser -> authorityUser.getAuthority())
				.collect(Collectors.toList())
				.get(0);

		String jwt = JWT.create()
				.withClaim("login", login)
				.withClaim("authority", authority)
				.sign(Algorithm.HMAC256(Auth.secret));

		Cookie cookie = new Cookie("token", jwt);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(Auth.expiresIn);
		response.addCookie(cookie);

		super.successfulAuthentication(request, response, chain, authResult);
	}
}
