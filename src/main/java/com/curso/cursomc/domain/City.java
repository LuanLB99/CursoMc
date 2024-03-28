package com.curso.cursomc.domain;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
@Entity
public class City {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;


    @ManyToOne
    @JoinColumn(name = "state_id")
    private  State state;

    public City(Integer id, String name, State state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }
}
