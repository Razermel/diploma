package com.razermel.diploma.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;



@Data
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalCost;
    private String supplier;
    private String customer;
    private boolean status;

    @OneToMany(mappedBy = "invoice")
    @JsonManagedReference
    private List<InvoiceProduct> invoiceProducts;

}
