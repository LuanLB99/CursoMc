package com.curso.cursomc.dto;

import com.curso.cursomc.domain.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Double price;

    public ProductDTO(Product product){
        id = product.getId();;
        name = product.getName();
        price = product.getPrice();
    }
}
