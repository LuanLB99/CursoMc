package com.curso.cursomc.dto;

import com.curso.cursomc.domain.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CategoryDTO implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    private  Integer id;
    @NotEmpty(message = "Preencimento de nome obrigatório.")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private  String name;

    public CategoryDTO(Category category){
        id = category.getId();
        name =category.getName();
    }

}
