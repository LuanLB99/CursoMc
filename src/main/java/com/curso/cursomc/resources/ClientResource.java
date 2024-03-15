package com.curso.cursomc.resources;


import com.curso.cursomc.domain.Category;
import com.curso.cursomc.domain.Client;

import com.curso.cursomc.dto.CategoryDTO;
import com.curso.cursomc.dto.ClientDTO;
import com.curso.cursomc.dto.NewClientDTO;
import com.curso.cursomc.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    final ClientService service;
    public ClientResource(ClientService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> find(@PathVariable Integer id){
        Client client = service.search(id);
        return ResponseEntity.ok().body(client);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody NewClientDTO clientDTO){
        Client client = service.fromDTO(clientDTO);
        client = service.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public  ResponseEntity<Void> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable Integer id){
        Client client = service.fromDTO(clientDTO);
        client.setId(id);
        Client updatedClient = service.update(client);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<Client> listClients = service.findAll();
        List<ClientDTO> listClientsDTO = listClients.stream().map( client -> new ClientDTO(client)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listClientsDTO);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClientDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<Client> listClients = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClientDTO> listClientsDTOPage= listClients.map( client -> new ClientDTO(client));
        return ResponseEntity.ok().body(listClientsDTOPage);
    }
}
