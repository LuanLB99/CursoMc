package com.curso.cursomc.services;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.domain.Item;
import com.curso.cursomc.domain.PaymentWithTicket;
import com.curso.cursomc.domain.PurchaseOrder;
import com.curso.cursomc.domain.enums.PaymentState;
import com.curso.cursomc.repositories.OrderedItemRepository;
import com.curso.cursomc.repositories.PaymentRepository;
import com.curso.cursomc.repositories.ProductRepository;
import com.curso.cursomc.repositories.PurchaseOrderRepository;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
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

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, TicketService ticketService,
                                PaymentRepository paymentRepository, ProductService productService,
                                OrderedItemRepository orderedItemRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.ticketService = ticketService;
        this.paymentRepository = paymentRepository;
        this.productService = productService;
        this.orderedItemRepository = orderedItemRepository;
    }

    public PurchaseOrder search(Integer id){
        Optional<PurchaseOrder> order =  purchaseOrderRepository.findById(id);

        return order.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + PurchaseOrder.class.getName()));
    }

    public PurchaseOrder insert(PurchaseOrder purchaseOrder){
        purchaseOrder.setId(null);
        purchaseOrder.setInstant(new Date());
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
            ip.setPrice(productService.search(ip.getProduct().getId()).getPrice());
            ip.setPurchaseOrder(purchaseOrder);
        }
        orderedItemRepository.saveAll(purchaseOrder.getItems());
        return purchaseOrder;
    }
}
