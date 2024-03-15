package com.curso.cursomc.domain;

import com.curso.cursomc.domain.enums.PaymentState;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private Integer state;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name= "pedido_id")
    @MapsId
    private PurchaseOrder purchaseOrder;

    public Payment(Integer id, PaymentState state, PurchaseOrder purchaseOrder) {
        this.id = id;
        this.state = (state == null) ? null : state.getCod();
        this.purchaseOrder = purchaseOrder;
    }

    public PaymentState getState(){
        return PaymentState.toEnum(state);
    }
    public void setPaymentState(PaymentState state){
        this.state = state.getCod();
    }
}
