package com.curso.cursomc.services;

import com.curso.cursomc.domain.PurchaseOrder;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmation(PurchaseOrder purchaseOrder);

    void sendMail(SimpleMailMessage msg);
}
