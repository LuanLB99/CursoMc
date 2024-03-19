package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.domain.PurchaseOrder;
import com.curso.cursomc.dto.CategoryDTO;
import com.curso.cursomc.services.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PurchaseOrder purchaseOrder){

        purchaseOrder = purchaseOrderService.insert(purchaseOrder);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(purchaseOrder.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
