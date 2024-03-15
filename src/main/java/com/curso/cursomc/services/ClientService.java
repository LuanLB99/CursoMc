package com.curso.cursomc.services;


import com.curso.cursomc.domain.Address;
import com.curso.cursomc.domain.City;
import com.curso.cursomc.domain.Client;
import com.curso.cursomc.domain.enums.ClientType;
import com.curso.cursomc.dto.ClientDTO;
import com.curso.cursomc.dto.NewClientDTO;
import com.curso.cursomc.repositories.AddressRepository;
import com.curso.cursomc.repositories.ClientRepository;
import com.curso.cursomc.services.exceptions.DataIntegrityException;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ClientService {

    final ClientRepository repo;

    final AddressRepository addressRepository;

    public ClientService(ClientRepository repo, AddressRepository addressRepository) {
        this.repo = repo;
        this.addressRepository = addressRepository;
    }

    public Client search(Integer id){
        Optional<Client> client =  repo.findById(id);

        return client.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + Client.class.getName()));
    }

    @Transactional
    public Client insert(Client client){
        client.setId(null);
        Client newClient = repo.save(client);
        addressRepository.saveAll(newClient.getAddress());
        return newClient;
    }

    public Client update(Client client){
        Client newClient = search(client.getId());
        updateData(newClient, client);
        return  repo.save(newClient);
    }

    public void delete(Integer id){
        search(id);
        try{
            repo.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
        }
    }
    public List<Client> findAll(){
        return repo.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return  repo.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO clientDTO){
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getEmail(), null, null );
    }

    public Client fromDTO(NewClientDTO clientDTO){
        Client cli = new Client(null, clientDTO.getName(), clientDTO.getEmail(),
                clientDTO.getCpfOrCnpj(), ClientType.toEnum(clientDTO.getType()));

        City city = new City(clientDTO.getCityId(), null, null);
        Address add = new Address(null, clientDTO.getLogradouro(), clientDTO.getNumero(),
                clientDTO.getComplemento(), clientDTO.getBairro(), clientDTO.getCep(), cli, city);

        cli.getAddress().add(add);
        cli.getPhones().add(clientDTO.getPhone1());
        if(clientDTO.getPhone2() != null){
            cli.getPhones().add(clientDTO.getPhone2());
        }
        if(clientDTO.getPhone3() != null){
            cli.getPhones().add(clientDTO.getPhone3());
        }
        return  cli;
    }

    private void updateData(Client newClient, Client client){
        newClient.setName(client.getName());
        newClient.setEmail(client.getEmail());
    }
}
