package com.curso.cursomc.domain;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ItemPK implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public int hashCode(){
        return Objects.hash(purchaseOrder, product);
    }

}
