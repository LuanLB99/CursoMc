package com.curso.cursomc.services;

import com.curso.cursomc.domain.PurchaseOrder;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendOrderConfirmation(PurchaseOrder purchaseOrder);

    void sendMail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(PurchaseOrder purchaseOrder);
    void sendHtmlEmail(MimeMessage msg);
}
