package com.curso.cursomc.resources;

import com.curso.cursomc.domain.PurchaseOrder;
import com.curso.cursomc.services.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class PurchaseOrderResource {

    final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderResource(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Integer id){
        PurchaseOrder order = purchaseOrderService.search(id);
        return ResponseEntity.ok().body(order);

    }
}
