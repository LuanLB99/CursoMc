package com.curso.cursomc.services;

import com.curso.cursomc.domain.PurchaseOrder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService{
    @Value("${default.sender}")
    private String sender;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmation(PurchaseOrder purchaseOrder) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPurchaseOrder(purchaseOrder);
        sendMail(sm);
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
    protected String htmlFromTemplatePedido(PurchaseOrder purchaseOrder) {
        Context context = new Context();
        context.setVariable("pedido", purchaseOrder);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(PurchaseOrder purchaseOrder){
        try {
            MimeMessage mm = prepareMimeMessageFromPurchaseOrder(purchaseOrder);
            sendHtmlEmail(mm);
        } catch (MessagingException e){
            sendOrderConfirmation(purchaseOrder);
        }

    }

    protected MimeMessage prepareMimeMessageFromPurchaseOrder(PurchaseOrder purchaseOrder) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(purchaseOrder.getClient().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido Confirmado! Código: " + purchaseOrder.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(purchaseOrder), true);
        return mimeMessage;
    }
}
