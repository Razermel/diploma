package com.razermel.diploma.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Дополнительные методы, если нужно
}