package com.helio.APIProdutosTeste.controller;

import com.helio.APIProdutosTeste.model.Produto;
import com.helio.APIProdutosTeste.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<Produto> getProdutos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/contar")
    public long countProdutos() {
        return produtoRepository.count();
    }

    record ProdutoRequest(String nome, String descricao, float preco, int quantidade) {}
    @PostMapping
    public ResponseEntity postProduto(@RequestBody ProdutoRequest req) {
        if (req.nome == null || req.nome.isEmpty()) {
            return new ResponseEntity<>("Nome inválido", HttpStatus.BAD_REQUEST);
        }

        if (req.preco < 0) {
            return new ResponseEntity<>("Preço precisa ser >= 0", HttpStatus.BAD_REQUEST);
        }

        if (req.quantidade < 0) {
            return new ResponseEntity<>("Quantidade precisa ser >= 0", HttpStatus.BAD_REQUEST);
        }

        Produto produto = new Produto(req.nome, req.descricao, req.preco, req.quantidade);

        if (produto.getDescricao() == null) {
            produto.setDescricao("");
        }

        if (produtoRepository.existsByNome(req.nome)) {
            return new ResponseEntity<>("Nome já está em uso", HttpStatus.BAD_REQUEST);
        }

        produtoRepository.save(produto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProduto(@PathVariable("id") int id) {
        Optional<Produto> optProduto = produtoRepository.findById(id);

        if (optProduto.isEmpty()) {
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.BAD_REQUEST);
        }

        Produto produto = optProduto.get();
        produtoRepository.delete(produto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    record PutProdutoRequest(String nome, String descricao, Float preco, Integer quantidade) {}

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity putProduto(@PathVariable("id") int id,
                                     @RequestBody PutProdutoRequest req) {

        Optional<Produto> optProduto = produtoRepository.findById(id);

        if (optProduto.isEmpty()) {
            return new ResponseEntity<>("Produto não encontrado", HttpStatus.BAD_REQUEST);
        }

        Produto produto = optProduto.get();

        if (req.nome != null) {
            if (req.nome.isEmpty()) {
                return new ResponseEntity<>("Nome inválido", HttpStatus.BAD_REQUEST);
            }

            if (produtoRepository.existsByNome(req.nome)) {
                return new ResponseEntity<>("Nome já está em uso", HttpStatus.BAD_REQUEST);
            }

            produto.setNome(req.nome);
        }

        if (req.preco != null) {
            if (req.preco < 0) {
                return new ResponseEntity<>("Preço precisa ser >= 0", HttpStatus.BAD_REQUEST);
            }

            produto.setPreco(req.preco);
        }

        if (req.quantidade != null) {
            if (req.quantidade < 0) {
                return new ResponseEntity<>("Quantidade precisa ser >= 0", HttpStatus.BAD_REQUEST);
            }

            produto.setQuantidade(req.quantidade);
        }

        if (req.descricao != null) {
            produto.setDescricao(req.descricao);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
