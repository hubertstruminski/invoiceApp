package com.hubertstruminski.invoice.app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String email;

    private String phoneNumber;
    private String website;
    private String nip;
    private String note;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;
}
