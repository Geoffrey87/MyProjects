package Memento.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "User.withSettings",
        attributeNodes = {
                @NamedAttributeNode("settings")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private Integer age;

    private String location;

    private String avatarUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relations
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserSettings settings;

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

    // Inverse relations
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Media> media = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<Story> stories = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<MusicPost> musicPosts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ProfileMusic> profileMusic = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<ConnectionRequest> sentRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<ConnectionRequest> receivedRequests = new HashSet<>();

    @OneToMany(mappedBy = "userA")
    private Set<Relationship> relationshipsAsA = new HashSet<>();

    @OneToMany(mappedBy = "userB")
    private Set<Relationship> relationshipsAsB = new HashSet<>();

    @OneToMany(mappedBy = "following")
    private Set<Follower> followers = new HashSet<>();

    @OneToMany(mappedBy = "follower")
    private Set<Follower> following = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Reaction> reactions = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<StoryReaction> storyReactions = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserPreference preference;
}
