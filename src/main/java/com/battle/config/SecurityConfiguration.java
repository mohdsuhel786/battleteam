package com.battle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	public static final String[] PUBLIC_URLS = {
			"/**",
			"/games/allGames",
			"/games/{gameId}",
			"/tournaments/{tournamentId}",
			"/tournaments/{tournamentId}/teams/{teamId}",
			"/tournaments/upcoming",
			"/tournaments/live",
			"/tournaments/completed",
			"/tournaments",
			"/results/**",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/battle/auth/**",
			"/v3/api-docs/**",
			"/v2/api-docs/**",
			"webjars/**"
			
			
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
	 http
	 .csrf()
	 .disable()
	 .authorizeHttpRequests()
	 .requestMatchers(PUBLIC_URLS)
	 .permitAll()
	 .anyRequest()
	 .authenticated()
	 .and()
	 .sessionManagement()
	 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	 .and()
	 .logout().permitAll()
	 .and()
	 .authenticationProvider(authenticationProvider)
	 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	 
	
	 return http.build();
	}
	
	
	
}
