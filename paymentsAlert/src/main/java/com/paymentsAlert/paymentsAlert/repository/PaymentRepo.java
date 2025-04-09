package com.paymentsAlert.paymentsAlert.repository;

import com.paymentsAlert.paymentsAlert.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
