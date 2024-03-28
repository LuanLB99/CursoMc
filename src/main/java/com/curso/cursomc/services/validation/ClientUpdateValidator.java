package com.curso.cursomc.services.validation;

import com.curso.cursomc.domain.Client;
import com.curso.cursomc.dto.ClientDTO;
import com.curso.cursomc.repositories.ClientRepository;
import com.curso.cursomc.resources.exceptions.FieldMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ClientRepository repo;
    @Override
    public void initialize(ClientUpdate ann) {
    }

    @Override
    public boolean isValid(ClientDTO clientDTO, ConstraintValidatorContext context) {
        Map<String, String> mapAtributtes = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer clientId = Integer.parseInt(mapAtributtes.get("id"));
        List<FieldMessage> list = new ArrayList<>();

        Client clientByEmail = repo.findByEmail(clientDTO.getEmail());

        if (clientByEmail != null && !clientByEmail.getId().equals(clientId)){
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