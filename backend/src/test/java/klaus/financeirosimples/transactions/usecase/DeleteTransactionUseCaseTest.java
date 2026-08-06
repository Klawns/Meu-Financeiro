package klaus.financeirosimples.transactions.usecase;

import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.exceptions.TransactionNotFoundException;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.application.usecases.DeleteTransactionUseCase;
import klaus.financeirosimples.transactions.domain.Transaction;
import klaus.financeirosimples.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Optional;
import java.util.UUID;

import static klaus.financeirosimples.transactions.TransactionFactory.createTransaction;
import static klaus.financeirosimples.user.UserFactory.createUser;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteTransactionUseCaseTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AuthenticatedUser currentUser;

    @InjectMocks
    DeleteTransactionUseCase delete;

    @Test
    @DisplayName("Success - Should delete a transaction with success.")
    void deleteTransactionSuccess(){
        UUID userId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();

        User user = createUser();
        Transaction testTransaction = createTransaction();

        when(currentUser.id()).thenReturn(userId);

        when(transactionRepository.findById(transactionId, userId)).thenReturn(Optional.of(testTransaction));

        delete.execute(transactionId);

        verify(transactionRepository).findById(transactionId, userId);
    }

    @Test
    @DisplayName("Error - Should throw when transaction does not exists.")
    void deleteTransactionFailure(){
        UUID userId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();

        when(currentUser.id()).thenReturn(userId);
        when(transactionRepository.findById(transactionId, userId)).thenReturn(Optional.empty());
        assertThrows(TransactionNotFoundException.class, () -> delete.execute(transactionId));

        verify(transactionRepository).findById(transactionId, userId);
    }

}
