package com.curso.cursomc.domain;

import com.curso.cursomc.domain.enums.PaymentState;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
@Entity
@JsonTypeName("pagamentoComCartao")
public class PaymentWithCard extends Payment{
    @Serial
    private  static  final long serialVersionUID = 1L;
    private Integer parcelsNumber;

    public PaymentWithCard(Integer id, PaymentState state, PurchaseOrder purchaseOrder, Integer parcelsNumber) {
        super(id, state, purchaseOrder);
        this.parcelsNumber = parcelsNumber;
    }
}
