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
    private SupplierRepository supplierRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public void createInvoice(InvoiceRequest invoiceRequest) {
        Supplier supplier = supplierRepository.save(invoiceRequest.getSupplier());
        Customer customer = customerRepository.save(invoiceRequest.getCustomer());

        Invoice invoice = invoiceRequest.getInvoice();
        invoice.setSupplier(supplier);
        invoice.setCustomer(customer);
        invoiceRepository.save(invoice);

        double totalCost = 0;
        List<InvoiceProduct> invoiceProducts = invoiceRequest.getInvoiceProducts();

        if (invoiceProducts != null) {
            for (InvoiceProduct invoiceProduct : invoiceProducts) {
                Product product = invoiceProduct.getProduct();
                productService.saveProduct(product);
                totalCost += product.getPrice() * invoiceProduct.getCount();
                invoiceProduct.setInvoice(invoice);
                invoiceProductRepository.save(invoiceProduct);
            }
        } else {
            System.out.println("Invoice products list is null");
        }

        invoice.setTotalCost(totalCost);
        invoiceRepository.save(invoice);
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