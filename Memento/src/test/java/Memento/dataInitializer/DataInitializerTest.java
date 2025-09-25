package Memento.dataInitializer;

import Memento.entities.Role;
import Memento.entities.RoleType;
import Memento.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void run_shouldCreateMissingRoles() {
        when(roleRepository.findByName(any(RoleType.class))).thenReturn(Optional.empty());

        dataInitializer.run();

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository, times(RoleType.values().length)).save(roleCaptor.capture());
        assertThat(roleCaptor.getAllValues())
                .extracting(Role::getName)
                .containsExactlyInAnyOrder(RoleType.values());
    }

    @Test
    void run_shouldNotDuplicateExistingRoles() {
        when(roleRepository.findByName(any(RoleType.class))).thenReturn(Optional.of(new Role()));

        dataInitializer.run();

        verify(roleRepository, never()).save(any(Role.class));
    }
}
