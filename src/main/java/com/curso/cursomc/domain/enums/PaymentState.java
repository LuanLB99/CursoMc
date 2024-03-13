package com.curso.cursomc.domain.enums;

public enum PaymentState {
    PENDING(1, "Pending"),
    PAID(2,"Paid"),
    CANCELED(3,"Canceled");

    private int cod;
    private  String description;

    private PaymentState(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod(){
        return cod;
    }
    public String getDescription(){
        return description;
    }

    public static PaymentState toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for (PaymentState x : PaymentState.values()){
            if(cod.equals(x.getCod())){
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
