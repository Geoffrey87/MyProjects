package Memento.dataInitializer;

import Memento.entities.Role;
import Memento.entities.RoleType;
import Memento.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        Arrays.stream(RoleType.values()).forEach(roleType -> {
            roleRepository.findByName(roleType).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleType);
                return roleRepository.save(role);
            });
        });
    }
}


