package com.github.marcoslsouza.transportador.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long pedidoId;
	
	private LocalDate dataParaBusca;
	
	private LocalDate previsaoParaEntrega;
	
	private String enderecoOrigem;
	
	private String enderecoDestino;
}
