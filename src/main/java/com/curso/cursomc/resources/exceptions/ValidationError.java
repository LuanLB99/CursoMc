package com.curso.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
        private  static  final long serialVersionUID = 1L;

        private List<FieldMessage> listErrors = new ArrayList<>();
    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getListErrors() {
        return listErrors;
    }

    public void addErrors(String fieldName, String message) {
        listErrors.add(new FieldMessage(fieldName, message));
    }
}
