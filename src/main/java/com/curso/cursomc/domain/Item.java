package com.curso.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;


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

    @JsonIgnore
    public ItemPK getId() {
        return id;
    }

    public void setId(ItemPK id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        StringBuilder builder = new StringBuilder();

        builder.append(getProduct().getName());
        builder.append(", Quantidade: ");
        builder.append(getQuantity());
        builder.append(", Preço unitário: ");
        builder.append(numberFormat.format(getPrice()));
        builder.append(", Subtotal: ");
        builder.append(numberFormat.format(getSubTotal()));
        builder.append("\n");

        return builder.toString();
    }


}
