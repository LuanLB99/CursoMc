package com.curso.cursomc.services;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.domain.PurchaseOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.stereotype.Service;

import java.util.Date;


@Service



public abstract class AbstractEmailService implements EmailService{
    @Value("${default.sender}")
    private String sender;
    @Override
    public void sendOrderConfirmation(PurchaseOrder purchaseOrder) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPurchaseOrder(purchaseOrder);
        sendMail(sm);
    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage sm = prepareNewPassword(client, newPass);
        sendMail(sm);
    }

    protected SimpleMailMessage prepareNewPassword(Client client, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(client.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Senha alterada com sucesso!" );
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha " + newPass);

        return sm;
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPurchaseOrder(PurchaseOrder purchaseOrder) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(purchaseOrder.getClient().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + purchaseOrder.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(purchaseOrder.toString());

        return sm;
    }
}
