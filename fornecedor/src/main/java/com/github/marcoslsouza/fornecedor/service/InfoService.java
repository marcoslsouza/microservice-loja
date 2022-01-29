package com.github.marcoslsouza.fornecedor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.marcoslsouza.fornecedor.model.InfoFornecedor;
import com.github.marcoslsouza.fornecedor.model.InfoRepository;

@Service
public class InfoService {

	@Autowired
	private InfoRepository infoRepository;
	
	public InfoFornecedor getInfoPorEstado(String estado) {
		return this.infoRepository.findByEstado(estado);
	}	
}
