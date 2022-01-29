package com.github.marcoslsouza.fornecedor.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoRepository extends JpaRepository<InfoFornecedor, Long> {

	InfoFornecedor findByEstado(String estado);
}
