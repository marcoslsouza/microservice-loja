package com.github.marcoslsouza.loja.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.marcoslsouza.loja.controller.dto.CompraDTO;
import com.github.marcoslsouza.loja.model.Compra;
import com.github.marcoslsouza.loja.service.CompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	private CompraService compraService;
	
	@PostMapping
	public Compra realizaCompra(@RequestBody CompraDTO compra) {
		return this.compraService.realizaCompra(compra);	
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Compra> getById(@PathVariable("id") Long id) {
		Optional<Compra> compra = this.compraService.getById(id);
		return compra.map(linha -> ResponseEntity.ok().body(linha)).orElse(ResponseEntity.notFound().build()); 
	}
}
