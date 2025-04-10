package ServiceTest;

import com.paymentsAlert.paymentsAlert.dto.UserInputDto;
import com.paymentsAlert.paymentsAlert.dto.UserOutputDto;
import com.paymentsAlert.paymentsAlert.entity.User;
import com.paymentsAlert.paymentsAlert.repository.UserRepo;
import com.paymentsAlert.paymentsAlert.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserInputDto userInputDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userInputDto = new UserInputDto("John Doe", "john@example.com", "password123");
        user = new User(1L, "John Doe", "john@example.com", "encodedPassword", null);
    }

    @Test
    void registerUser_ShouldSaveUser_WhenEmailIsNew() {
        when(userRepo.findByEmail(userInputDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userInputDto.getPassword())).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserOutputDto result = userService.registerUser(userInputDto);

        assertEquals("John Doe", result.getUsername());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailExists() {
        when(userRepo.findByEmail(userInputDto.getEmail())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.registerUser(userInputDto));

        assertEquals("Email already exists", exception.getMessage());
        verify(userRepo, never()).save(any());
    }

    @Test
    void getUserById_ShouldReturnUser_WhenFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        UserOutputDto result = userService.getUserById(1L);

        assertEquals("John Doe", result.getUsername());
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByEmail_ShouldReturnUser_WhenFound() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        UserOutputDto result = userService.getUserByEmail("john@example.com");

        assertEquals("John Doe", result.getUsername());
    }

    @Test
    void updateUser_ShouldUpdateFields_WhenUserExists() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPass")).thenReturn("newEncodedPass");

        UserInputDto updateDto = new UserInputDto("New Name", "new@example.com", "newPass");
        User updatedUser = new User(1L, "New Name", "new@example.com", "newEncodedPass", null);
        when(userRepo.save(any(User.class))).thenReturn(updatedUser);

        UserOutputDto result = userService.updateUser(1L, updateDto);

        assertEquals("New Name", result.getUsername());
    }

    @Test
    void deleteUser_ShouldRemoveUser_WhenExists() {
        when(userRepo.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepo).deleteById(1L);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenNotFound() {
        when(userRepo.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenExists() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        assertTrue(userService.existsByEmail("john@example.com"));
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenNotExists() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());

        assertFalse(userService.existsByEmail("john@example.com"));
    }
}

