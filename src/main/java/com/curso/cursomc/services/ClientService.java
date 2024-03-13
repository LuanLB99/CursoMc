package com.curso.cursomc.services;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.repositories.ClientRepository;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ClientService {

    final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client search(Integer id){
        Optional<Client> client =  clientRepository.findById(id);

        return client.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + Client.class.getName()));
    }
}
