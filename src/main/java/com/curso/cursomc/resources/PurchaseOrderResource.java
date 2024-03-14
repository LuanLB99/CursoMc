package com.curso.cursomc.resources;

import com.curso.cursomc.domain.PurchaseOrder;
import com.curso.cursomc.services.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
public class PurchaseOrderResource {

    final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderResource(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PurchaseOrder> find(@PathVariable Integer id){
        PurchaseOrder order = purchaseOrderService.search(id);
        return ResponseEntity.ok().body(order);

    }
}
