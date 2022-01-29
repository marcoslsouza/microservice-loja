package com.github.marcoslsouza.loja.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InformacaoEntregaDTO {

	private Long pedidoId;
	
	private LocalDate dataParaEntrega;
	
	private String enderecoOrigem;
	
	private String enderecoDestino;
}
