package Memento.entities;

import Memento.entities.enums.Visibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "music_posts")
@NamedEntityGraph(
        name = "MusicPost.withAuthor",
        attributeNodes = {@NamedAttributeNode("author")}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String platform; // Spotify, YouTube, AppleMusic

    @Column(nullable = false)
    private String trackUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    private String coverUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sharedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.FRIENDS;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "musicPost")
    private Set<Reaction> reactions = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.sharedAt = LocalDateTime.now();
    }
}
