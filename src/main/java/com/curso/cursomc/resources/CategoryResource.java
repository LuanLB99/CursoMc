package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    final CategoryService service;

    public CategoryResource(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Integer id){
        Category category = service.search(id);
        return ResponseEntity.ok().body(category);

    }
}
