package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    final CategoryService service;

    public CategoryResource(CategoryService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> find(@PathVariable Integer id){
        Category category = service.search(id);
        return ResponseEntity.ok().body(category);

    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Category category){
        category = service.insert(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public  ResponseEntity<Void> update(@RequestBody Category category, @PathVariable Integer id){
        category.setId(id);
        Category updatedCategory = service.update(category);
        return ResponseEntity.noContent().build();
    }
}
