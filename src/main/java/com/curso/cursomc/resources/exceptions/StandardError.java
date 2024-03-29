package com.curso.cursomc.resources.exceptions;

import lombok.Data;

import java.io.Serializable;

@Data
public class StandardError implements Serializable {
    private  static  final long serialVersionUID = 1L;

    private Integer status;
    private String msg;
    private Long timeStamp;

    public StandardError(Integer status, String msg, Long timeStamp) {
        super();
        this.status = status;
        this.msg = msg;
        this.timeStamp = timeStamp;
    }
}
