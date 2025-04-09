package com.paymentsAlert.paymentsAlert.repository;

import com.paymentsAlert.paymentsAlert.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

}
