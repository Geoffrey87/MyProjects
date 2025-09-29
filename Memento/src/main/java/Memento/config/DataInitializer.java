package Memento.config;

import Memento.entities.*;
import Memento.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        // 1. Ensure roles exist
        Arrays.stream(RoleType.values()).forEach(roleType -> {
            roleRepository.findByName(roleType).orElseGet(() -> roleRepository.save(new Role(null, roleType)));
        });

        // 2. Ensure default admin user exists
        if (userRepository.findByEmail("admin@memento.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@memento.com");
            admin.setPasswordHash(passwordEncoder.encode("Admin123!"));
            admin.setRoles(Set.of(
                    roleRepository.findByName(RoleType.ADMIN).orElseThrow(),
                    roleRepository.findByName(RoleType.USER).orElseThrow()
            ));
            userRepository.save(admin);
        }

        // 3. Load seed data from JSON
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("seed-data.json");
        if (inputStream != null) {
            SeedData seedData = objectMapper.readValue(inputStream, SeedData.class);

            for (SeedCategory seedCategory : seedData.getCategories()) {
                Category category = categoryRepository.findByName(seedCategory.getName())
                        .orElseGet(() -> categoryRepository.save(new Category(null, seedCategory.getName(), new HashSet<>())));

                seedCategory.getTags().forEach(tagName -> createTagIfNotExists(tagName, category));
            }
        }
    }

    private void createTagIfNotExists(String tagName, Category category) {
        tagRepository.findByName(tagName).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setName(tagName);
            tag.setCategory(category);
            return tagRepository.save(tag);
        });
    }
}
