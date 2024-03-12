package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
    @GetMapping
    public List<Category> list(){

        Category cat1 = new Category(1,"Informática");
        Category cat2 = new Category(2,"Escritório");

        List<Category> categories = new ArrayList<Category>();
        categories.add(cat1);
        categories.add(cat2);

        return categories;
    }
}
