package com.curso.cursomc.services.validation;

import com.curso.cursomc.domain.enums.ClientType;
import com.curso.cursomc.dto.NewClientDTO;
import com.curso.cursomc.resources.exceptions.FieldMessage;
import com.curso.cursomc.services.validation.utils.BR;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, NewClientDTO> {
    @Override
    public void initialize(ClientInsert ann) {
    }

    @Override
    public boolean isValid(NewClientDTO newClientDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if(newClientDTO.getType().equals(ClientType.PESSOAFISICA.getCod()) &&
                !BR.isValidCPF(newClientDTO.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CPF Inválido"));
        }
        if(newClientDTO.getType().equals(ClientType.PESSOAJURIDICA.getCod()) &&
                !BR.isValidCNPJ(newClientDTO.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CNPJ Inválido"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}