package com.github.marcoslsouza.loja.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.marcoslsouza.loja.dto.InformacaoEntregaDTO;
import com.github.marcoslsouza.loja.dto.VoucherDTO;

@FeignClient("transportador")
public interface TransportadorClient {

	@PostMapping("/entrega")
	public VoucherDTO reservaEntrega(InformacaoEntregaDTO entregaDTO);
}
