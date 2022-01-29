package com.github.marcoslsouza.transportador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableResourceServer
public class TransportadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportadorApplication.class, args);
	}

}
