package com.helio.APIProdutosTeste.repository;

import com.helio.APIProdutosTeste.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    boolean existsByNome(String nome);
}
