package com.curso.cursomc.services;

import com.curso.cursomc.domain.PaymentWithTicket;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TicketService {

    public  void fillTicket(PaymentWithTicket pgto, Date orderInstant){
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderInstant);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pgto.setExpirationDate(cal.getTime());
    }
}
