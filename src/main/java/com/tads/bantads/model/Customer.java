package com.tads.bantads.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String cpf;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "address_id")
    private Address address;
    @Column(unique = true)
    private UUID account;
}
