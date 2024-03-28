package com.curso.cursomc.domain;

import com.curso.cursomc.domain.enums.ClientType;
import com.curso.cursomc.domain.enums.Profile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Client implements Serializable {
    @Serial
    private  static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    private String cpfOrCnpj;
    private Integer type;
    @JsonIgnore
    private String password;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "phone")
    private Set<String> phones = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profiles")
    private Set<Integer> profiles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();

    public Client(Integer id, String name, String email, String cpfOrCnpj, ClientType type, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpfOrCnpj = cpfOrCnpj;
        this.type = (type == null) ? null : type.getCod();
        this.password = password;
        addProfile(Profile.CLIENT);
    }

    public void setType(ClientType type){
        this.type = type.getCod();
    }

    public ClientType getType(){
        return ClientType.toEnum(type);
    }

    public Set<Profile> getProfiles(){
        return profiles.stream().map(cod -> Profile.toEnum(cod)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile){
        profiles.add(profile.getCod());
    }

}
