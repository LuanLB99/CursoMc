package com.curso.cursomc.dto;

import com.curso.cursomc.services.validation.ClientInsert;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ClientInsert
public class NewClientDTO  implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min=5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String name;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "email inválido")
    private String email;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String cpfOrCnpj;
    private Integer type;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String password;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String logradouro;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String numero;
    private String complemento;
    private String bairro;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String cep;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String phone1;
    private String phone2;
    private String phone3;

    private Integer cityId;
}
