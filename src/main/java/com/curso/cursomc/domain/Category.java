package com.curso.cursomc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class Category implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    private  Integer id;
    private  String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
