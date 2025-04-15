package com.paymentsAlert.paymentsAlert.repository;

import com.paymentsAlert.paymentsAlert.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByUsername(String username);

    Optional<CustomUser> findByEmail(String email);

}
