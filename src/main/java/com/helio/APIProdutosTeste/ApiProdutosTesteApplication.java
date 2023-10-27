package com.helio.APIProdutosTeste;

import com.helio.APIProdutosTeste.model.Produto;
import com.helio.APIProdutosTeste.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ApiProdutosTesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProdutosTesteApplication.class, args);
	}

	/*@Bean
	CommandLineRunner commandLineRunner(ProdutoRepository produtoRepository) {
		return args -> {
			produtoRepository.saveAll(List.of(
					new Produto("Coca-cola 2L", "bem geladinha", 11.99f, 10),
					new Produto("Guaranazinho 2L", "ummmm", 10.90f, 6),
					new Produto("Vinho carbenaire", "vinho bem bom", 49.90f, 20),
					new Produto("prego", "serve pra pregar coisas", 0.1f, 543)
			));
		};
	}*/
}
