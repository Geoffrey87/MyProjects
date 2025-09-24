package Memento.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    private Set<Media> media = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    private Set<Story> stories = new HashSet<>();

    @ManyToMany(mappedBy = "preferredTags")
    private Set<UserPreference> userPreferences = new HashSet<>();
}

