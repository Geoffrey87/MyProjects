package com.LegisTrack.LegisTrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "party", schema = "my_schema")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int nrOfDeputies;

    @OneToMany(mappedBy = "proposingParty", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Law> proposedLaws;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Vote> votes;

}
