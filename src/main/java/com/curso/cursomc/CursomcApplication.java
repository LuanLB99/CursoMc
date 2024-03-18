package com.curso.cursomc;

import com.curso.cursomc.domain.*;
import com.curso.cursomc.domain.enums.ClientType;
import com.curso.cursomc.domain.enums.PaymentState;
import com.curso.cursomc.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	final CategoryRepository categoryRepository;
	final ProductRepository productRepository;
	final StateRepository stateRepository;
	final CityRepository cityRepository;

	final ClientRepository clientRepository;
	final AddressRepository addressRepository;

	final PurchaseOrderRepository purchaseOrderRepository;
	final PaymentRepository paymentRepository;

	final OrderedItemRepository orderedItemRepository;

	public CursomcApplication(CategoryRepository categoryRepository, ProductRepository productRepository,
							  StateRepository stateRepository, CityRepository cityRepository,
							  ClientRepository clientRepository, AddressRepository addressRepository,
							  PurchaseOrderRepository purchaseOrderRepository, PaymentRepository paymentRepository,
							  OrderedItemRepository orderedItemRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.clientRepository = clientRepository;
		this.addressRepository = addressRepository;
		this.purchaseOrderRepository = purchaseOrderRepository;
		this.paymentRepository = paymentRepository;
		this.orderedItemRepository = orderedItemRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Enfermaria");

		Product prod1 = new Product(null,"Computador",2000.00);
		Product prod2 = new Product(null,"Impressora",800.00);
		Product prod3 = new Product(null,"Mouse",80.00);
		Product prod4 =  new Product(null, "Mesa de Escritório",300.00);
		Product prod5 =  new Product(null, "Toalha",50.00);
		Product prod6 =  new Product(null, "Colcha",200.00);
		Product prod7 =  new Product(null, "TV true color",1200.00);
		Product prod8 =  new Product(null, "Roçadeira",800.00);
		Product prod9 =  new Product(null, "Abajour",100.00);
		Product prod10 =  new Product(null, "Pendente",180.00);
		Product prod11 =  new Product(null, "Shampoo",90.00);

		cat1.getProducts().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProducts().addAll(Arrays.asList(prod2,prod4));
		cat3.getProducts().addAll(Arrays.asList(prod5,prod6));
		cat4.getProducts().addAll(Arrays.asList(prod1,prod2,prod3,prod7));
		cat5.getProducts().addAll(Arrays.asList(prod8));
		cat6.getProducts().addAll(Arrays.asList(prod9,prod10));
		cat7.getProducts().addAll(Arrays.asList(prod11));

		prod1.getCategories().addAll(Arrays.asList(cat1, cat4));
		prod2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		prod3.getCategories().addAll(Arrays.asList(cat1, cat4));
		prod4.getCategories().addAll(Arrays.asList(cat2));
		prod5.getCategories().addAll(Arrays.asList(cat3));
		prod6.getCategories().addAll(Arrays.asList(cat3));
		prod7.getCategories().addAll(Arrays.asList(cat4));
		prod8.getCategories().addAll(Arrays.asList(cat5));
		prod9.getCategories().addAll(Arrays.asList(cat6));
		prod10.getCategories().addAll(Arrays.asList(cat6));
		prod11.getCategories().addAll(Arrays.asList(cat7));

		categoryRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		productRepository.saveAll(Arrays.asList(prod1,prod2,prod3,prod4,prod5,prod6,prod7,prod8,prod9,prod10,prod11));

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

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		PurchaseOrder ped1 = new PurchaseOrder(null,dateFormat.parse("30/09/2017 10:32"), cli1, add1);
		PurchaseOrder ped2 = new PurchaseOrder(null, dateFormat.parse("10/10/2017 19:35"), cli1, add2);

		PaymentWithCard pay1 = new PaymentWithCard(null, PaymentState.PAID, ped1, 6);
		ped1.setPayment(pay1);
		PaymentWithTicket pay2 = new PaymentWithTicket(null, PaymentState.PENDING, ped2, dateFormat.parse("20/10/2017 00:00"), null);
		ped2.setPayment(pay2);

		cli1.getPurchaseOrders().addAll(Arrays.asList(ped1,ped2));

		purchaseOrderRepository.saveAll(Arrays.asList(ped1,ped2));
		paymentRepository.saveAll(Arrays.asList(pay1,pay2));

		Item it1 = new Item(ped1, prod1, 0.00, 1, prod1.getPrice());
		Item it2 = new Item(ped1, prod3, 0.00, 2, prod3.getPrice());
		Item it3 = new Item(ped2, prod2, 100.00, 1, prod2.getPrice());

		ped1.getItems().addAll(Arrays.asList(it1,it2));
		ped2.getItems().addAll(Arrays.asList(it3));

		prod1.getItems().addAll(Arrays.asList(it1));
		prod2.getItems().addAll(Arrays.asList(it3));
		prod3.getItems().addAll(Arrays.asList(it2));

		orderedItemRepository.saveAll(Arrays.asList(it1,it2,it3));
	}
}
