package com.curso.cursomc;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.domain.Product;
import com.curso.cursomc.repositories.CategoryRepository;
import com.curso.cursomc.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	final CategoryRepository categoryRepository;
	final ProductRepository productRepository;

	public CursomcApplication(CategoryRepository categoryRepository, ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");

		Product prod1 = new Product(null,"Computador",2000.00);
		Product prod2 = new Product(null,"Impressora",800.00);
		Product prod3 = new Product(null,"Mouse",80.00);

		cat1.getProducts().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProducts().add(prod2);

		prod1.getCategories().add(cat1);
		prod2.getCategories().addAll(Arrays.asList(cat1,cat2));
		prod3.getCategories().add(cat1);

		categoryRepository.saveAll(Arrays.asList(cat1,cat2));
		productRepository.saveAll(Arrays.asList(prod1,prod2,prod3));
	}
}
