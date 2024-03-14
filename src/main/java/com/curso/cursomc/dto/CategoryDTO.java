package com.curso.cursomc.dto;

import com.curso.cursomc.domain.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    private  Integer id;
    private  String name;

    public CategoryDTO(Category category){
        id = category.getId();
        name =category.getName();
    }

}
