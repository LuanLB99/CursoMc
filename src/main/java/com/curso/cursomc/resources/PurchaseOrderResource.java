package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Category;
import com.curso.cursomc.domain.PurchaseOrder;
import com.curso.cursomc.dto.CategoryDTO;
import com.curso.cursomc.services.PurchaseOrderService;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
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

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<Page<PurchaseOrder>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instant")String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC")String direction){
        Page<PurchaseOrder> listPurchaseOrders = purchaseOrderService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(listPurchaseOrders);
    }
}
