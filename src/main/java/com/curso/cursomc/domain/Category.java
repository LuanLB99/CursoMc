package com.curso.cursomc.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Category implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private  String name;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
