package com.paymentsAlert.paymentsAlert.controller;

import com.paymentsAlert.paymentsAlert.dto.PaymentInputDto;
import com.paymentsAlert.paymentsAlert.dto.PaymentOutputDto;
import com.paymentsAlert.paymentsAlert.entity.Payment;
import com.paymentsAlert.paymentsAlert.service.IPayment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final IPayment paymentService;

    @Autowired
    public PaymentController(IPayment paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentOutputDto> createPayment(@Valid @RequestBody PaymentInputDto payment) {
        PaymentOutputDto paymentOutputDto = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentOutputDto> getPaymentById(@PathVariable Long id) {
        PaymentOutputDto dto = paymentService.getPaymentById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentOutputDto> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentInputDto payment) {
        PaymentOutputDto dto = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}

