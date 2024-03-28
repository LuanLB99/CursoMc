package com.curso.cursomc.domain;

import com.curso.cursomc.domain.enums.PaymentState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;
@Data
@NoArgsConstructor
@Entity
@JsonTypeName("pagamentoComBoleto")
public class PaymentWithTicket extends Payment{
    @Serial
    private  static  final long serialVersionUID = 1L;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date expirationDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
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
