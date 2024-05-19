package com.razermel.diploma.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Дополнительные методы, если нужно
}
