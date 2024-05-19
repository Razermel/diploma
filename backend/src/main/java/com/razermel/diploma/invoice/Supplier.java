package com.razermel.diploma.invoice;


import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phone;


}