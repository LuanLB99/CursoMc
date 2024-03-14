package com.curso.cursomc.services;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.repositories.CategoryRepository;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public Category search(Integer id){
        Optional<Category> category = repo.findById(id);

        return category.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + Category.class.getName()));
    }

    public Category insert(Category category){
        category.setId(null);
        return repo.save(category);
    }
}
