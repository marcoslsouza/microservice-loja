package com.github.marcoslsouza.loja.service;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.marcoslsouza.loja.client.FornecedorClient;
import com.github.marcoslsouza.loja.client.TransportadorClient;
import com.github.marcoslsouza.loja.controller.dto.CompraDTO;
import com.github.marcoslsouza.loja.controller.dto.InfoFornecedorDTO;
import com.github.marcoslsouza.loja.dto.InfoPedidoDTO;
import com.github.marcoslsouza.loja.dto.InformacaoEntregaDTO;
import com.github.marcoslsouza.loja.dto.VoucherDTO;
import com.github.marcoslsouza.loja.model.Compra;
import com.github.marcoslsouza.loja.model.CompraRepository;
import com.github.marcoslsouza.loja.model.CompraState;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CompraService {
	
	// Para injetar precisou criar um bean em LojaApplication
	//@Autowired
	//private RestTemplate client;
	
	// LoadBalance
	//@Autowired
	//private DiscoveryClient eurekaClient;

	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private TransportadorClient transportadorClient;
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	// threadPoolKey = "getByIdThreadPool" utilizado para Bulkhead ("Divisao de threads")
	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Optional<Compra> getById(Long id) {
		return this.compraRepository.findById(id);
	}
	
	// threadPoolKey = "realizaCompraThreadPool" utilizado para Bulkhead ("Divisao de threads")
	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compra) {
		
		final String estado = compra.getEndereco().getEstado();
		LOG.info("Buscando informacoes do fornecedor de {},", estado);
		
		// Salvar a compra
		Compra compraSalva = new Compra();
		compraSalva.setState(CompraState.RECEBIDO);
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		this.compraRepository.save(compraSalva);
		// Assim que salva a compra retornamos o id da compra
		compra.setCompraId(compraSalva.getId());
		
		// Salva pedido
		InfoFornecedorDTO info = this.fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());
		System.out.println(info.getEndereco());
		LOG.info("Realizando um pedido");
		InfoPedidoDTO pedido = this.fornecedorClient.realizaPedido(compra.getItens());
		compraSalva.setState(CompraState.PEDIDO_REALIZADO);
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		this.compraRepository.save(compraSalva);
		
		// Simular um erro para nao fazer a reserva da entrega.
		// Chama "fallbackMethod = "realizaCompraFallback"".
		// Dando a opcao do cliente reprocessar "reprocessaCompra(Long id)" a compra ou cancelar "cancelaCompra(Long id)"
		//if(1==1) throw new RuntimeException();
		
		// Salva a reserva da entrega
		InformacaoEntregaDTO entregaDTO = new InformacaoEntregaDTO();
		entregaDTO.setPedidoId(pedido.getId());
		// Tempo de entrega. A partir de hoje 
		entregaDTO.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDTO.setEnderecoOrigem(info.getEndereco());
		entregaDTO.setEnderecoDestino(compra.getEndereco().toString());
		VoucherDTO voucher = this.transportadorClient.reservaEntrega(entregaDTO);
		compraSalva.setState(CompraState.RESERVA_ENTREGA_REALIZADO);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		this.compraRepository.save(compraSalva);
		
		return compraSalva;
	}
	
	public Compra reprocessaCompra(Long id) {
		return null;
	}
	
	public Compra cancelaCompra(Long id) {
		return null;
	}
	
	public Compra realizaCompraFallback(CompraDTO compra) {
		
		// Verifica se a compra foi criada "CompraState.RECEBIDO". Retorna a compra
		if(compra.getCompraId() != null) {
			return this.compraRepository.findById(compra.getCompraId()).get();
		}
		
		Compra compraFalback = new Compra();
		compraFalback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFalback;
	}
}
