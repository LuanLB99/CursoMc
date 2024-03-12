package com.curso.cursomc;

import com.curso.cursomc.domain.*;
import com.curso.cursomc.domain.enums.ClientType;
import com.curso.cursomc.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	final CategoryRepository categoryRepository;
	final ProductRepository productRepository;
	final StateRepository stateRepository;
	final CityRepository cityRepository;

	final ClientRepository clientRepository;
	final AddressRepository addressRepository;

	public CursomcApplication(CategoryRepository categoryRepository, ProductRepository productRepository,
							  StateRepository stateRepository, CityRepository cityRepository,
							  ClientRepository clientRepository, AddressRepository addressRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.clientRepository = clientRepository;
		this.addressRepository = addressRepository;
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

		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");

		City cit1 = new City(null, "Uberlândia", st1);
		City cit2 = new City(null, "São Paulo", st2);
		City cit3 = new City(null, "Campinas", st2);

		st1.getCities().add(cit1);
		st2.getCities().addAll(Arrays.asList(cit2, cit3));


		stateRepository.saveAll(Arrays.asList(st1,st2));
		cityRepository.saveAll(Arrays.asList(cit1,cit2,cit3));


		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PESSOAFISICA);
		cli1.getPhones().addAll(Arrays.asList("27363323", "93838393"));

		Address add1 = new Address(null, "Rua das Flores","300", "Apto 203","Jardim", "38220834", cli1, cit1);
		Address add2 = new Address(null, "Avenida Matos","105", "Sala 800","Centro", "38777012", cli1, cit2);
		cli1.getAddress().addAll(Arrays.asList(add1,add2));
		clientRepository.save(cli1);
		addressRepository.saveAll(Arrays.asList(add1,add2));
	}
}
