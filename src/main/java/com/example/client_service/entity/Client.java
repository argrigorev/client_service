package com.example.client_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column
    private String name;

    @Column(name = "last_name")
    private String lastname;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
}
