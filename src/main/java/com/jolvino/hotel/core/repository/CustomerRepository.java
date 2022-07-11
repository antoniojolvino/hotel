package com.jolvino.hotel.core.repository;

import com.jolvino.hotel.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
