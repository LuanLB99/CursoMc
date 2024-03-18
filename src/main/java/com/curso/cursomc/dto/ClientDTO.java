package com.curso.cursomc.dto;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.services.validation.ClientUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ClientUpdate
public class ClientDTO implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    private Integer id;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min=5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private  String name;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "email inválido")
    private  String email;

    public ClientDTO(Client client){
        id = client.getId();
        name = client.getName();
        email = client.getEmail();
    }
}
