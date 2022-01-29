package com.github.marcoslsouza.loja.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.github.marcoslsouza.loja.controller.dto.InfoFornecedorDTO;
import com.github.marcoslsouza.loja.controller.dto.ItemCompraDTO;
import com.github.marcoslsouza.loja.dto.InfoPedidoDTO;

// fornecedor id declarado para o eureka (discovery)
@FeignClient("fornecedor")
public interface FornecedorClient {

	@GetMapping("/info/{estado}")
	InfoFornecedorDTO getInfoPorEstado(@PathVariable String estado);

	@PostMapping("/pedido")
	InfoPedidoDTO realizaPedido(List<ItemCompraDTO> itens);
}
