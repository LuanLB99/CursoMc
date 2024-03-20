package com.curso.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class PurchaseOrder implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "purchaseOrder")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "id.purchaseOrder")
    private Set<Item> items = new HashSet<>();

    public PurchaseOrder(Integer id, Date instant, Client client, Address address) {
        this.id = id;
        this.instant = instant;
        this.client = client;
        this.address = address;
    }
    public double getTotalValue(){
        double sum = 0.0;

        for(Item ip : items){
           sum = sum + ip.getSubTotal();
        }
        return sum;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        builder.append("Pedido número: ");
        builder.append(getId());
        builder.append(", Instante: ");
        builder.append(dateFormat.format(getInstant()));
        builder.append(" Cliente: ");
        builder.append(getClient().getName());
        builder.append(", Situação pagamento: ");
        builder.append(getPayment().getState().getDescription());
        builder.append("\nDetalhes: \n");
        for (Item ip: getItems()){
            builder.append(ip.toString());
        }
        builder.append("Valor total: ");
        builder.append(numberFormat.format(getTotalValue()));
        return builder.toString();
    }

}
