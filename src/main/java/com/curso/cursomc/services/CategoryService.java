package com.curso.cursomc.services;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.repositories.CategoryRepository;
import com.curso.cursomc.services.exceptions.DataIntegrityException;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public  Category update(Category category){
        search(category.getId());
        return  repo.save(category);
    }

    public void delete(Integer id){
        search(id);
        try{
            repo.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir uma categoria com produtos associados");
        }
    }
    public List<Category> findAll(){
        return repo.findAll();
    }

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return  repo.findAll(pageRequest);
    }
}
