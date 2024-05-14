package com.razermel.diploma.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;



@RestController
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;


    @PostMapping("/api/v1/invoices/create")
    public ResponseEntity<String> createInvoice(@RequestBody Invoice invoice, Authentication authentication) {
        // Получаем имя текущего пользователя из аутентификационного объекта
        String currentUserName = authentication.getName();

        // Проверяем роль пользователя
        boolean hasRole1 = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"));


        if (hasRole1) {
            // Логика создания счета-фактуры
            logger.info("Received request to create invoice: {}", invoice.getId());
            invoiceService.createInvoice(invoice);
            logger.info("Invoice created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Invoice created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to create invoices");
        }
    }


    @GetMapping("/api/v1/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id, Authentication authentication) {
        String currentUserName = authentication.getName();

        // Проверяем роль пользователя
        boolean hasRole1 = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"));

        if (hasRole1) {
            Invoice invoice = invoiceService.getInvoiceById(id);
            if (invoice != null) {
                logger.info("Invoice {}", id);
                return ResponseEntity.ok(invoice);
            } else {
                logger.info("Invoice {} not found", id);
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.info("User {} doesn't have permission to view invoices", currentUserName);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @GetMapping("/api/v1/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices(Authentication authentication) {
        String currentUserName = authentication.getName();

        // Проверяем роль пользователя
        boolean hasRole1 = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"));

        if (hasRole1) {
            List<Invoice> invoices = invoiceService.getAllInvoices();
            if (!invoices.isEmpty()) {
                logger.info("Retrieved all invoices");
                return ResponseEntity.ok(invoices);
            } else {
                logger.info("No invoices found");
                return ResponseEntity.noContent().build();
            }
        } else {
            logger.info("User {} doesn't have permission to view invoices", currentUserName);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

}