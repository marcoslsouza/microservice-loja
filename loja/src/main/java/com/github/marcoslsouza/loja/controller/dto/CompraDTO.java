package com.github.marcoslsouza.loja.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CompraDTO {

	// Nao aceitar um compraId que vem da tela
	@JsonIgnore
	private Long compraId;
	
	private List<ItemCompraDTO> itens;
	private EnderecoDTO endereco;
}
