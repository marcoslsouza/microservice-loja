package com.github.marcoslsouza.auth;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@RestController
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	// Retornar os dados do usuario. Porque o serviço auth retorna inicialmente o token, com esse token
	// conseguimos os dados do usuario com o metodo abaixo.
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
}
