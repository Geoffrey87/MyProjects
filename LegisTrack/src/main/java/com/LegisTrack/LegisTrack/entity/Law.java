package com.LegisTrack.LegisTrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a law entity in the system.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Law {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate dateProposed;

    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party proposingParty;

    @OneToMany(mappedBy = "law", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;

}
