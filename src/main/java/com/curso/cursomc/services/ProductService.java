package com.curso.cursomc.services;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.domain.Product;
import com.curso.cursomc.repositories.CategoryRepository;
import com.curso.cursomc.repositories.ProductRepository;

import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    final ProductRepository repo;
    final CategoryRepository categoryRepository;

    public ProductService(ProductRepository ProductRepository, CategoryRepository categoryRepository) {
        this.repo = ProductRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product search(Integer id){
        Optional<Product> order =  repo.findById(id);

        return order.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id + ", Tipo: " + Product.class.getName()));
    }

    public Page<Product> searchProductsPerPage(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(ids);
        return repo.findDistinctByNameContainingAndCategriesIn(name, categories, pageRequest);
    }
}
