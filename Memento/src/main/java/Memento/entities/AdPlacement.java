package Memento.entities;

import Memento.entities.enums.AdStatus;
import Memento.entities.enums.Visibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ad_placements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdPlacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campaign title or internal name
    @Column(nullable = false)
    private String title;

    // Destination URL (where the user is redirected when clicking the ad)
    @Column(nullable = false)
    private String targetUrl;

    // Media asset for the ad (image, banner, short video, etc.)
    @Column(nullable = false)
    private String mediaUrl;

    // Campaign start date and time
    @Column(nullable = false)
    private LocalDateTime startAt;

    // Campaign end date and time
    @Column(nullable = false)
    private LocalDateTime endAt;

    // Name of the advertiser (company or brand)
    @Column(nullable = false)
    private String advertiserName;

    // Targeted category for this ad (optional, can be null = general ad)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Targeted tag for this ad (optional, more specific than category)
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    // Visibility scope of the ad (usually PUBLIC, but flexible if needed)
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PUBLIC;

    // Status of the ad lifecycle (PENDING, APPROVED, ACTIVE, EXPIRED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdStatus status = AdStatus.PENDING;
}
