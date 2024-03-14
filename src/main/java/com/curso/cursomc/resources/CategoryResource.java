package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.dto.CategoryDTO;
import com.curso.cursomc.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO categoryDTO){
        Category category = service.fromDTO(categoryDTO);
        category = service.insert(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public  ResponseEntity<Void> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer id){
        Category category = service.fromDTO(categoryDTO);
        category.setId(id);
        Category updatedCategory = service.update(category);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<Category> listCategories = service.findAll();
        List<CategoryDTO> listCategoriesDTO = listCategories.stream().map( category -> new CategoryDTO(category)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listCategoriesDTO);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoryDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<Category> listCategories = service.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoryDTO> listCategoriesDTOPage = listCategories.map( category -> new CategoryDTO(category));
        return ResponseEntity.ok().body(listCategoriesDTOPage);
    }
}
