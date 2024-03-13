package com.curso.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class PurchaseOrder implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    private Payment payment;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "id.purchaseOrder")
    private Set<Item> items = new HashSet<>();

    public PurchaseOrder(Integer id, Date instant, Client client, Address address) {
        this.id = id;
        this.instant = instant;
        this.client = client;
        this.address = address;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
