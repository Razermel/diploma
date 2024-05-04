package com.razermel.diploma.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProductService {

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    public InvoiceProduct saveInvoiceProduct(InvoiceProduct invoiceProduct) {
        return invoiceProductRepository.save(invoiceProduct);
    }

    // Другие методы сервиса, если необходимо
}
