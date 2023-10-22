package com.task.tracker.dao;

import com.task.tracker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.company = ?1 ORDER BY c.createdDate DESC")
    Optional<Customer> findByCompanyName(final String companyName);
}
