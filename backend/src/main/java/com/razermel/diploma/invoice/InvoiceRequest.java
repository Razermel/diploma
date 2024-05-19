package com.razermel.diploma.invoice;

import lombok.Data;
import java.util.List;

@Data

public class InvoiceRequest {
    private Supplier supplier;
    private Customer customer;
    private Invoice invoice;
    private List<InvoiceProduct> invoiceProducts;
    
}

