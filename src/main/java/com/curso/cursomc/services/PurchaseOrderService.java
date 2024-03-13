package com.curso.cursomc.services;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.domain.PurchaseOrder;
import com.curso.cursomc.repositories.PurchaseOrderRepository;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseOrderService {

    final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public PurchaseOrder search(Integer id){
        Optional<PurchaseOrder> order =  purchaseOrderRepository.findById(id);

        return order.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + PurchaseOrder.class.getName()));
    }
}
