package com.curso.cursomc.services;


import com.curso.cursomc.domain.Client;
import com.curso.cursomc.domain.Item;
import com.curso.cursomc.domain.PaymentWithTicket;
import com.curso.cursomc.domain.PurchaseOrder;
import com.curso.cursomc.domain.enums.PaymentState;
import com.curso.cursomc.repositories.OrderedItemRepository;
import com.curso.cursomc.repositories.PaymentRepository;
import com.curso.cursomc.repositories.PurchaseOrderRepository;
import com.curso.cursomc.security.UserSS;
import com.curso.cursomc.services.exceptions.AuthorizationException;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    final private PurchaseOrderRepository purchaseOrderRepository;
    final private TicketService ticketService;
    final private PaymentRepository paymentRepository;
    final private OrderedItemRepository orderedItemRepository;
    final private ProductService productService;
    final private ClientService clientService;
    final private EmailService emailService;


    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, TicketService ticketService,
                                PaymentRepository paymentRepository, ProductService productService,
                                OrderedItemRepository orderedItemRepository, ClientService clientService, EmailService emailService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.ticketService = ticketService;
        this.paymentRepository = paymentRepository;
        this.productService = productService;
        this.orderedItemRepository = orderedItemRepository;
        this.clientService = clientService;
        this.emailService = emailService;
    }

    public PurchaseOrder search(Integer id){
        Optional<PurchaseOrder> order =  purchaseOrderRepository.findById(id);

        return order.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + PurchaseOrder.class.getName()));
    }

    public PurchaseOrder insert(PurchaseOrder purchaseOrder){
        purchaseOrder.setId(null);
        purchaseOrder.setInstant(new Date());
        purchaseOrder.setClient(clientService.search(purchaseOrder.getClient().getId()));
        purchaseOrder.getPayment().setPaymentState(PaymentState.PENDING);
        purchaseOrder.getPayment().setPurchaseOrder(purchaseOrder);

        if(purchaseOrder.getPayment() instanceof PaymentWithTicket){
            PaymentWithTicket pgto = (PaymentWithTicket) purchaseOrder.getPayment();
            ticketService.fillTicket(pgto, purchaseOrder.getInstant());
        }
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        paymentRepository.save(purchaseOrder.getPayment());
        for(Item ip : purchaseOrder.getItems()){
            ip.setDiscount(0.0);
            ip.setProduct(productService.search(ip.getProduct().getId()));
            ip.setPrice(ip.getProduct().getPrice());
            ip.setPurchaseOrder(purchaseOrder);
        }
        orderedItemRepository.saveAll(purchaseOrder.getItems());

        emailService.sendOrderConfirmation(purchaseOrder);

        return purchaseOrder;
    }

    public Page<PurchaseOrder> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user = UserService.authenticated();
        if(user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Client client = clientService.search(user.getId());

        return purchaseOrderRepository.findByClient(client, pageRequest);
    }
}
