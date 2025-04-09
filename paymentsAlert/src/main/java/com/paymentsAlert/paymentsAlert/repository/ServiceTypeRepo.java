package com.paymentsAlert.paymentsAlert.repository;

import com.paymentsAlert.paymentsAlert.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepo extends JpaRepository<ServiceType, Long> {
    // Find a ServiceType by name
    Optional<ServiceType> findByName(String name);

    // Check if a ServiceType with the given name already exists
    boolean existsByName(String name);

}
