package klaus.financeirosimples.user.usecase;


import klaus.financeirosimples.user.application.exceptions.UserNotFoundException;
import klaus.financeirosimples.user.application.output.UserOutput;
import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.application.usecases.FindUserUseCase;
import klaus.financeirosimples.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static klaus.financeirosimples.user.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindUserUseCaseTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    FindUserUseCase findUserUseCase;

    @Test
    @DisplayName("Success - Should find user by user_id with success.")
    void shouldFindUserByIdWithSuccess() {
        UUID id = UUID.randomUUID();
        User user = createUser();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserOutput response = findUserUseCase.findById(id);

        assertNotNull(response);

        assertEquals(user.getUsername(), response.username());
        assertEquals(user.getEmail(), response.email());

        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("Error - Should throw not found exception.")
    void shouldThrowWhenUserNotFound() {
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
           findUserUseCase.findById(id);
        });

        verify(userRepository).findById(id);
    }

}
