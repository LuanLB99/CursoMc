package com.curso.cursomc.domain;

import com.curso.cursomc.domain.enums.PaymentState;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;
@Data
@NoArgsConstructor
@Entity
public class PaymentWithTicket extends Payment{
    @Serial
    private  static  final long serialVersionUID = 1L;
    private Date expirationDate;
    private Date paymentDate;

    public PaymentWithTicket(Date expirationDate, Date paymentDate) {
        this.expirationDate = expirationDate;
        this.paymentDate = paymentDate;
    }

    public PaymentWithTicket(Integer id, PaymentState state, PurchaseOrder purchaseOrder, Date expirationDate, Date paymentDate) {
        super(id, state, purchaseOrder);
        this.expirationDate = expirationDate;
        this.paymentDate = paymentDate;
    }
}
