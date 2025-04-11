package com.paymentsAlert.paymentsAlert.repository;

import com.paymentsAlert.paymentsAlert.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    List<Payment> findByUserIdAndDateTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);

}
