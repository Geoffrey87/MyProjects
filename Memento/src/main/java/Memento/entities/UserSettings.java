package Memento.entities;

import Memento.entities.enums.Visibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility accountVisibility = Visibility.FRIENDS;

    @Column(nullable = false)
    private String canReceiveRequests = "Everyone"; // Could be an enum later

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility defaultPostVisibility = Visibility.FRIENDS;

    @Column(nullable = false)
    private Boolean allowFollowers = false;

    @Column(nullable = false)
    private Boolean emailNotifications = true;

    @Column(nullable = false)
    private Boolean pushNotifications = true;

    @Column(nullable = false)
    private Boolean isDeactivated = false;

    private LocalDateTime deleteRequestedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relations
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Lifecycle hooks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

