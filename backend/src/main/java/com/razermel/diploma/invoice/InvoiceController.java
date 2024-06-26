package com.razermel.diploma.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

@RestController
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/api/v1/invoices/create")
    public ResponseEntity<String> createInvoice(@RequestBody InvoiceRequest invoiceRequest, Authentication authentication) {
        logger.info("Received request to create invoice");
        invoiceService.createInvoice(invoiceRequest);
        logger.info("Invoice created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body("Invoice created successfully");
    }

    @GetMapping("/api/v1/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id, Authentication authentication) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice != null) {
            logger.info("Invoice {}", id);
            return ResponseEntity.ok(invoice);
        } else {
            logger.info("Invoice {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/v1/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices(Authentication authentication) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        if (!invoices.isEmpty()) {
            logger.info("Retrieved all invoices");
            return ResponseEntity.ok(invoices);
        } else {
            logger.info("No invoices found");
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/api/v1/invoices/{id}/status")
    public ResponseEntity<?> updateInvoiceStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> statusMap) {
        logger.info("Update invoice status started");
        Boolean newStatus = statusMap.get("status");
        try {
            invoiceService.updateInvoiceStatus(id, newStatus);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Failed to update invoice status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update invoice status");
        }
    }

}
