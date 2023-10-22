package com.task.tracker.dao;

import com.task.tracker.model.CostCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {

    @Query("SELECT c FROM CostCenter c WHERE c.title = ?1 AND c.customer.customerId = ?2 ORDER BY c.createdDate DESC")
    Optional<CostCenter> findByTitleAndCustomer(final String title, final Long customerId);

    @Query("SELECT c FROM CostCenter c WHERE c.customer.customerId = ?1 ORDER BY c.createdDate DESC")
    List<CostCenter> findByCustomerId(final Long customerId);
}
