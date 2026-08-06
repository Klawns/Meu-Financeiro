package klaus.financeirosimples.transactions.usecase;

import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.transactions.application.commands.TransactionPeriod;
import klaus.financeirosimples.transactions.application.exceptions.TransactionNotFoundException;
import klaus.financeirosimples.transactions.application.outputs.TransactionOutput;
import klaus.financeirosimples.transactions.application.ports.TransactionRepository;
import klaus.financeirosimples.transactions.application.usecases.FindTransactionUseCase;
import klaus.financeirosimples.transactions.domain.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static klaus.financeirosimples.transactions.TransactionFactory.createTransaction;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindTransactionUseCaseTest {

    @Mock
    private TransactionRepository repo;

    @Mock
    private AuthenticatedUser user;

    @InjectMocks
    private FindTransactionUseCase useCase;

    @Test
    @DisplayName("Success - Should find transaction by id.")
    void shouldFindTransactionById() {

        UUID userId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();

        Transaction transaction = createTransaction();

        when(user.id()).thenReturn(userId);
        when(repo.findById(transactionId, userId))
                .thenReturn(Optional.of(transaction));

        TransactionOutput response = useCase.findById(transactionId);

        assertNotNull(response);
        assertEquals(transaction.getCategory(), response.category());
        assertEquals(transaction.getType(), response.type());
        assertEquals(transaction.getAmount(), response.amount());
        assertEquals(transaction.getAccount(), response.account());
        assertEquals(transaction.getOccurredAt(), response.occurredAt());

        verify(repo).findById(transactionId, userId);
    }

    @Test
    @DisplayName("Error - Should throw when transaction is not found.")
    void shouldThrowWhenTransactionNotFound() {

        UUID userId = UUID.randomUUID();
        UUID transactionId = UUID.randomUUID();

        when(user.id()).thenReturn(userId);
        when(repo.findById(transactionId, userId))
                .thenReturn(Optional.empty());

        assertThrows(
                TransactionNotFoundException.class,
                () -> useCase.findById(transactionId)
        );

        verify(repo).findById(transactionId, userId);
    }

    @Test
    @DisplayName("Success - Should return all transactions.")
    void shouldReturnAllTransactions() {

        UUID userId = UUID.randomUUID();

        when(user.id()).thenReturn(userId);
        when(repo.findAll(userId))
                .thenReturn(List.of(createTransaction(), createTransaction()));

        List<TransactionOutput> response = useCase.findAll();

        assertEquals(2, response.size());

        verify(repo).findAll(userId);
    }

    @Test
    @DisplayName("Success - Should return empty transaction list.")
    void shouldReturnEmptyTransactionList() {

        UUID userId = UUID.randomUUID();

        when(user.id()).thenReturn(userId);
        when(repo.findAll(userId))
                .thenReturn(List.of());

        List<TransactionOutput> response = useCase.findAll();

        assertTrue(response.isEmpty());

        verify(repo).findAll(userId);
    }

    @Test
    @DisplayName("Success - Should return transactions by period.")
    void shouldReturnTransactionsByPeriod() {

        UUID userId = UUID.randomUUID();

        when(user.id()).thenReturn(userId);
        when(repo.findByDateBetween(any(), any(), eq(userId)))
                .thenReturn(List.of(createTransaction()));

        List<TransactionOutput> response =
                useCase.findAllByDateBetween(TransactionPeriod.TODAY);

        assertEquals(1, response.size());

        verify(repo).findByDateBetween(any(), any(), eq(userId));
    }

    @Test
    @DisplayName("Success - Should return empty transaction list by period.")
    void shouldReturnEmptyTransactionListByPeriod() {

        UUID userId = UUID.randomUUID();

        when(user.id()).thenReturn(userId);
        when(repo.findByDateBetween(any(), any(), eq(userId)))
                .thenReturn(List.of());

        List<TransactionOutput> response =
                useCase.findAllByDateBetween(TransactionPeriod.TODAY);

        assertTrue(response.isEmpty());

        verify(repo).findByDateBetween(any(), any(), eq(userId));
    }
}