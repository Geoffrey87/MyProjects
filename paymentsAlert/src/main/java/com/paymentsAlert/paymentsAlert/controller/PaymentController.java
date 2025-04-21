package com.paymentsAlert.paymentsAlert.controller;

import com.paymentsAlert.paymentsAlert.dto.PaymentInputDto;
import com.paymentsAlert.paymentsAlert.dto.PaymentOutputDto;
import com.paymentsAlert.paymentsAlert.entity.Payment;
import com.paymentsAlert.paymentsAlert.service.IPayment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final IPayment paymentService;

    @Autowired
    public PaymentController(IPayment paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Creates a new payment.
     */
    @PostMapping
    public ResponseEntity<PaymentOutputDto> createPayment(@Valid @RequestBody PaymentInputDto payment) {
        PaymentOutputDto paymentOutputDto = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentOutputDto);
    }

    /**
     * Retrieves a payment by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentOutputDto> getPaymentById(@PathVariable Long id) {
        PaymentOutputDto dto = paymentService.getPaymentById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Updates a payment by its ID.
     */
    @GetMapping("/by-date")
    public ResponseEntity<List<PaymentOutputDto>> getPaymentsByDate(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<PaymentOutputDto> payments = paymentService.getPaymentsByUserAndDate(userId, date);
        return ResponseEntity.ok(payments);
    }

    /**
     * Retrieves all payments for a user.
     */
    @PatchMapping("/{id}/paid")
    public ResponseEntity<Void> markPaymentPaidStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> payload) {

        Boolean paid = payload.get("paid");
        if (paid == null) {
            return ResponseEntity.badRequest().build();
        }

        paymentService.setPaidStatus(id, paid);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all payments for a user.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}

