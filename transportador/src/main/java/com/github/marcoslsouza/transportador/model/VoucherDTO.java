package com.github.marcoslsouza.transportador.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherDTO {

	private Long numero;
	
	private LocalDate previsaoParaEntrega;
}
