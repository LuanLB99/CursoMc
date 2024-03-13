package com.curso.cursomc.resources;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    final ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> find(@PathVariable Integer id){
        Client client = clientService.search(id);
        return ResponseEntity.ok().body(client);

    }
}
