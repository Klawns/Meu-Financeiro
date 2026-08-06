package klaus.financeirosimples.transactions.usecase;

import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.commands.CreateTransactionCommand;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.application.usecases.CreateTransactionUseCase;
import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.user.application.exceptions.UserNotFoundException;
import klaus.financeirosimples.user.application.ports.UserRepository;
import klaus.financeirosimples.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static klaus.financeirosimples.transactions.TransactionFactory.createTransactionCommand;
import static klaus.financeirosimples.user.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionUseCaseTest {
    @Mock
    UserRepository userRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AuthenticatedUser  currentUser;

    @InjectMocks
    CreateTransactionUseCase create;

    @Test
    @DisplayName("Success - Should create transaction with success")
    void createTransactionSuccess() {
        UUID userId = UUID.randomUUID();

        User user = createUser();

        when(currentUser.id()).thenReturn(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        CreateTransactionCommand command = createTransactionCommand();

        UUID response = create.execute(command);

        assertNotNull(response);

        verify(userRepository)
                .findById(userId);

        verify(transactionRepository)
                .save(any(Transaction.class));
    }

    @Test
    @DisplayName("Error - Should throw when user does not exists.")
    void createTransactionFailure() {
        UUID userId = UUID.randomUUID();
        when(currentUser.id()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        CreateTransactionCommand command = createTransactionCommand();

        assertThrows(UserNotFoundException.class, () -> {
            create.execute(command);
        });

        verify(userRepository)
                .findById(userId);
    }

}
