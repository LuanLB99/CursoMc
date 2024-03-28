package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Product;
import com.curso.cursomc.dto.ProductDTO;
import com.curso.cursomc.resources.utils.URL;
import com.curso.cursomc.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> find(@PathVariable Integer id){
        Product product = productService.search(id);
        return ResponseEntity.ok().body(product);
    }

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<Page<ProductDTO>> findPage(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categories", defaultValue = "") String categories,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        String nameDecoded = URL.decodeParam(name);
        List<Integer> ids = URL.decodeIntList(categories);
        Page<Product> listProducts = productService.searchProductsPerPage(nameDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProductDTO> listProductsDTOPage = listProducts.map( product -> new ProductDTO(product));
        return ResponseEntity.ok().body(listProductsDTOPage);
    }
}
