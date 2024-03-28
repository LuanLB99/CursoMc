package com.curso.cursomc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class CredentialsDTO  implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    private String email;
    private String password;

    public CredentialsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
