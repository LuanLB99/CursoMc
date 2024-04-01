package com.curso.cursomc.services;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.repositories.ClientRepository;
import com.curso.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BCryptPasswordEncoder pe;

    @Autowired
    EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email){

        Client client = clientRepository.findByEmail(email);

        if(client == null){
            throw new ObjectNotFoundException("Email não cadastrado");
        }

        String newPass = newPassword();
        client.setPassword(pe.encode(newPass));
       // clientRepository.save(client);
        emailService.sendNewPasswordEmail(client, newPass);

    }

    private String newPassword() {
        char[] vet = new char[10];

        for (int i = 0; i < 10; i++ ){
            vet[i] = newChar();
        }

        return new String(vet);
    }

    private char newChar() {
        int opt = rand.nextInt(3);
        if(opt == 0){
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1){
            return (char) (rand.nextInt(10) + 65);
        } else {
            return (char) (rand.nextInt(10) + 97);
        }
    }
}
