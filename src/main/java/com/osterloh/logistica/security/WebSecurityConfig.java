package com.osterloh.logistica.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private ImplementsUserDetailsService implementsUserDetailsService;

	private static final String[] AUTH_LIST = {
			"/",
			"/clientes",
			"/clientes/{clienteId}"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, AUTH_LIST).permitAll()
				.antMatchers(HttpMethod.POST, AUTH_LIST).permitAll()
				.antMatchers(HttpMethod.PUT, AUTH_LIST).permitAll()
				.antMatchers(HttpMethod.DELETE, AUTH_LIST).permitAll()
				.antMatchers(HttpMethod.GET, "/entregas").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/entregas").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().permitAll()
				.and().addFilter(jwtUserAutheticationFilter())
				.addFilter(jwtBasicAuthenticationFilter())
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.deleteCookies("token").invalidateHttpSession(true);
	}

	@Bean
	public JWTUserAutheticationFilter jwtUserAutheticationFilter() throws Exception{
		JWTUserAutheticationFilter jwtUserAutheticationFilter = new JWTUserAutheticationFilter();
		jwtUserAutheticationFilter.setAuthenticationManager(authenticationManager());

		return jwtUserAutheticationFilter;
	}

	@Bean
	public JWTBasicAuthenticationFilter jwtBasicAuthenticationFilter() throws Exception{
		return new JWTBasicAuthenticationFilter(authenticationManager());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//				.withUser("johnatan").password("{noop}123456").roles("ADMIN");
		auth.userDetailsService(implementsUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**", "/style/**");
	}
}
