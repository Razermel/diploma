package com.razermel.diploma.invoice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
public class HtmlTemplateController {

    @Autowired
    private InvoiceService invoiceService;

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @GetMapping("/api/v1/invoices/{id}/html")
    public ResponseEntity<String> getInvoiceHtmlById(@PathVariable Long id, Authentication authentication) {
        String currentUserName = authentication.getName();

        // Проверяем роль пользователя
        boolean hasRole1 = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"));

        if (hasRole1) {
            Invoice invoice = invoiceService.getInvoiceById(id);
            if (invoice != null) {
                logger.info("Invoice {}", id);
                // Форматируем шаблон HTML
                String htmlTemplate = formatInvoiceTemplate(invoice);
                // Возвращаем отформатированный HTML-шаблон
                return ResponseEntity.ok(htmlTemplate);
            } else {
                logger.info("Invoice {} not found", id);
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.info("User {} doesn't have permission to view invoices", currentUserName);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    private String formatInvoiceTemplate(Invoice invoice) {
        // Здесь вы можете создать шаблон HTML или другой формат, используя данные счета-фактуры
        // Например, вы можете использовать Thymeleaf, FreeMarker или другие шаблонизаторы

        StringBuilder templateBuilder = new StringBuilder();
        templateBuilder.append("<h1>Invoice</h1>");
        templateBuilder.append("<p>ID: ").append(invoice.getId()).append("</p>");
        templateBuilder.append("<p>Total Cost: ").append(invoice.getTotalCost()).append("</p>");
        templateBuilder.append("<p>Supplier: ").append(invoice.getSupplier()).append("</p>");
        templateBuilder.append("<p>Customer: ").append(invoice.getCustomer()).append("</p>");

        // Добавляем информацию о продуктах
        templateBuilder.append("<h2>Products</h2>");
        for (InvoiceProduct invoiceProduct : invoice.getInvoiceProducts()) {
            Product product = invoiceProduct.getProduct();
            templateBuilder.append("<p>Product: ").append(product.getName()).append("</p>");
            templateBuilder.append("<p>Price: ").append(product.getPrice()).append("</p>");
            templateBuilder.append("<p>Weight: ").append(product.getWeight()).append("</p>");
            templateBuilder.append("<p>Article: ").append(product.getArticle()).append("</p>");
            templateBuilder.append("<p>Count: ").append(invoiceProduct.getCount()).append("</p>");
        }

        return templateBuilder.toString();
    }

}
