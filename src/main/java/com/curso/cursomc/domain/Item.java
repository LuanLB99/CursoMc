package com.curso.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @EmbeddedId
    private  ItemPK id = new ItemPK();

    private Double discount;
    private Integer quantity;
    private Double price;

    public double getSubTotal(){
        return (price - discount ) * quantity;
    }

    public Item(PurchaseOrder purchaseOrder, Product product, Double discount, Integer quantity, Double price) {
        id.setPurchaseOrder(purchaseOrder);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public PurchaseOrder getPurchaseOrder(){
        return id.getPurchaseOrder();
    }

    public Product getProduct(){
        return id.getProduct();
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder){
        id.setPurchaseOrder(purchaseOrder);
    }

    public void setProduct(Product product){
        id.setProduct(product);
    }

}
