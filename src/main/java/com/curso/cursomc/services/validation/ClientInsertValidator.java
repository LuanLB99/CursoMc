package com.curso.cursomc.services.validation;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.domain.enums.ClientType;
import com.curso.cursomc.dto.NewClientDTO;
import com.curso.cursomc.repositories.ClientRepository;
import com.curso.cursomc.resources.exceptions.FieldMessage;
import com.curso.cursomc.services.validation.utils.BR;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, NewClientDTO> {
    @Autowired
    private ClientRepository repo;
    @Override
    public void initialize(ClientInsert ann) {
    }

    @Override
    public boolean isValid(NewClientDTO newClientDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if(newClientDTO.getType().equals(ClientType.PESSOAFISICA.getCod()) &&
                !BR.isValidCPF(newClientDTO.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj","CPF Inválido"));
        }
        if(newClientDTO.getType().equals(ClientType.PESSOAJURIDICA.getCod()) &&
                !BR.isValidCNPJ(newClientDTO.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj","CNPJ Inválido"));
        }

        Client clientByEmail = repo.findByEmail(newClientDTO.getEmail());
        if (clientByEmail != null ){
            list.add(new FieldMessage("email", "Email já cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}