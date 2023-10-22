package com.task.tracker.dao;

import com.task.tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.title = ?1 AND p.customer.customerId = ?2 ORDER BY p.createdDate DESC")
    Optional<Project> findByTitleAndCustomer(String title, Long customerId);

    @Query("SELECT p FROM Project p WHERE p.customer.customerId = ?1 ORDER BY p.createdDate DESC")
    List<Project> findByCustomer(Long customerId);

    @Query("SELECT p FROM Project p WHERE p.costCenter.costCenterId = ?1 ORDER BY p.createdDate DESC")
    Optional<Project> findByCostCenter(Long costCenterId);
}
