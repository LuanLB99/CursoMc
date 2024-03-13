package com.curso.cursomc.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class Item implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @EmbeddedId
    private  ItemPK id = new ItemPK();
    private Double discount;
    private Integer quantity;
    private Double price;

    public Item(PurchaseOrder purchaseOrder, Product product, Double discount, Integer quantity, Double price) {
        id.setPurchaseOrder(purchaseOrder);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
    }

    public PurchaseOrder getPurchaseOrder(){
        return id.getPurchaseOrder();
    }

    public Product getProduct(){
        return id.getProduct();
    }
}
