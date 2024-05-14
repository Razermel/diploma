package com.razermel.diploma.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;



@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private ProductService productService;

    public void createInvoice(Invoice invoice) {
        double totalCost = 0;
        for (InvoiceProduct invoiceProduct : invoice.getInvoiceProducts()) {
            Product product = invoiceProduct.getProduct();
            productService.saveProduct(product); // Сохраняем продукт перед сохранением InvoiceProduct
            totalCost += product.getPrice() * invoiceProduct.getCount();
        }

        invoice.setTotalCost(totalCost);
        invoiceRepository.save(invoice);
        for (InvoiceProduct invoiceProduct : invoice.getInvoiceProducts()) {
            invoiceProduct.setInvoice(invoice);
            invoiceProductRepository.save(invoiceProduct);
        }
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Transactional
    public void updateInvoiceStatus(Long id, boolean newStatus) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoice invoice = optionalInvoice.get();
            invoice.setStatus(newStatus);
            invoiceRepository.save(invoice);
        } else {
            System.out.println("Invoice with ID " + id + " not found");
        }
    }

}