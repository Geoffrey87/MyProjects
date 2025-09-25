package Memento.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Column(columnDefinition = "TEXT")
    private String details;

    @ManyToOne
    @JoinColumn(name = "performed_by")
    private User performedBy;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}

